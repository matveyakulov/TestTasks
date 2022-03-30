package com.matveyakulov.github.task1.parser;

import com.matveyakulov.github.task1.domain.FullCountryData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumParser {

    private static final String countryNumPath = "https://onlinesim.ru/api/getFreePhoneList?country=";

    public static List<FullCountryData> getNumber(int countryId) {

        List<FullCountryData> countryData = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(countryNumPath + countryId))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonBody = new JSONObject(response.body());
            JSONArray numbers = (JSONArray) jsonBody.get("numbers");
            for (int i = 0; i < numbers.length(); i++) {
                JSONObject jsonNumber = numbers.getJSONObject(i);
                String number = jsonNumber.getString("number");
                int id = jsonNumber.getInt("country");
                String updatedAt = jsonNumber.getString("updated_at");
                String dataHumans = jsonNumber.getString("data_humans");
                String fullNumber = jsonNumber.getString("full_number");
                String maxDate = jsonNumber.getString("maxdate");
                String status = jsonNumber.getString("status");
                countryData.add(
                        new FullCountryData(
                                number,
                                id,
                                updatedAt,
                                dataHumans,
                                fullNumber,
                                maxDate,
                                status
                        ));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return countryData;
    }
}
