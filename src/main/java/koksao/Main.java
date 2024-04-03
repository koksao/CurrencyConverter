package koksao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.cli.*;

import javax.swing.text.html.Option;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException, ParseException {
        Options options = new Options();
        options.addOption("r", "rates", true, "path to a file");

        Currencies currencies = new Currencies();

        CommandLineParser clp = new DefaultParser();
        CommandLine cl = clp.parse(options, args);

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

            if (cl.hasOption("r")) {
                String filePath = "";
                filePath = cl.getOptionValue("r");
                currencies.currenciesFromFile(filePath);
                System.out.println(String.format("%.2f", currencies.convert(amount, firstCurrency, secondCurrency)));
            } else {
                sendHttpGETRequest(firstCurrency, secondCurrency, amount);
            }

            System.out.println("If you want to end program type Exit, if not type anything");
            answer = scanner.next();
            if (answer.equals("Exit")) {
                System.exit(0);
            } else {
                System.out.println();
            }
        }
    }

    private static void sendHttpGETRequest(String from, String to, Double amount) throws IOException {

        String GET_URL = "https://v6.exchangerate-api.com/v6/a4dc850dcae19d8f516e45e4/pair/" + from + "/" + to + "/" + amount;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == httpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(String.valueOf(response), JsonObject.class);
            double result = jsonObject.get("conversion_result").getAsDouble();
            System.out.println(String.format("%.2f", result));
        } else {
            System.out.println("Request failed");
        }
    }

}