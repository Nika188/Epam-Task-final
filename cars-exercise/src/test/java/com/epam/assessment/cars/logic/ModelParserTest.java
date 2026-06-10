package com.epam.assessment.cars.logic;

import com.epam.assessment.cars.domain.ElectricModel;
import com.epam.assessment.cars.domain.Model;
import com.epam.assessment.cars.domain.RegularModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModelParserTest {

    @Test
    public void testParseValidElectricModel() {
        String line = "Electric,Tesla,Model 3 RWD,208,60,20";
        Model model = ModelParser.parse(line);

        Assertions.assertTrue(model instanceof ElectricModel, "Expected an instance of ElectricModel");
        ElectricModel electricModel = (ElectricModel) model;
        Assertions.assertEquals("Tesla", electricModel.getManufacturer());
        Assertions.assertEquals("Model 3 RWD", electricModel.getModelName());
        Assertions.assertEquals(208, electricModel.getPower());
        Assertions.assertEquals(60, electricModel.getBatteryCapacity());
        Assertions.assertEquals(20, electricModel.getEnergyConsumption());
    }

    @Test
    public void testParseInvalidTypeRaisesError() {
        String line = "Diesel,BMW,320d,50,5";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ModelParser.parse(line);
        });
        Assertions.assertEquals("Unknown type: Diesel", exception.getMessage());
    }

    @Test
    public void testParseInvalidFormatRaisesError() {
        String line = "Electric,Tesla,Model 3 RWD,60";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ModelParser.parse(line));
        Assertions.assertEquals("Invalid number of parameters. Expected 6, received: 4", exception.getMessage());
    }

    @Test
    public void testParseValidRegularModel() {
        String line = "Regular,Ford,Focus EcoBoost,92,999,5.6";
        Model model = ModelParser.parse(line);

        Assertions.assertTrue(model instanceof RegularModel, "Expected an instance of RegularModel");
        RegularModel regularModel = (RegularModel) model;
        Assertions.assertEquals("Ford", regularModel.getManufacturer());
        Assertions.assertEquals("Focus EcoBoost", regularModel.getModelName());
        Assertions.assertEquals(92, regularModel.getPower());
        Assertions.assertEquals(999, regularModel.getEngineDisplacement());
        Assertions.assertEquals(5.6, regularModel.getFuelConsumption());
    }
}