package com.matveyakulov.github.task1.parser;

import com.matveyakulov.github.task1.domain.CountryData;
import com.matveyakulov.github.task1.domain.FullCountryData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ResponceParser {

    public static Map<String, List<FullCountryData>> parseDataFromURI(String path) {

        Map<String, List<FullCountryData>> result = new HashMap<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(path))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<CountryData> countries = CountryParser.getCountries(response.body());
            for (CountryData countryData : countries) {
                result.put(countryData.getText(), PhoneNumParser.getNumber(countryData.getId()));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

}
