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
    void validRateConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(currencyRatesTestCsvPath);
        assertEquals(6.752, converter.convert(20., "PLN", "CAD"), 0.0001);
    }

    @Test
    void invalidRateConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(currencyRatesTestCsvPath);
        IllegalArgumentException expectedException =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(20., "PLN", "EUR"));
        assertEquals("Currency not found", expectedException.getMessage());
    }

    @Test
    void validRateConversionChangedOrder() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(changedOrderCsvPath);
        assertEquals(6.752, converter.convert(20., "PLN", "CAD"), 0.0001);
    }

    @Test
    void amountEquals0() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(changedOrderCsvPath);
        IllegalArgumentException expectedException =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(0., "PLN", "CAD"));
        assertEquals("The given amount of money equals 0", expectedException.getMessage());
    }

    @Test
    void invertedCurrenciesConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter(changedOrderCsvPath);
        assertEquals(59.24, converter.convert(20., "CAD", "PLN"), 0.01);
        assertEquals(90.74, converter.convert(20., "CHF", "PLN"), 0.01);
    }
}