package ee.ciszewsj.exchangeRateNotfierServer.config.firebase;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.firebase")
public class FirebaseProperties {
	private String projectId;

	private String serviceAccountKey;

	private String databaseUrl;
}
