package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
public class ReversedSequence implements CharSequence {

    private final int length;
    private final List<Character> chars;

    public ReversedSequence(String str) {
        chars = new ArrayList<>();
        for (var i = 0; i < str.length(); i++) {
            chars.add(str.charAt(i));
        }
        this.length = str.length();
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        if (index >= length) {
            throw  new IndexOutOfBoundsException();
        }
        return chars.get(length - index - 1);
    }

    private List<Character> reverse() {
        var result = new ArrayList<Character>();

        for (var i = length - 1; i >= 0; i--) {
            result.add(chars.get(i));
        }

        return result;
    }

    @Override
    public CharSequence subSequence(int start, int end) {

        if (end < start || start >= length || end > length || start < 0) {
            throw new IndexOutOfBoundsException();
        }
        StringBuilder result = new StringBuilder();

        for (var i = start; i < end; i++) {
            result.append(charAt(i));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (var elem : reverse()) {
            sb.append(elem);
        }

        return sb.toString();
    }
}
// END
