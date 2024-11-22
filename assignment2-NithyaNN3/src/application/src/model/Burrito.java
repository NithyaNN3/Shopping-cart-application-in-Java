package model;

public class Burrito extends Food {
    private static double burritoPrice = 7.00;

    public Burrito(int quantity) {
        super("Burrito", quantity, burritoPrice);
    }
    
    // getters and setters
    public static double getBurritoPrice() {
        return burritoPrice;
    }

    public static void setBurritoPrice(double burritoPrice) {
        Burrito.burritoPrice = burritoPrice;
    }
}
