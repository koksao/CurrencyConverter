package koksao;

import java.io.IOException;


public interface Converter {
    double convert(Double amount, String from, String to) throws CurrencyNotFoundException, IOException;
}
