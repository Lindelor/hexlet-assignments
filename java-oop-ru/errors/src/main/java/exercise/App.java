package exercise;

// BEGIN
public class App {
    public static void printSquare(Circle circle) {
        try {
            System.out.print(Math.round(circle.getSquare()));
        } catch (NegativeRadiusException e) {
            System.out.print("Не удалось посчитать площадь");
        } finally {
            System.out.print("\nВычисление окончено");
        }
    }
}
// END
