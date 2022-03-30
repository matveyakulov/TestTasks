package com.matveyakulov.github.task1.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullCountryData {

    private String number;
    private int countryId;
    private String updatedAt;
    private String dataHumans;
    private String fullNumber;
    private String maxDate;
    private String status;

    @Override
    public String toString() {
        return "\n{\n" +
                "number='" + number + "',\n" +
                "country='" + countryId + "',\n" +
                "updated_at='" + updatedAt + "',\n" +
                "data_humans='" + dataHumans + "',\n" +
                "full_number='" + fullNumber + "',\n" +
                "maxdate='" + maxDate + "',\n" +
                "status='" + status + "',\n" +
                "}";
    }
}
