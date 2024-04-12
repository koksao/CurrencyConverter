package koksao;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, ParseException, CurrencyNotFoundException {
        Options options = new Options();
        options.addOption("r", "rates", true, "path to a file");
        options.addOption("t", "token", true, "api token");

        Converter converter;

        CommandLineParser clp = new DefaultParser();
        CommandLine cl = clp.parse(options, args);

        if (cl.hasOption("r")) {
            String filePath = cl.getOptionValue("r");
            converter = new FileConverter(filePath);
        } else if (cl.hasOption("t")) {
            converter = new ApiConverter(cl.getOptionValue("t"));
        } else {
            throw new IllegalArgumentException("please provide path to file or token for api");
        }

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
            double amount = scanner.nextDouble();

            System.out.println(String.format("%.2f", converter.convert(amount, firstCurrency, secondCurrency)));

            System.out.println("If you want to end program type Exit, if not type anything");
            answer = scanner.next();
            if (answer.equals("Exit")) {
                System.exit(0);
            } else {
                System.out.println();
            }
        }
    }


}