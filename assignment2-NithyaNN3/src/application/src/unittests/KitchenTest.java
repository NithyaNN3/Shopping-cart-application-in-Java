package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Kitchen;

class KitchenTest {

    @BeforeEach
    public void setUp() {
        // Reset Kitchen to initial state before each test
        Kitchen.printStock(); // Print initial stock for reference
        Kitchen.firstOrderReceived = true; // Reset first order status
    }

    @Test
    public void testCalculatePreparationTime() {
        // Test when there is sufficient stock for the orders
        int expectedPreparationTime = 0; 
        assertEquals(expectedPreparationTime, Kitchen.calculatePreparationTime("Burrito: 2"));

        // Test when additional preparation time is needed due to insufficient stock
        expectedPreparationTime = 9; 
        assertEquals(expectedPreparationTime, Kitchen.calculatePreparationTime("Burrito: 2, Fries: 1"));

        // Test when no preparation time is needed (sufficient stock)
        assertEquals(0, Kitchen.calculatePreparationTime("Fries: 3"));
    }

    @Test
    public void testHandleSubsequentOrders() {
        // Test when it's the first order received
        assertTrue(Kitchen.isFirstOrderReceived());

        // Test when it's not the first order received and there is sufficient stock
        Kitchen.handleSubsequentOrders("Burrito", 1, 2, 0); 
        assertFalse(Kitchen.isFirstOrderReceived());

        // Test when it's not the first order received and there is insufficient stock
        Kitchen.handleSubsequentOrders("Fries", 5, 0, 8); 
        assertFalse(Kitchen.isFirstOrderReceived());
    }
}
