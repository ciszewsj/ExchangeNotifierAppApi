package ee.ciszewsj.exchangerateupdaterservice.data.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "newest_data_rate")
@AllArgsConstructor
@NoArgsConstructor
public class NewestCurrencyRateEntity {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "main_currency")
	private String mainCurrency;

	@Column(name = "second_currency")
	private String secondCurrency;

	@Column(name = "exchange_rate")
	private Double exchangeRate;

	@Column(name = "updated")
	private Date updated;

	public NewestCurrencyRateEntity(String mainCurrency, String secondCurrency) {
		this.mainCurrency = mainCurrency;
		this.secondCurrency = secondCurrency;
	}

}
