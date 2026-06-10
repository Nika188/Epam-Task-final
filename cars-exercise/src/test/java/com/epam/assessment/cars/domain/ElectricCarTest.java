package com.epam.assessment.cars.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElectricCarTest {
    private ElectricCar teslaCar;

    @BeforeEach
    public void setUp() {
        ElectricModel teslaModel = new ElectricModel("Tesla", "Model 3 RWD", 208, 60, 20);
        teslaCar = new ElectricCar("AA-BP-022", teslaModel, 60);
    }

    @Test
    public void testDrive100km() {
        int distance = 100;

        teslaCar.drive(distance);

        Assertions.assertEquals(40, teslaCar.getBatteryLevel(),
            "Battery level should decrease to 40 kWh after driving 100 km.");
        Assertions.assertEquals(100, teslaCar.getDistanceDriven(),
            "Distance driven should be 100 km.");
    }

    @Test
    public void testDrive50km() {
        int distance = 50;

        teslaCar.drive(distance);

        Assertions.assertEquals(50, teslaCar.getBatteryLevel(),
            "Battery level should decrease to 50 kWh after driving 50 km.");
        Assertions.assertEquals(50, teslaCar.getDistanceDriven(),
            "Distance driven should be 50 km.");
    }

    @Test
    public void testDrive400kmRaisesError() {
        int distance = 400;

        Assertions.assertThrows(IllegalStateException.class,
            () -> teslaCar.drive(distance),
            "Driving 400 km should throw an IllegalStateException due to insufficient battery.");
    }

    @Test
    public void testDrive100kmAnd50km() {
        int distance1 = 100;
        int distance2 = 50;
        teslaCar.drive(distance1);
        teslaCar.drive(distance2);

        Assertions.assertEquals(30, teslaCar.getBatteryLevel(),
            "Battery level should decrease to 30 kWh after driving 150 km.");
        Assertions.assertEquals(150, teslaCar.getDistanceDriven(),
            "Distance driven should be 150 km.");
    }
}