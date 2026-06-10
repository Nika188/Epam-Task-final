package com.epam.rd.autotasks.springstatefulcalc;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalcService {

    public int getResultCalc(CalcSession session) {

        if (session.getExpression() == null) {
            throw new ApiException(HttpStatus.CONFLICT, "Expression is not set");
        }

        try {
            String expr = session.getExpression();
            Map<String, Integer> vars = resolveVariables(session);
            int result = calculate(expr, vars);

            return result;
        } catch (IllegalStateException e) {
            throw new ApiException(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    private Map<String, String> getVarsStr(CalcSession session) {
        Map<String, String> varsStr = session.getVariables();

        if (varsStr == null) {
            varsStr = new HashMap<>();
            session.setVariables(varsStr);
        }

        return varsStr;
    }

    private Map<String, Integer> resolveVariables(CalcSession session) {
        Map<String, String> varsStr = getVarsStr(session);
        Map<String, Integer> values = new HashMap<>();
        Set<String> resolving = new HashSet<>();

        for (String name : varsStr.keySet()) {
            resolve(name, varsStr, values, resolving);
        }
        return values;
    }

    private int resolve(String name, Map<String, String> varsStr, Map<String, Integer> values, Set<String> resolving) {

        if (values.containsKey(name)) {
            return values.get(name);
        }
        if (!resolving.add(name)) {
            throw new IllegalArgumentException("Circular reference");
        }

        String raw = varsStr.get(name);
        if (raw == null) {
            throw new IllegalArgumentException("Undefined variable");
        }

        int value;
        if (raw.matches("-?\\d+")) {
            value = Integer.parseInt(raw);
        } else if (raw.matches("[a-z]")) {
            value = resolve(raw, varsStr, values, resolving);
        } else {
            throw new IllegalArgumentException("Invalid variable");
        }

        resolving.remove(name);
        values.put(name,value);
        return value;
    }

    int calculate(String expr, Map<String, Integer> vars) {
        class Parser {
            private final String s;
            private int pos = 0;

            Parser(String s) {
                this.s = s.replaceAll("\\s+", "");
            }

            int parse() {
                int value = parseExpression();
                if (pos != s.length()){
                    throw new IllegalArgumentException("Unexpected character at position " + pos);
                }
                return value;
            }

            int parseExpression() {
                int value = parseTerm();
                while (pos < s.length()) {
                    char c = s.charAt(pos);
                    if (c == '+') {
                        pos++;
                        value += parseTerm();
                    } else if (c == '-') {
                        pos++;
                        value -= parseTerm();
                    } else {
                        break;
                    }
                }
                return value;
            }

            int parseTerm() {
                int value = parseFactor();
                while (pos < s.length()) {
                    char c = s.charAt(pos);
                    if (c == '*') {
                        pos++;
                        value *= parseFactor();
                    } else if (c == '/') {
                        pos++;
                        value /= parseFactor();
                    } else {
                        break;
                    }
                }
                return value;
            }

            int parseFactor() {
                char c = s.charAt(pos);

                if (c == '(') {
                    pos++;
                    int value = parseExpression();
                    if (s.charAt(pos) != ')') {
                        throw new IllegalArgumentException("Missing ')'");
                    }
                    pos++;
                    return value;
                }

                if (Character.isLetter(c)){
                    int start = pos;
                    while (pos < s.length() && Character.isLetter(s.charAt(pos))){
                        pos++;
                    }
                    String var = s.substring(start, pos);
                    if (!vars.containsKey(var)) {
                        throw new IllegalStateException("Unknown variable: " + var);
                    }
                    return vars.get(var);
                }
                if (Character.isDigit(c)){
                    int start = pos;
                    while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
                        pos++;
                    }
                    return Integer.parseInt(s.substring(start, pos));
                }
                throw new IllegalArgumentException("Unexpected character: " + c);
            }
        }

        return new Parser(expr).parse();
    }
}