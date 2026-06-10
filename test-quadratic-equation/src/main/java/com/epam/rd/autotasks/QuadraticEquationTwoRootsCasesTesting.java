package com.epam.rd.autotasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuadraticEquationTwoRootsCasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();
    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(2,5,-3,"-3.0 0.5"),
                Arguments.of(1,-3,1,"0.3819660112501051 2.618033988749895"),
                Arguments.of(2,-38,156,"6.0 13.0"),
                Arguments.of(-0.5,34,1046.5,"-23.0 91.0")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void testCase(double a, double b, double c, String expected) {
        String[] parts = expected.split(" ");
        String reversed = parts[1] + " " + parts[0];
        String result = quadraticEquation.solve(a, b, c);
        assertTrue(
                result.equals(expected) ||
                        result.equals(reversed),
                () -> "Expected roots: " + expected + " but got: " + result
        );


    }
}
