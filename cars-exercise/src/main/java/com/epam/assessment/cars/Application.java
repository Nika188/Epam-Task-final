package com.epam.assessment.cars;

import com.epam.assessment.cars.domain.ElectricCar;
import com.epam.assessment.cars.domain.ElectricModel;
import com.epam.assessment.cars.domain.Model;
import com.epam.assessment.cars.logic.ModelsCollection;

import java.util.Optional;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();

        System.out.println("Loading models from CSV file...");
        application.load("input/models.csv");

        application.queryModel();

        // Simulate driving a Tesla Model 3 and charging it
        application.teslaDrive();
    }

    private final ModelsCollection modelsCollection = new ModelsCollection();

    private void queryModel() {
        System.out.println("Querying electric models with battery capacity larger than 60 kWh");
        modelsCollection.getElectricModelsByBatteryCapacity(60)
                .forEach(System.out::println);
    }

    public void load(String filePath) {
        modelsCollection.readFromFile(filePath);
    }

    public void teslaDrive() {
        System.out.println("Simulating Tesla Model 3 RWD driving...");
        Optional<Model> optionalModel = modelsCollection.getModel("Tesla", "Model 3 RWD");
        if (optionalModel.isPresent()) {
            ElectricModel tesla = (ElectricModel) optionalModel.get();
            ElectricCar car = new ElectricCar("AA-BP-022", tesla, 60);
            car.drive(100);
            car.drive(150);
        }
    }
}
