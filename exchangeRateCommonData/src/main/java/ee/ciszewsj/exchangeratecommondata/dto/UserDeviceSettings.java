package ee.ciszewsj.exchangeratecommondata.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDeviceSettings {
	public static final String UPDATE_TIME = "update_time";
	public static final String DEVICE_TOKEN = "device_token";

	@JsonProperty(UPDATE_TIME)
	private Date lastUpdateTime;
	@JsonProperty(DEVICE_TOKEN)
	private String token;
}
