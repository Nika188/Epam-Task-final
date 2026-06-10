package com.epam.rd.autotasks;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet("/calc/*")
public class CalcServlet extends HttpServlet {

    private static final String EXPRESSION = "expression";
    private static final String VARIABLES = "variables";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!"/result".equals(req.getPathInfo())) {
            resp.sendError(400, "Invalid GET resource");
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(EXPRESSION) == null) {
            resp.sendError(409, "Expression not set");
            return;
        }

        try{
            String expr = (String) session.getAttribute(EXPRESSION);
            Map<String, Integer> vars = resolveVariables(session);
            int result = calculate(expr, vars);

            resp.setStatus(200);
            resp.setContentType("text/plain");
            resp.getWriter().write(String.valueOf(result));
        } catch (IllegalStateException e) {
            resp.sendError(409, e.getMessage());
        } catch (Exception e) {
            resp.sendError(400, e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        String path = req.getPathInfo();

        String body = req.getReader().lines().reduce("", String::concat).trim();

        if (path == null) {
            resp.sendError(400, "Missing resource");
            return;
        }

        if (path.equals("/expression")) {
            boolean created = session.getAttribute(EXPRESSION) == null;
            try{
                Map<String, Integer> vars = new HashMap<>();
                for (int i = 0; i < 26; i++) {
                    vars.put(String.valueOf((char) (i + 97)), i);
                }
                calculate(body,vars);
            } catch (Exception e) {
                resp.sendError(400, e.getMessage());
                return;
            }

            if (body.isEmpty()) {
                resp.sendError(400, "Empty expression");
                return;
            }
            session.setAttribute(EXPRESSION, body);
            resp.setStatus(created ? 201 : 200);
            return;
        }

        String var = path.substring(1);
        if (!var.matches("[a-z]")) {
            resp.sendError(400, "Invalid variable name");
            return;
        }

        if (!body.matches("-?\\d+|[a-z]")) {
            resp.sendError(400, "Invalid variable value");
            return;
        }

        if (body.matches("-?\\d+")) {
            int v = Integer.parseInt(body);
            if (v < -10000 || v > 10000) {
                resp.sendError(403, "Value out of range");
                return;
            }
        }

        Map<String, String> varsStr = getVarsStr(session);
        boolean created = !varsStr.containsKey(var);
        varsStr.put(var, body);

        resp.setStatus(created ? 201 : 200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(204);
            return;
        }

        String path = req.getPathInfo();

        if ("/expression".equals(path)) {
            session.removeAttribute(EXPRESSION);
            resp.setStatus(204);
            return;
        }

        if (path != null && path.matches("/[a-z]")) {
            getVarsStr(session).remove(path.substring(1));
            resp.setStatus(204);
            return;
        }

        resp.setStatus(204);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getVarsStr(HttpSession session) {
        Map<String, String> varsStr = (Map<String, String>) session.getAttribute(VARIABLES);

        if (varsStr == null) {
            varsStr = new HashMap<>();
            session.setAttribute(VARIABLES, varsStr);
        }

        return varsStr;
    }

    private Map<String, Integer> resolveVariables(HttpSession session) {
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