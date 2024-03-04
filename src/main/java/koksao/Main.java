package koksao;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.cli.*;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, ParseException {
        Options options = new Options();
        options.addRequiredOption("r", "rates", true, "path to a file");
        String filePath = "";

        CommandLineParser clp = new DefaultParser();
        CommandLine cl = clp.parse(options, args);
        filePath = cl.getOptionValue("r");

        Currencies currencies = new Currencies(filePath);

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
            System.out.println(String.format("%.2f", currencies.convert(amount, firstCurrency, secondCurrency)));

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