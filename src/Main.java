import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String answer = "";
        String firstCurrency;
        String secondCurrency;

        while (!answer.equals("Exit")) {
            System.out.println("What currency you want to convert from?");
            firstCurrency = scanner.next();
            System.out.println("On which?");
            secondCurrency = scanner.next();
            System.out.println("What amount of money?");
            int amount = scanner.nextInt();

            String line;
            String csvSplitBy = ",";
            try {
                BufferedReader br = new BufferedReader(new FileReader(args[0]));
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(csvSplitBy);
                    if (data[0].equals(firstCurrency) && data[1].equals(secondCurrency)) {
                        double rate = Double.parseDouble(data[2]);
                        System.out.println(String.format("%.2f", rate * amount));
                    }
                    if (data[1].equals(firstCurrency) && data[0].equals(secondCurrency)) {
                        double rate = Double.parseDouble(data[2]);
                        System.out.println(String.format("%.2f", (1 / rate) * amount));

                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("If you want to end program type Exit, if not type No");
            answer = scanner.next();
            if (answer.equals("Exit")) {
                System.exit(0);
            } else {
                System.out.println();
            }
        }


        Currencies currencies = new Currencies();

        currencies.addCurrency("PLN");
        currencies.addCurrency("CAD");
        currencies.addCurrency("CHF");
        currencies.addCurrency("EUR");
        currencies.addCurrency("DKK");

        currencies.addRates("PLN", "CAD", 0.3376);
        currencies.addRates("PLN", "CHF", 0.2204);
        currencies.addRates("PLN", "EUR", 0.2313);
        currencies.addRates("PLN", "DKK", 1.7241);

        //System.out.println(String.format("%.2f", currencies.convert(50, "EUR", "PLN")));


    }
}