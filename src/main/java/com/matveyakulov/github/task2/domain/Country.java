package com.matveyakulov.github.task2.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class Country {

    private String countryName;
    private Map<String, Double> services;
}
