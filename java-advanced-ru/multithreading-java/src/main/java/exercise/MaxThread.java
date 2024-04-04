package exercise;

import java.util.Arrays;

import static java.util.Arrays.stream;

public class MaxThread extends Thread{

    private int[] numbers;

    private int result;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    public int getResult() {
        return this.result;
    }

    @Override
    public void run() {
        this.result = Arrays.stream(this.numbers)
                .max()
                .orElse(0);
    }
}
