package exercise;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

// BEGIN
public class FileKV implements KeyValueStorage {

    private final String path;
    private final Map<String, String> storage;

    public FileKV(String path, Map<String, String> storage) {
        this.path = path;
        this.storage = new HashMap<>(storage);
    }

    public FileKV(String path) {
        this.path = path;
        storage = raiseBackUp(path);
    }

    @Override
    public void set(String key, String value) {
        storage.put(key, value);
        refreshFile();
    }

    @Override
    public void unset(String key) {
        storage.remove(key);
        refreshFile();
    }

    @Override
    public String get(String key, String defaultValue) {
        return storage.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<>(storage);
    }

    private void refreshFile() {
        Gson gson = new Gson();
        String json = gson.toJson(storage);

        try (FileWriter writter = new FileWriter(path, false)) {
            writter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> raiseBackUp(String path) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();

        try (FileReader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return gson.fromJson(sb.toString(), type);
    }
}
// END
