import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Currencies currencies = new Currencies();

        currencies.addCurrency("PLN", "CAD", 0.3376);
        currencies.addCurrency("PLN", "CHF", 0.2204);
        currencies.addCurrency("PLN", "EUR", 0.2313);
        currencies.addCurrency("PLN", "DKK", 1.7241);

        System.out.println(String.format("%.2f", currencies.convert(50, "PLN", "CAD")));


    }
}