package koksao;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FileConverterTest {
    private String changedOrderCsvPath = "src/test/resources/changed-order.csv";
    private String currencyRatesTestCsvPath = "src/test/resources/currency-rates-test.csv";

    @Test
    void validRateConversion() throws CsvValidationException, IOException, CurrencyNotFoundException {
        FileConverter converter = new FileConverter(currencyRatesTestCsvPath);
        assertEquals(6.752, converter.convert(20., "PLN", "CAD"), 0.0001);
    }

    @Test
    void invalidRateConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(currencyRatesTestCsvPath);
        assertThrows(CurrencyNotFoundException.class, () -> converter.convert(20., "PLN", "EUR"));

    }

    @Test
    void validRateConversionChangedOrder() throws CsvValidationException, CurrencyNotFoundException, IOException {
        FileConverter converter = new FileConverter(changedOrderCsvPath);
        assertEquals(6.752, converter.convert(20., "PLN", "CAD"), 0.0001);
    }

    @Test
    void rateEquals0orLess() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(currencyRatesTestCsvPath);
        assertThrows(CurrencyNotFoundException.class, () -> converter.convert(50., "PLN", "DKK"));
    }

    @Test
    void invertedCurrenciesConversion() throws CsvValidationException, IOException, CurrencyNotFoundException {
        FileConverter converter = new FileConverter(changedOrderCsvPath);
        assertEquals(59.24, converter.convert(20., "CAD", "PLN"), 0.01);
        assertEquals(90.74, converter.convert(20., "CHF", "PLN"), 0.01);
    }
}