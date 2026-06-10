package com.epam.rd.autotasks.springstatefulcalc;

import java.util.HashMap;
import java.util.Map;

public class CalcSession {

    private String expression;
    private Map<String, String> variables = new HashMap<>();

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
