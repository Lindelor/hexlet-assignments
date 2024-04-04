package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    public static Map<String, Integer> getMinMax(int[] numbers) {
        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);

        Map<String, Integer> result = new HashMap<>();

        minThread.start();
        LOGGER.log(Level.INFO, "minThread started");
        maxThread.start();
        LOGGER.log(Level.INFO, "maxThread started");

        try {
            minThread.join();
            LOGGER.log(Level.INFO, "minThread finished");
            maxThread.join();
            LOGGER.log(Level.INFO, "maxThread finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result.put("min", minThread.getResult());
        result.put("max", maxThread.getResult());

        return result;
    }
}
