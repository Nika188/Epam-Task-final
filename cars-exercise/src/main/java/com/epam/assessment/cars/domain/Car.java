package com.epam.assessment.cars.domain;

public abstract class Car {

    private Model model;
    private String licensePlate;
    private int distanceDriven;
    public Car(String licensePlate, Model model) {
        this.model = model;
        this.licensePlate = licensePlate;
        this.distanceDriven = 0;
    }

    public abstract void drive(int distance);

    public Model getModel() {
        return model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public int getDistanceDriven() {
        return distanceDriven;
    }
    protected void addDistance(int distance) {
        this.distanceDriven += distance;
    }

}
