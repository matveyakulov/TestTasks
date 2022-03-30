package com.matveyakulov.github.task2.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matveyakulov.github.task2.domain.Country;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileWriter {

    private static final String path = "src/main/resources/files/";
    private static final String format = ".txt";
    private static final String resultFileName = "Все страны";
    private static final Logger log = Logger.getLogger(FileWriter.class);

    private static void createFile(String fileName){

        File dir = new File(path);
        dir.mkdirs();
        File file = new File(path + fileName + format);
        try {
            file.createNewFile();
            log.info("Создан файл '" + fileName + format + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCountry(Country country){

        log.info("Старт заполнения файла '" + country.getCountryName() + format + "'");
        createFile(country.getCountryName());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(country.getServices());
            byte[] bs = json.getBytes();
            Files.write(Paths.get(path + country.getCountryName() + format), bs);
            log.info("Файл '" + country.getCountryName() + format + "' заполнен успешно");

        } catch (IOException e) {
            log.info("Ошибка заполнения файла '" + country.getCountryName() + format + "'");
            e.printStackTrace();
        }


    }

    public static void writeAllCountry(Map<String, Map<String, Double>> resultData) {

        log.info("Старт заполнения файла '" + resultFileName + format + "'");
        createFile(resultFileName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(resultData);
            byte[] bs = json.getBytes();
            Files.write(Paths.get(path + resultFileName + format), bs);
            log.info("Файл '" + resultFileName + format + "' заполнен успешно");

        } catch (IOException e) {
            log.info("Ошибка заполнения файла '" + resultFileName + format + "'");
            e.printStackTrace();
        }
    }
}
