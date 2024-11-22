package model;

public class Soda extends Food {
    private static double sodaPrice = 2.50;

    public Soda(int quantity) {
        super("Soda", quantity, sodaPrice);
    }

    public static double getSodaPrice() {
        return sodaPrice;
    }

    public static void setSodaPrice(double sodaPrice) {
        Soda.sodaPrice = sodaPrice;
    }
}
