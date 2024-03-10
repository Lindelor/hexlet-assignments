package exercise;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.Map;

// BEGIN
public abstract class Tag {
    private String name;
    private Map<String, String> attributes;

    public Tag(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = new LinkedHashMap<>();
        this.attributes.putAll(attributes);
    }

    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<%s", getName()));

        getAttributes().forEach((key, value) -> sb.append(String.format(" %s=\"%s\"", key, value)));
        sb.append(">");

        return sb.toString();
    }

}
// END
