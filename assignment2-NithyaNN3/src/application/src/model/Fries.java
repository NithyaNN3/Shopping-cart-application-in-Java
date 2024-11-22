package model;

public class Fries extends Food {
    private static double friesPrice = 4.00;

    public Fries(int quantity) {
        super("Fries", quantity, friesPrice);
    }

    public static double getFriesPrice() {
        return friesPrice;
    }

    public static void setFriesPrice(double friesPrice) {
        Fries.friesPrice = friesPrice;
    }
}
