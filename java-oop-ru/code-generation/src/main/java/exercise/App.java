package exercise;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

// BEGIN
public class App {
    public static void save(Path path, Car car) {
        try (FileWriter writer = new FileWriter(path.toString(), false)) {
            writer.write(car.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Car extract(Path path) {
        try {
            String json = Files.readString(path);
            return Car.unserialize(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
// END
