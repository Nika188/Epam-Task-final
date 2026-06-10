package com.epam.rd.autotasks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuadraticEquationZeroACasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(0,5,-30),
                Arguments.of(0,-3,10),
                Arguments.of(0,-38,1560),
                Arguments.of(0,34,1046.5)
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void testCase(double a, double b, double c) {
        assertThrows(IllegalArgumentException.class, () -> quadraticEquation.solve(a, b, c));
    }
}
