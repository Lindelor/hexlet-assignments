package exercise;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
// BEGIN

// END


class FileKVTest {

    private static Path filepath = Paths.get("src/test/resources/file").toAbsolutePath().normalize();

    @BeforeEach
    public void beforeEach() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(new HashMap<String, String>());
        Files.writeString(filepath, content, StandardOpenOption.CREATE);
    }

    @Test
    void fileKVTest() {
        KeyValueStorage storage = new FileKV("src/test/resources/file",Map.of("key", "10"));
        assertThat(storage.get("key2", "default")).isEqualTo("default");
        assertThat(storage.get("key", "default")).isEqualTo("10");

        storage.set("key2", "value2");
        storage.set("key", "value");

        assertThat(storage.get("key2", "default")).isEqualTo("value2");
        assertThat(storage.get("key", "default")).isEqualTo("value");

        storage.unset("key");
        assertThat(storage.get("key", "def")).isEqualTo("def");
        assertThat(storage.toMap()).isEqualTo(Map.of("key2", "value2"));
    }

    @Test
    void fileKVTest2() {
        KeyValueStorage storage = new FileKV("src/test/resources/file",Map.of("key", "10"));
        assertThat(storage.get("key2", "default")).isEqualTo("default");
        assertThat(storage.get("key", "default")).isEqualTo("10");

        storage.set("key2", "value2");
        storage.set("key", "value");
        storage.set("key3", "value3");
        storage.set("key4", "value4");

        assertThat(storage.get("key2", "default")).isEqualTo("value2");
        assertThat(storage.get("key", "default")).isEqualTo("value");
        assertThat(storage.get("key3", "default")).isEqualTo("value3");
        assertThat(storage.get("key4", "default")).isEqualTo("value4");
    }

    @Test
    void backUpTest() {
        KeyValueStorage storage = new FileKV("src/test/resources/file",Map.of("key", "10"));
        assertThat(storage.get("key2", "default")).isEqualTo("default");
        assertThat(storage.get("key", "default")).isEqualTo("10");

        storage.set("key2", "value2");
        storage.set("key", "value");
        storage.set("key3", "value3");
        storage.set("key4", "value4");

        assertThat(storage.get("key2", "default")).isEqualTo("value2");
        assertThat(storage.get("key", "default")).isEqualTo("value");
        assertThat(storage.get("key3", "default")).isEqualTo("value3");
        assertThat(storage.get("key4", "default")).isEqualTo("value4");

        KeyValueStorage storage2 = new FileKV("src/test/resources/file");
        assertThat(storage2.get("key2", "default")).isEqualTo("value2");
        assertThat(storage2.get("key", "default")).isEqualTo("value");
        assertThat(storage2.get("key3", "default")).isEqualTo("value3");
        assertThat(storage2.get("key4", "default")).isEqualTo("value4");
    }

}
