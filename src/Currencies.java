import java.util.*;

public class Currencies {
    private Map<String, Map<String, Double>> rates = new HashMap<>();

    public void addCurrency(String from, String to, Double rate) {
        if (!rates.containsKey(from)) {
            rates.put(from, new HashMap<>());
        }
        if (!rates.containsKey(to)) {
            rates.put(to, new HashMap<>());
        }
        rates.get(from).put(to, rate);
        rates.get(to).put(from, (1 / rate));
    }
    public double convert(Integer amount, String from, String to) {
        if (!rates.containsKey(from) || !rates.get(from).containsKey(to)) {
            throw new IllegalArgumentException("Currency not found");
        }
        double rate = rates.get(from).get(to);
        double result = rate * amount;
        return result;
    }
}
