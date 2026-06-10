package com.epam.rd.autotasks;

import java.math.BigInteger;

public class Factorial {
    public String factorial(String n) {
        if (n == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (!n.matches("\\d+")) {
            throw new IllegalArgumentException("Input must be a non-negative integer");
        }

        int value;
        try {
            value = Integer.parseInt(n);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input is too large to parse as integer");
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= value; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result.toString();
    }
}
