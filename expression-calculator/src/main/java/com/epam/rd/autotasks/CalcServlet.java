package com.epam.rd.autotasks;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String expression = req.getParameter("expression");
        if (expression == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Map<String,Integer> vars = resolveVariables(req);
            int result = calculate(expression, vars);

            resp.setContentType("text/plain");
            resp.getWriter().write(String.valueOf(result));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, Integer> resolveVariables(HttpServletRequest req) {
        Map<String, Integer> values = new HashMap<>();
        Set<String> resolving = new HashSet<>();

        for (String name : req.getParameterMap().keySet()) {
            if (name.matches("[a-z]")) {
                resolve(name, req, values, resolving);
            }
        }
        return values;
    }

    private int resolve(String name, HttpServletRequest req, Map<String, Integer> values, Set<String> resolving) {

        if (values.containsKey(name)) {
            return values.get(name);
        }
        if (!resolving.add(name)) {
            throw new IllegalArgumentException("Circular reference");
        }

        String raw = req.getParameter(name);
        if (raw == null) {
            throw new IllegalArgumentException("Undefined variable");
        }

        int value;
        if (raw.matches("-?\\d+")) {
            value = Integer.parseInt(raw);
        } else if (raw.matches("[a-z]")) {
            value = resolve(raw, req, values, resolving);
        } else {
            throw new IllegalArgumentException("Invalid variable");
        }

        resolving.remove(name);
        values.put(name,value);
        return value;
    }

    private int calculate(String expr, Map<String, Integer> vars) {
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

                if (Character.isLetter(c)) {
                    int start = pos;
                    while (pos < s.length() && Character.isLetter(s.charAt(pos))) {
                        pos++;
                    }
                    String var = s.substring(start, pos);
                    if (!vars.containsKey(var)) {
                        throw new IllegalArgumentException("Unknown variable: " + var);
                    }
                    return vars.get(var);
                }

//                if (Character.isDigit(c)) {
//                    int start = pos;
//                    while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
//                        pos++;
//                    }
//                    return Integer.parseInt(s.substring(start, pos));
//                }

                throw new IllegalArgumentException("Unexpected character: " + c);
            }
        }

        return new Parser(expr).parse();
    }

}