package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Customer;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void testSetAndGetFirstName() {
        customer.setFirst_name("John");
        assertEquals("John", customer.getFirst_name());
    }

    @Test
    public void testSetAndGetLastName() {
        customer.setLast_name("Doe");
        assertEquals("Doe", customer.getLast_name());
    }

    @Test
    public void testSetAndGetUserName() {
        customer.setUser_name("johndoe");
        assertEquals("johndoe", customer.getUser_name());
    }

    @Test
    public void testSetAndGetPassword() {
        customer.setPassword("password123");
        assertEquals("password123", customer.getPassword());
    }

    @Test
    public void testSetAndGetEmailID_Valid() {
        assertTrue(customer.setEmailID("john@example.com"));
        assertEquals("john@example.com", customer.getEmailID());
    }

    @Test
    public void testSetAndGetEmailID_Invalid() {
        assertFalse(customer.setEmailID("invalid_email"));
        assertNull(customer.getEmailID());
    }

    @Test
    public void testSetAndGetCredits() {
        customer.setCredits("100");
        assertEquals("100", customer.getCredits());
    }
}
