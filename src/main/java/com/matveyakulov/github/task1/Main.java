package com.matveyakulov.github.task1;

import com.matveyakulov.github.task1.parser.ResponceParser;

public class Main {

    public static void main(String[] args) {

        String countryURI = "https://onlinesim.ru/api/getFreeCountryList";
        System.out.println(ResponceParser.parseDataFromURI(countryURI));
    }
}
