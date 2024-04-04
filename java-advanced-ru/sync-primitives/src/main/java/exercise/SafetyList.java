package exercise;

import java.util.ArrayList;
import java.util.List;

public class SafetyList {
    private List<Integer> list = new ArrayList<>();

    public SafetyList() {
    }

    public synchronized void add(int number) {
        this.list.add(number);
    }

    public int get(int index) {
        return this.list.get(index);
    }

    public int getSize() {
        return this.list.size();
    }
}
