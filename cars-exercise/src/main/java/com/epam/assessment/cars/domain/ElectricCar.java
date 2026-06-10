package com.epam.assessment.cars.domain;

public class ElectricCar extends Car {
    private int batteryLevel;
    public ElectricCar(String licensePlate, ElectricModel electricModel, int batteryLevel) {
        super(licensePlate, electricModel);
        this.batteryLevel =batteryLevel;
    }

    @Override
    public void drive(int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be positive.");
        }

        ElectricModel model = (ElectricModel) getModel();
        double consumptionPerKm = model.getEnergyConsumption() / 100.0;
        double totalConsumption = consumptionPerKm * distance;

        int roundedConsumption = (int) Math.round(totalConsumption);

        if (batteryLevel - roundedConsumption < 0) {
            throw new IllegalStateException("Not enough battery to drive " + distance + " km.");
        }

        batteryLevel -= roundedConsumption;
        addDistance(distance);

        System.out.println(getLicensePlate() + " drove " + distance + " km. Battery now at " + batteryLevel + " kWh.");
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }
}
