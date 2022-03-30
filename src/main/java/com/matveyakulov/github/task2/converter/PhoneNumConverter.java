package com.matveyakulov.github.task2.converter;

public class PhoneNumConverter {

    public static double getNumber(String phone){

        int index = phone.indexOf('р');
        String number = phone.substring(0, index);
        return Double.parseDouble(number);
    }
}
