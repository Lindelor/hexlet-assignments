package exercise;

// BEGIN
public class Flat implements Home {
    private double area;
    private double balconyArea;
    private int floor;

    public Flat(double area, double balconyArea, int floor) {
        this.area = area;
        this.balconyArea = balconyArea;
        this.floor = floor;
    }

    @Override
    public double getArea() {
        return area + balconyArea;
    }

    public double getBalconyArea() {
        return balconyArea;
    }

    public int getFloor() {
        return floor;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setBalconyArea(double balconyArea) {
        this.balconyArea = balconyArea;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public int compareTo(Home another) {
        if (this.area == another.getArea()) {
            return 0;
        } else if (this.getArea() > another.getArea()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Квартира площадью " +
                (area + balconyArea) +
                " метров на " +
                floor +
                " этаже";
    }
}
// END
