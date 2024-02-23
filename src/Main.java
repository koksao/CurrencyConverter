import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Currencies currencies = new Currencies();

        currencies.addCurrency("PLN");
        currencies.addCurrency("CAD");
        currencies.addCurrency("CHF");
        currencies.addCurrency("EUR");
        currencies.addCurrency("DKK");

        currencies.addRates("PLN", "CAD",0.3376 );
        currencies.addRates("PLN", "CHF", 0.2204);
        currencies.addRates("PLN", "EUR",0.2313);
        currencies.addRates("PLN", "DKK",1.7241);

        System.out.println(currencies.convert(50, "PLN", "EUR"));



    }
}