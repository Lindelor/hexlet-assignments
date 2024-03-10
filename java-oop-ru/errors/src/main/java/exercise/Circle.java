package exercise;

// BEGIN
public class Circle {
    Point center;
    int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (radius < 0) {
            throw new NegativeRadiusException(String.format("Радиус круга должен быть больше нуля, текущий радиус равен %s",
                    getRadius()));
        }

        return Math.pow(radius, 2) * Math.PI;
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
// END
