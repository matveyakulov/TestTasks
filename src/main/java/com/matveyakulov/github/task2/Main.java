package com.matveyakulov.github.task2;

import com.matveyakulov.github.task2.parser.MultiThreadHtmlParser;
import com.matveyakulov.github.task2.parser.SingleThreadHtmlParser;

public class Main {

    public static void main(String[] args) {

        String URI = "https://onlinesim.ru/price-list";
        //SingleThreadHtmlParser.getServicesFromUri(URI);
        MultiThreadHtmlParser.getServicesFromUri(URI);

    }
}
