package com.epam.autotasks;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

public class JsonService {

    @SneakyThrows
    public void createAnimalJson(String filePath, List<Animal> animals) {
        if (filePath == null || animals == null) {
            throw new IllegalArgumentException("filePath and animals must not be null");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(filePath), animals);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
