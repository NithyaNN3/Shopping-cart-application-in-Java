package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Food;

class FoodTest {

    private Food food;

    @BeforeEach
    public void setUp() {
        food = new Food("Burger", 2, 5.99);
    }

    @Test
    public void testGetName() {
        assertEquals("Burger", food.getName());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2, food.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        food.setQuantity(3);
        assertEquals(3, food.getQuantity());
    }

    @Test
    public void testGetPrice() {
        assertEquals(5.99, food.getPrice());
    }

    @Test
    public void testSetPrice() {
        food.setPrice(6.99);
        assertEquals(6.99, food.getPrice());
    }
}
