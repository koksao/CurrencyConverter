package koksao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileConverter implements Converter {
    private Map<String, Map<String, Double>> rates = new HashMap<>();

    public FileConverter(String filePath) throws IOException, CsvValidationException {
        if (filePath != "") {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] nextline;
            String[] allColumnHeaders;
            int ratePosition;
            int fromCurrencyPosition;
            int toCurrencyPosition;

            allColumnHeaders = reader.readNext();
            ratePosition = getPositionUsingHeaders(allColumnHeaders, "rate");
            fromCurrencyPosition = getPositionUsingHeaders(allColumnHeaders, "from");
            toCurrencyPosition = getPositionUsingHeaders(allColumnHeaders, "to");
            while ((nextline = reader.readNext()) != null) {
                String fromCurrency = nextline[fromCurrencyPosition];
                String toCurrency = nextline[toCurrencyPosition];
                try {
                    addCurrency(fromCurrency, toCurrency, Double.parseDouble(nextline[ratePosition]));
                } catch (NumberFormatException e) {
                    System.out.println("Rate from " + fromCurrency + " to " + toCurrency + " is probably invalid. Will not load this rate");
                }
            }
        } else {
            throw new IllegalArgumentException("Empty file path provided");
        }
    }

    private void addCurrency(String from, String to, Double rate) {
        if (!rates.containsKey(from)) {
            rates.put(from, new HashMap<>());
        }
        if (!rates.containsKey(to)) {
            rates.put(to, new HashMap<>());
        }
        rates.get(from).put(to, rate);
        rates.get(to).put(from, (1 / rate));
    }

    public double convert(Double amount, String from, String to) {
        if (amount == 0) {
            throw new IllegalArgumentException("The given amount of money equals 0");
        }
        if (!rates.containsKey(from) || !rates.get(from).containsKey(to)) {
            throw new IllegalArgumentException("Currency not found");
        }
        double rate = rates.get(from).get(to);
        return rate * amount;
    }

    private int getPositionUsingHeaders(String[] allColumnHeaders, String headerName) {
        return Arrays.asList(allColumnHeaders).indexOf(headerName);
    }
}
