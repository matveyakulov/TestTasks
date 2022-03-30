package com.matveyakulov.github.task1.parser;

import com.matveyakulov.github.task1.domain.CountryData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryParser {

    public static List<CountryData> getCountries(String body) {

        List<CountryData> countryDataList = new ArrayList<>();
        JSONObject jsonBody = new JSONObject(body);
        JSONArray countries = (JSONArray) jsonBody.get("countries");
        for (int i = 0; i < countries.length(); i++) {
            JSONObject country = countries.getJSONObject(i);
            int id = country.getInt("country");
            String name = country.getString("country_text");
            countryDataList.add(new CountryData(id, name));
        }
        return countryDataList;
    }
}
