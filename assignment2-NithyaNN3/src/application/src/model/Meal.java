package model;

public class Meal extends Food {
    private static double mealPrice = Burrito.getBurritoPrice() + Fries.getFriesPrice() + Soda.getSodaPrice() - 3.00;

    public Meal(int quantity) {
        super("Meal", quantity, mealPrice);
    }

    public static double getMealPrice() {
        return mealPrice;
    }

    public static void setMealPrice(double mealPrice) {
        Meal.mealPrice = mealPrice;
    }
}
