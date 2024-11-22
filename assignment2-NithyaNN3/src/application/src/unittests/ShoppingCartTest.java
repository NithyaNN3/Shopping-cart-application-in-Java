package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ShoppingCart;

class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void testAddItem() {
        cart.addItem("Burrito", 2);
        assertEquals(2, cart.getItems().get(0).getQuantity());
        assertEquals("Burrito", cart.getItems().get(0).getName());

        // Adding same item again should increase quantity
        cart.addItem("Burrito", 1);
        assertEquals(3, cart.getItems().get(0).getQuantity());
    }

    @Test
    void testUpdateItem() {
        // Test updating quantity of existing item
        cart.addItem("Fries", 1);
        cart.updateItem("Fries", 3);
        assertEquals(3, cart.getItems().get(0).getQuantity());

        // Test updating quantity to 0 removes item
        cart.updateItem("Fries", 0);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testRemoveItem() {
        cart.addItem("Soda", 2);
        cart.removeItem("Soda");
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testGetTotalPrice() {
        cart.addItem("Meal", 1);
        cart.addItem("Soda", 2);

        // Check total price calculation
        assertEquals(15.5, cart.getTotalPrice());
    }

    @Test
    void testClear() {
        cart.addItem("Burrito", 1);
        cart.clear();
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testGetItemString() {
        cart.addItem("Fries", 2);
        cart.addItem("Burrito", 1);

        // Check if item string is generated correctly
        assertEquals("Fries: 2, Burrito: 1", cart.getItemString());
    }
}
