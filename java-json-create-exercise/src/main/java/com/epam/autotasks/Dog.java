package com.epam.autotasks;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Dog extends Animal {

    private Breed breed;

    public enum Breed {

        SHEPHERD(0),
        BULLDOG(1),
        LABRADOR(2),
        HUSKY(3),
        MALAMUTE(4);

        final int code;

        Breed(int code) {
            this.code = code;
        }

        public static Breed getBreedByCode(int code) {
            for (Breed breed : values()) {
                if (breed.code == code) {
                    return breed;
                }
            }
            return null;
        }
    }
}
