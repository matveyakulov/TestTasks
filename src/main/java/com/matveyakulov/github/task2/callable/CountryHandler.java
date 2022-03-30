package com.matveyakulov.github.task2.callable;

import com.matveyakulov.github.task2.domain.Country;
import com.matveyakulov.github.task2.parser.MultiThreadHtmlParser;
import com.matveyakulov.github.task2.writer.FileWriter;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class CountryHandler implements Callable<Country> {

   private String countryName;
   private String uri;
   private int i;

    @Override
    public Country call() {
        Map<String, Double> services = MultiThreadHtmlParser.getServicesFromCountry(uri, i);
        Country country = new Country(countryName, services);
        FileWriter.writeCountry(country);
        return country;
    }
}
