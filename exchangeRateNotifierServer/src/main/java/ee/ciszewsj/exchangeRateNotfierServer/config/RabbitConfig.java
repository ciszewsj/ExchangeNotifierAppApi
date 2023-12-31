package ee.ciszewsj.exchangeRateNotfierServer.config;

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
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
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
}
