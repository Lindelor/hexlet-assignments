package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/welcome")
public class WelcomeController {
    @Autowired
    private Daytime daytime;

    @GetMapping(path = "")
    public ResponseEntity<String> showWelcome() {
        var result = String.format("It is %s now! Welcome to Spring!", daytime.getName());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}