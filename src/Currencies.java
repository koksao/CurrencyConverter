import java.util.*;
public class Currencies {
    Map<String, Map<String,Double>> rates;
    Currencies(){
        rates= new HashMap<>();
    }
    public void addCurrency (String currency){
        rates.put(currency,new HashMap<>());
    }
    public void addRates(String x, String y, Double i){
        rates.get(x).put(y,i);
        rates.get(y).put(x,(1 / i));
    }

    public String convert (Integer amountx, String x, String y){
        double rate = rates.get(x).get(y);
        double result = rate * amountx;
        String round = String.format("%.2f", result);

        return round;
    }
}
