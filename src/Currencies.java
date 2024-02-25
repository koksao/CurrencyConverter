import java.util.*;

public class Currencies {
    private Map<String, Map<String, Double>> rates = new HashMap<>();

    public void addCurrency(String currency) {
        rates.put(currency, new HashMap<>());
    }

    public void addRates(String from, String to, Double rate) {
        rates.get(from).put(to, rate);
        rates.get(to).put(from, (1 / rate));
    }

    public double convert(Integer amount, String from, String to) {
        double rate = rates.get(from).get(to);
        double result = rate * amount;
        return result;
    }
}
