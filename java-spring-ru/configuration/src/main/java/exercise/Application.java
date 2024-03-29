package exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.beans.factory.annotation.Autowired;

import exercise.model.User;
import exercise.component.UserProperties;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();
    @Autowired
    private UserProperties userProperties;

    @GetMapping("/admins")
    public ResponseEntity<List<String>> admins() {
        var adminNames = new ArrayList<String>();
        for (var admin : userProperties.getAdmins()) {
            users.stream()
                    .filter(user -> user.getEmail().equals(admin))
                    .findFirst()
                    .ifPresent(user -> adminNames.add(user.getName()));
        }
        var result = adminNames.stream().sorted().toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
            .filter(u -> u.getId() == id)
            .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
