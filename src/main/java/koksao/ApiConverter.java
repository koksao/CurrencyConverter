package koksao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConverter implements Converter {

    private final String token;

    public ApiConverter(String token) {
        this.token = token;
    }

    @Override
    public double convert(Double amount, String from, String to) throws IOException {

        String apiServerAddress = "https://v6.exchangerate-api.com/v6";
        String GET_URL = apiServerAddress + "/" + token + "/pair/" + from + "/" + to + "/" + amount;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(httpURLConnection.getInputStream()), JsonObject.class);
            return jsonObject.get("conversion_result").getAsDouble();

        } else {
            throw new IllegalStateException("Conversion failed: received response code " + responseCode + " from Api.");
        }
    }
}
