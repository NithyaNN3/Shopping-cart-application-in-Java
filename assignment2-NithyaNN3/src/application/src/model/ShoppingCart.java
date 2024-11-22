package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Food> items;
    private double totalPrice;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String name, int quantity) {
    	// adds item to cart
        double price = getPrice(name);
        for (Food item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new Food(name, quantity, price));
    }
    
    public void updateItem(String name, int quantity) {
    	// updates item in cart
        for (Food item : items) {
            if (item.getName().equals(name)) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    items.remove(item);
                }
                return;
            }
        }
        if (quantity > 0) {
            addItem(name, quantity);
        }
    }
    
    private double getPrice(String name) {
    	// get price of item
        switch (name.toLowerCase()) {
            case "burrito": return Burrito.getBurritoPrice();
            case "fries": return Fries.getFriesPrice();
            case "soda": return Soda.getSodaPrice();
            case "meal": return Meal.getMealPrice();
            default: throw new IllegalArgumentException("Unknown food item: " + name);
        }
    }

    public void removeItem(String name) {
    	// removes item from cart
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public List<Food> getItems() {
    	// gets items in cart
        return new ArrayList<>(items);
    }

    public void clear() {
    	// clears items
        items.clear();
    }

    public double getTotalPrice() {
    	// Calculate total price only if it hasn't been set explicitly
        totalPrice = items.stream()
                    .mapToDouble(item -> item.getQuantity() * item.getPrice())
                    .sum();
        return totalPrice;
    }
    
    // getters and setters
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getItemString() {
        StringBuilder itemString = new StringBuilder();
        for (Food item : items) {
            if (itemString.length() > 0) {
            	itemString.append(", ");
            }
            itemString.append(item.getName()).append(": ").append(item.getQuantity());
        }
        return itemString.toString();
    }
}
