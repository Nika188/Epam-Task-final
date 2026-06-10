package com.epam.assessment.cars.domain;

public class RegularModel extends Model {
    int engineDisplacement;
    double fuelConsumption;
    public RegularModel(String manufacturer, String modelName, int power, int engineDisplacement, double fuelConsumption) {
        super(manufacturer, modelName, power);
        this.engineDisplacement = engineDisplacement;
        this.fuelConsumption = fuelConsumption;
    }

    public int getEngineDisplacement() {
        return this.engineDisplacement;
    }

    public double getFuelConsumption() {
        return this.fuelConsumption;
    }
    @Override
    public String toString() {
        return "RegularModel{" +
                "manufacturer='" + getManufacturer() + '\'' +
                ", modelName='" + getModelName() + '\'' +
                ", power=" + getPower() +
                ", engineDisplacement=" + engineDisplacement +
                ", fuelConsumption=" + fuelConsumption +
                '}';
    }

}
