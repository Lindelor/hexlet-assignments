package exercise;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

// BEGIN
@Getter
@Setter
@Value
// END
class User {
    int id;
    String firstName;
    String lastName;
    int age;
}
