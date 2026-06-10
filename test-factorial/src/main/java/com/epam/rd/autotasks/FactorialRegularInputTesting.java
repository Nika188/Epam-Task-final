package com.epam.rd.autotasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialRegularInputTesting {

    Factorial factorial = new Factorial();
    @Test
    void testZero() {
        assertEquals("1", factorial.factorial("0"));
    }

    @Test
    void testLargeNumber() {
        // 10! = 3628800
        assertEquals("3628800", factorial.factorial("10"));
    }

    @Test
    void testSingleDigit() {
        assertEquals("6", factorial.factorial("3"));
        assertEquals("120", factorial.factorial("5"));
    }
}
