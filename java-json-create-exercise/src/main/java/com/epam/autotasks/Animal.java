package com.epam.autotasks;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Animal {

    private String name;
    private Integer age;

}
