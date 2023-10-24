package ee.ciszewsj.exchangeratecommondata.repositories.currencies;

import ee.ciszewsj.exchangeratecommondata.dto.CurrencyEntity;
import ee.ciszewsj.exchangeratecommondata.exceptions.WrongQuerySizeException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface CurrenciesRateFirestoreInterface {
	void updateCurrenciesDocument(List<List<String>> currencies) throws ExecutionException, InterruptedException;

	void setCurrenciesIsMainVariable(Map<String, Boolean> props) throws ExecutionException, InterruptedException, WrongQuerySizeException;

	List<CurrencyEntity> getCurrencies() throws ExecutionException, InterruptedException;
}
