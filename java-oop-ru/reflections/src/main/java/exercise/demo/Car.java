package exercise.demo;

import exercise.MinLength;
import exercise.NotNull;

public class Car {

    private String name;

    private String maxSpeed;

    public Car(String name, String maxSpeed) {
        this.name = name;
        this.maxSpeed = maxSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{"
                + "name='" + name + '\''
                + ", maxSpeed=" + maxSpeed
                + '}';
    }
}
