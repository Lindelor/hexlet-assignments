package exercise;

import java.util.Arrays;

public class MinThread extends Thread{

    private int[] numbers;

    private int result;

    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    public int getResult() {
        return this.result;
    }

    @Override
    public void run() {
        this.result = Arrays.stream(this.numbers)
                .min()
                .orElse(0);
    }
}
