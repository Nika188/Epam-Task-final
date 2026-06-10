package com.epam.assessment.cars.logic;

import com.epam.assessment.cars.domain.ElectricModel;
import com.epam.assessment.cars.domain.Model;
import com.epam.assessment.cars.domain.RegularModel;

public class ModelParser {

    public static Model parse(String line) {
        String[] tokens = line.split(",");

        if (tokens.length == 0) {
            throw new IllegalArgumentException("Empty input line.");
        }

        String type = tokens[0];

        if (type.equals("Regular")) {
            if (tokens.length != 6) {
                throw new IllegalArgumentException("Invalid number of parameters. Expected 6, received: " + tokens.length);
            }

            String manufacturer = tokens[1];
            String modelName = tokens[2];
            int power = Integer.parseInt(tokens[3]);
            int engineDisplacement = Integer.parseInt(tokens[4]);
            double fuelConsumption = Double.parseDouble(tokens[5]);

            return new RegularModel(manufacturer, modelName, power, engineDisplacement, fuelConsumption);

        } else if (type.equals("Electric")) {
            if (tokens.length != 6) {
                throw new IllegalArgumentException("Invalid number of parameters. Expected 6, received: " + tokens.length);
            }

            String manufacturer = tokens[1];
            String modelName = tokens[2];
            int power = Integer.parseInt(tokens[3]);
            int batteryCapacity = Integer.parseInt(tokens[4]);
            int energyConsumption = Integer.parseInt(tokens[5]);

            return new ElectricModel(manufacturer, modelName, power, batteryCapacity, energyConsumption);

        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
