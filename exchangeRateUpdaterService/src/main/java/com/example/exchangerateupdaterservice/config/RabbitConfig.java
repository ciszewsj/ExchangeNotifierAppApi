package com.example.exchangerateupdaterservice.config;

import ee.ciszewsj.exchangeratecommondata.rabbit.RabbitNamespace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitConfig {


	@Bean
	public TopicExchange exchange(RabbitAdmin rabbitAdmin) {
		TopicExchange exchange = new TopicExchange(RabbitNamespace.EXCHANGE_NAME, true, false);
		configRabbit(rabbitAdmin, exchange);
		return exchange;
	}

	@Bean
	public Queue queue(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(RabbitNamespace.QUEUE_NAME, true, false, false, Map.of("x-message-ttl", 60000));
		configRabbit(rabbitAdmin, queue);
		return queue;
	}

	@Bean
	public Binding binding(RabbitAdmin rabbitAdmin, TopicExchange exchange, Queue queue) {
		Binding binding = BindingBuilder.bind(queue).to(exchange).with("#");
		configRabbit(rabbitAdmin, binding);
		return binding;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		log.error(connectionFactory.getHost() + " " + connectionFactory.getPort()
				+ " " + connectionFactory.getUsername() + " " +
				connectionFactory.getVirtualHost());
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	@ConfigurationProperties("spring.rabbitmq")
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory();
	}

	public void configRabbit(RabbitAdmin rabbitAdmin, AbstractExchange exchange) {
		rabbitAdmin.declareExchange(exchange);
	}

	public void configRabbit(RabbitAdmin rabbitAdmin, Queue queue) {
		rabbitAdmin.declareQueue(queue);
	}

	public void configRabbit(RabbitAdmin rabbitAdmin, Binding binding) {
		rabbitAdmin.declareBinding(binding);
	}

}
