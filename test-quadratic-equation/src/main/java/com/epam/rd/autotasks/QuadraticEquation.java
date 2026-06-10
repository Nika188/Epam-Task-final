package com.epam.rd.autotasks;

public class QuadraticEquation {

    public String solve(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Coefficient 'a' cannot be zero");
        }
        double x1, x2;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return "no roots";
        } else if (discriminant == 0) {
            x1 = -b / (2 * a);
            return String.valueOf(x1);
        } else {
            double sqrtD = Math.sqrt(discriminant);
            x1 = (-b - sqrtD) / (2 * a);
            x2 = (-b + sqrtD) / (2 * a);
            return x1 + " " + x2;
        }

    }
}