package com.epam.assessment.cars.domain;

public class Model {
    private String manufacturer;
    private String modelName;
    private int power;
    public Model(String manufacturer, String modelName, int power) {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.power = power;
    }
    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getModelName() {
        return this.modelName;
    }

    public int getPower() {
        return this.power;
    }
    @Override
    public String toString() {
        return "Model{" +
                "manufacturer='" + manufacturer + '\'' +
                ", modelName='" + modelName + '\'' +
                ", power=" + power +
                '}';
    }
}
