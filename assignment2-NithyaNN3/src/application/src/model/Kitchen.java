package model;

import java.util.HashMap;
import java.util.Map;

/*
 * This is a sample for the Burrito King restaurant
 * ordering system.
 * 
 * Assumptions: 1) It is assumed that there is a new customer every time the user is on the Main menu
 * 				and it is the same customer modifying his order when user is on the item menu specifically
 * 				2) When the total sales report is printed, items with updated prices are printed in a separate line to 
 * 					reflect that the prices were changed. 
 * 
 * @author Nithya N Nagaraja
 */

public class Kitchen {

    // Required Constants
    private static final Map<String, Integer> stock = new HashMap<>() {{
        put("Burrito", 2);
        put("Fries", 5);
    }};
    private static final int timeToCookBurrito = 9;
    private static final int timeToCookFries = 8;
    private static final Map<String, Integer> batchPrepared = new HashMap<>();

    static {
        batchPrepared.put("Burrito", 2);
        batchPrepared.put("Fries", 5);
    }

    public static boolean firstOrderReceived = true; // Initially set to true

    public static boolean isFirstOrderReceived() {
        // Getter method for first order status
        return firstOrderReceived;
    }

    public static void printStock() {
        // Method to print current stock
        System.out.println("Current Stock:");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static int calculatePreparationTime(String items) {
        int totalCookingTime = 0;

        String[] itemArray = items.split(", ");

        // Parse items and calculate total cooking time
        for (String item : itemArray) {
            String[] itemDetails = item.split(": ");
            String itemName = itemDetails[0];
            int quantity = Integer.parseInt(itemDetails[1]);

            if (itemName.equals("Meal")) {
                totalCookingTime = Math.max(totalCookingTime, handleMealOrder(quantity));
            } else {
                totalCookingTime = Math.max(totalCookingTime, handleOrder(itemName, quantity));
            }
        }

        System.out.println("Total preparation time: " + totalCookingTime);
        return totalCookingTime;
    }

    public static int handleOrder(String itemName, int quantity) {
        int timeToCook = 0;

        // Check if the item exists in stock
        if (!stock.containsKey(itemName)) {
            System.out.println("Batch not set for item: " + itemName);
            return timeToCook;
        }

        int remainingStock = stock.get(itemName);

        switch (itemName) {
            case "Burrito":
                timeToCook = timeToCookBurrito;
                break;
            case "Fries":
                timeToCook = timeToCookFries;
                break;
            default:
                return timeToCook; // No batch cooking for other items
        }

        if (remainingStock == 0) {
        	// No stock available, calculate preparation time based on batch size
            int batchesRequired = (int) Math.ceil((double) quantity / (double) batchPrepared.get(itemName));
            timeToCook = batchesRequired * timeToCook;
            stock.put(itemName, (batchesRequired * batchPrepared.get(itemName)) - quantity);
        } else if (quantity <= remainingStock) {
            stock.put(itemName, remainingStock - quantity); // Adjust remaining stock
            timeToCook = 0; // Sufficient stock, no preparation time needed
        } else {
            int extraQuantity = quantity - remainingStock;
            int batchesRequired = (int) Math.ceil((double) extraQuantity / (double) remainingStock);
            timeToCook = batchesRequired * timeToCook; // Calculate preparation time
            int newStock = (remainingStock * batchesRequired) - extraQuantity; // Adjust remaining stock
            stock.put(itemName, newStock > 0 ? newStock : remainingStock - extraQuantity % remainingStock);
        }

        handleSubsequentOrders(itemName, quantity, remainingStock, timeToCook); // Call method for subsequent orders
        return timeToCook;
    }

    public static int handleMealOrder(int quantity) {
        // A meal consists of 1 burrito and 1 fries
        int totalCookingTime = 0;

        totalCookingTime = Math.max(totalCookingTime, handleOrder("Burrito", quantity));
        totalCookingTime = Math.max(totalCookingTime, handleOrder("Fries", quantity));

        // No need to handle soda 

        return totalCookingTime;
    }

    public static void handleSubsequentOrders(String itemName, int quantity, int remainingStock, int timeToCook) {
        if (!firstOrderReceived) { // Check if it's not the first order received
            if (quantity > remainingStock && remainingStock > 0) { // Check if there is remaining stock
                int extraQuantity = quantity - remainingStock;
                int batchesRequired = (int) Math.ceil((double) extraQuantity / (double) remainingStock);

                // Calculate preparation time considering only the upper limit of the preparation times
                int additionalTimeToCook = batchesRequired * timeToCook;
                timeToCook += additionalTimeToCook;

                int newStock = (remainingStock * batchesRequired) - extraQuantity; // Adjust remaining stock
                stock.put(itemName, newStock > 0 ? newStock : remainingStock - extraQuantity % remainingStock);
            }
        } else {
            firstOrderReceived = false; // Set to false after the first order is received
        }
        printStock();
    }
}
