package koksao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileConverter implements Converter {
    private Map<String, Map<String, Double>> rates = new HashMap<>();

    public FileConverter(String filePath) throws IOException, CsvValidationException {
        if (filePath != "") {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] nextline = reader.readNext();
            while ((nextline = reader.readNext()) != null) {
                addCurrency(nextline[0], nextline[1], Double.parseDouble(nextline[2]));
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
        if (!rates.containsKey(from) || !rates.get(from).containsKey(to)) {
            throw new IllegalArgumentException("Currency not found");
        }
        double rate = rates.get(from).get(to);
        return rate * amount;
    }
}
