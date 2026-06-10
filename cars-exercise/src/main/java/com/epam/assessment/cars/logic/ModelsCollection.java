package com.epam.assessment.cars.logic;

import com.epam.assessment.cars.domain.ElectricModel;
import com.epam.assessment.cars.domain.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelsCollection {
    private List<Model> models;
    public ModelsCollection() {
        this.models = new ArrayList<>();
    }
    public void readFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Model model = ModelParser.parse(line);
                    models.add(model);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading file: " + filePath);
        }
    }

    public Optional<Model> getModel(String manufacturer, String modelName) {
        return models.stream()
                .filter(m -> m.getManufacturer().equalsIgnoreCase(manufacturer)
                        && m.getModelName().equalsIgnoreCase(modelName))
                .findFirst();
    }

    public List<ElectricModel> getElectricModelsByBatteryCapacity(int batteryCapacityLimit) {
        return models.stream()
                .filter(m -> m instanceof ElectricModel)
                .map(m -> (ElectricModel) m)
                .filter(em -> em.getBatteryCapacity() >= batteryCapacityLimit)
                .sorted(Comparator.comparing(Model::getManufacturer)
                        .thenComparing(Model::getModelName))
                .collect(Collectors.toList());
    }
}
