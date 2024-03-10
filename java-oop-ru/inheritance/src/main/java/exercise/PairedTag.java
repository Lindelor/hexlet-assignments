package exercise;

import java.util.Map;
import java.util.List;

// BEGIN
public class PairedTag extends Tag{
    private String body;
    private List<Tag> childs;


    public PairedTag(String name, Map<String, String> attributes, String body, List<Tag> childs) {
        super(name, attributes);
        this.body = body;
        this.childs = List.copyOf(childs);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        childs.forEach(elem -> sb.append(elem.toString()));
        sb.append(body);

        sb.append(String.format("</%s>", getName()));
        return sb.toString();
    }
}
// END
