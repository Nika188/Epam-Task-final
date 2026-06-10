package com.epam.rd.autotasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FactorialBadInputTesting {

    Factorial factorial = new Factorial();

    @Test
    void testNullInput(){
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial(null));
    }

    @Test
    void testNegativeInput(){
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("-1"));
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("-123"));
    }

    @Test
    void testFractionalInput(){
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("2.3"));
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("4.7"));
    }

    @Test
    void testNonDigitalInput(){
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("ads"));
        assertThrows(IllegalArgumentException.class, ()-> factorial.factorial("addvv42s"));
    }
}
