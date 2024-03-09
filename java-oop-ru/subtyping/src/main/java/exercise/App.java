package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// BEGIN
public class App {
    public static void swapKeyValue(KeyValueStorage storage) {
        Map<String, String> resultMap = new HashMap<>();
        storage.toMap().forEach((key, value) -> resultMap.put(value, key));
        for (var elem: resultMap.values()) {
            storage.unset(elem);
        }

        for (Map.Entry<String, String> elem: resultMap.entrySet()) {
            storage.set(elem.getKey(), elem.getValue());
        }

    }
}
// END
