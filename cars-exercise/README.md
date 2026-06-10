## Task Overview

The application simulates a car model management system. It loads different types of cars (regular and electric)
from a file, provide query functionality to retrieve specific models, and simulate driving an electric car.

Implementing the application is estimated to take approximately **4 hours**.

After cloning the repository, you will find several partially implemented classes in the project directory.
The main class of the project is `com.epam.assessment.cars.Application`.
You can start the application; it will run without errors, but it is not yet functional.
You will need to implement the missing functionality step by step.

Unit tests are provided in the project to verify that the main classes satisfy the requirements
specified in this document.


# Step 1: Implement the Model Classes

The `Model` class represents a car model that is described by the `manufacturer`, `modelName`, `power` attributes.
Example values: **Opel** (manufacturer), **Astra K** (model name), **81 kW** (power).

`RegularModel` is a subclass of `Model` that represent cars running on either petrol or gasoline engines.
It extends the `Model` class with additional attributes: `engine displacement` and `fuel consumption`.

Example value of the added attributes: **1199 cc** (engine displacement), **5.2 l** / 100 km (fuel consumption).

`ElectricModel` is another subclass of `Model` which represents electric cars.
It extends the `Model` base class with additional attributes: `batteryCapacity` and `energyConsumption`.

Example values: **60 kWh** (battery capacity), **20 kWh** / 100 km (energy consumption).


![Models diagram](https://github.com/viktorselmeci/exercise-images/blob/53a699c77df0b6fe65b6bb748b481078ec519d0b/cars/model.png?raw=true)

Implement the `Model`, `RegularModel` and `ElectricModel` classes according to the UML diagram above.


# Step 2: Load the Models

## Input file format

`input/models.csv` file contains some already defined models, both regular cars and electric cars.

Each CSV line starts with the model type: either **"Regular"** or **"Electric"**.

The additional values of the CSV depends on the model type:

1. Regular models have the following attributes:
   - manufacturer
   - model name
   - power (in kW)
   - engine displacement (cubic centimeters)
   - fuel consumption (in liters per 100 km)

Example line:
```text
Regular,Opel,Astra K,81,1199,5.2
```

2. Electric models have the following attributes:
   - manufacturer
   - model name
   - power (in kW)
   - battery capacity (in kWh)
   - energy consumption (in kWh per 100 km)

Example line:
```text
Electric,Tesla,Model 3 RWD,208,60,20
```

## Model Parser

First, implement the `ModelParser` class, which reads a single line
and creates either a `RegularModel` or `ElectricModel` instance based on the type specified in the input.

Implement the following validations:

- If the type is not "Regular" or "Electric", throw an `IllegalArgumentException`.
  The exception message should be: _"Unknown type: xyz"_.
- If the number of attributes is incorrect for the specified type, throw `IllegalArgumentException`.
  The exception message should be: _"Invalid number of parameters. Expected 6, received: xyz"_.

**Hint**
- You can run the `ModelParserTest` to check if your implementation is correct.


## Models Collection

`ModelsCollection` is the class that manages a collection of models.
It also offers some model query functionality (see later).

Implement `ModelsCollection` class's `readFromFile` method that reads the input file and create a collection of models.

**Hint**
- Use the `ModelParser` in `ModelsCollection` to parse each line of the input file.

**Error handling**
- If an I/O error occurs while reading the file, throw a `RuntimeException`.
  The exception message should be: _"Error occurred while reading file: xyz"_.


# Step 3: Implement Query Functionality

## Text representation and comparing of models

First, implement the `toString` method in the `Model` class and its subclasses
that returns a textual representation of the model.

Example output for an electric model:

```text
ElectricModel{manufacturer='Tesla', modelName='Model 3 RWD', power=208, batteryCapacity=60, energyConsumption=20}
```

Also, implement **natural ordering** for the `Model` class based on the following fields:
`manufacturer` and `modelName`.

In this step, you will implement several query methods for the `ModelsCollection` class.
Implement the query methods using the Java 8 **Stream API**.

## getModel method

Implement the `getModel` method, which takes `manufacturer` and `modelName` as parameters
and returns the model matching these parameters.

If no model is found, return an empty `Optional` object.

## getElectricModelsByBatteryCapacity method

Implement the `getElectricModelsByBatteryCapacity` method, which takes a `batteryCapacityLimit` as a parameter,
and returns a list of electric models with battery capacity **greater than or equal** to the specified limit.
The result should be sorted by the natural ordering of `Model`.

## Testing

Run the Application to see the results of the implemented methods.


# Step 4: Car Implementation

In this step, you will implement the `Car` and `ElectricCar` classes.

A `Car` represents a specific vehicle of a given model. It references a `Model`, it has a unique `licensePlate` field,
and tracks the distance it has driven (`distanceDriven`).

As with models, there are two types of cars: **regular** and **electric**.

Note
- The `Car` class is an abstract class, so it can not be instantiated directly. It defines drive method abstractly.

![Cars Diagram](https://github.com/viktorselmeci/exercise-images/blob/53a699c77df0b6fe65b6bb748b481078ec519d0b/cars/extended-model.png?raw=true)

## ElectricCar's drive() method

The `drive()` method takes a `distance` parameter, representing the number of kilometers to drive.
The method should update the car's state based on the distance driven.

Increment the `distanceDriven` field by the value of the `distance` parameter.

Update the `batteryLevel` field as follows:

**Formula**

Calculate energy consumption using the model's `energyConsumption` value.
This value represents the energy consumed by the car over **100 km**.

1. Calculate energy consumption per kilometer: `energyConsumption / 100`
2. Calculate total energy consumption for the distance: multiply the per-kilometer value by the `distance` parameter.
3. Subtract the calculated energy consumption from the car's `batteryLevel`.

**Hint**
- Perform the calculation using floating-point arithmetic, then round and convert the result to an integer.

**Validation**
- If the `batteryLevel` would become negative, throw `IllegalArgumentException`.

**Note**
- You can use `ElectricCarTest` to check if your implementation is correct.

**Console messages**
- Print messages to the console when the car drives.

## Testing

Run your application to see the car's changes. Expected output:

```text
Simulating Tesla Model 3 RWD driving...
Driving for 100 km
    Odometer: 100 km
    Consumption: 20 kWh
    Battery level: 40 kWh
Driving for 150 km
    Odometer: 250 km
    Consumption: 30 kWh
    Battery level: 10 kWh
```

# Exercise Completion

The project contains unit tests to validate your solution's correctness. You have to pass 85% of them.
