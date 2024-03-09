package exercise;

import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class App {
    public static List<String> buildApartmentsList(List<Home> homes, int n) {
        return homes.stream()
                .sorted(Home::compareTo)
                .limit(n)
                .map(Object::toString)
                .toList();
    }
}
// END
