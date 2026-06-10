package com.epam.rd.autotasks.springstatefulcalc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calc")
public class CalcController {

    private final CalcService service;

    public CalcController(CalcService service) {
        this.service = service;
    }

    private CalcSession session(HttpSession httpSession) {
        CalcSession data = (CalcSession) httpSession.getAttribute("data");
        if (data == null) {
            data = new CalcSession();
            httpSession.setAttribute("data", data);
        }
        return data;
    }

    @PutMapping("/expression")
    public ResponseEntity<Void> putExpression(@RequestBody String body, HttpSession session) {
        if (body.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CalcSession data = session(session);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/calc/expression");
        boolean created = data.getExpression() == null;
        try{
            Map<String, Integer> vars = new HashMap<>();
            for (int i = 0; i < 26; i++) {
                vars.put(String.valueOf((char) (i + 97)), i);
            }
            service.calculate(body,vars);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        data.setExpression(body);
        return new ResponseEntity<>(headers, created ? HttpStatus.CREATED : HttpStatus.OK);
    }

    @PutMapping("/{var}")
    public ResponseEntity<Void> putVariable(@PathVariable String var, @RequestBody String body, HttpSession session) {

        if (!var.matches("[a-z]")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid variable name");
        }

        if (!body.matches("-?\\d+|[a-z]")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid variable value");
        }

        if (body.matches("-?\\d+")) {
            int v = Integer.parseInt(body);
            if (v < -10000 || v > 10000) {
                throw new ApiException(HttpStatus.FORBIDDEN, "Value out of range");
            }
        }

        CalcSession data = session(session);
        boolean created = !data.getVariables().containsKey(var);
        data.getVariables().put(var, body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/calc/" + var);

        return new ResponseEntity<>(headers, created ? HttpStatus.CREATED : HttpStatus.OK);
    }

    @DeleteMapping("/expression")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpression(HttpSession session) {
        session(session).setExpression(null);
    }

    @DeleteMapping("/{var}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVariable(@PathVariable String var, HttpSession session) {
        session(session).getVariables().remove(var);
    }

    @GetMapping("/result")
    public ResponseEntity<String> getResult(HttpSession session) {
        int result = service.getResultCalc(session(session));
        return new ResponseEntity<>(String.valueOf(result),HttpStatus.OK);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handle(ApiException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}