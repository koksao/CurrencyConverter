package koksao;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FileConverterTest {
    @Test
    void validRateConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter("src/test/resources/currency-rates-test.csv");
        assertEquals(6.752, converter.convert(20., "PLN", "CAD"), 0.0001);
    }
    @Test
    void invalidRateConversion() throws CsvValidationException, IOException {
        FileConverter converter = new FileConverter("src/test/resources/currency-rates-test.csv");
        IllegalArgumentException expectedException =
                assertThrows(IllegalArgumentException.class, () -> converter.convert(20., "PLN", "EUR"));
        assertEquals("Currency not found", expectedException.getMessage());
    }

}