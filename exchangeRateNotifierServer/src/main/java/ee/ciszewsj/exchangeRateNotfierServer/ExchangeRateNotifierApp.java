package ee.ciszewsj.exchangeRateNotfierServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeRateNotifierApp {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateNotifierApp.class, args);
	}

}
