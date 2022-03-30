package com.matveyakulov.github.task2.mapper;

import com.matveyakulov.github.task2.converter.PhoneNumConverter;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneNumMapper {

    public static Map<String, Double> getPhoneData(List<WebElement> serviceNames, List<WebElement> servicePrice){

        Map<String,Double> phoneData = new HashMap<>();
        for (int i = 0; i < serviceNames.size(); i++) {
            String service = serviceNames.get(i).getText();
            double phone = PhoneNumConverter.getNumber(servicePrice.get(i).getText());

            phoneData.put(service, phone);
        }
        return phoneData;

    }
}
