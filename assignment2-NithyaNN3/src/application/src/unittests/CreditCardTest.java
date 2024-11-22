package unittests;

import controller.DashboardController;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.CreditCard;

class CreditCardTest {

    private CreditCard creditCard;

    @BeforeEach
    public void setUp() {
        creditCard = new CreditCard("1234567890123456", "12/2024", "123");
    }

    @Test
    public void testValidateCreditCardNo_Valid() {
        assertTrue(creditCard.validateCreditCardNo());
    }

    @Test
    public void testValidateCreditCardNo_Null() {
        creditCard = new CreditCard(null, "12/2024", "123");
        assertFalse(creditCard.validateCreditCardNo());
    }

    @Test
    public void testValidateCreditCardNo_IncorrectLength() {
        creditCard = new CreditCard("123456", "12/2024", "123");
        assertFalse(creditCard.validateCreditCardNo());
    }

    @Test
    public void testValidateCreditCardNo_NonNumeric() {
        creditCard = new CreditCard("1234abcd56789012", "12/2024", "123");
        assertFalse(creditCard.validateCreditCardNo());
    }

    @Test
    public void testValidateExpiryDate_Valid() {
        assertTrue(creditCard.validateExpiryDate());
    }

    @Test
    public void testValidateExpiryDate_InvalidFormat() {
        creditCard = new CreditCard("1234567890123456", "2024/12", "123");
        assertFalse(creditCard.validateExpiryDate());
    }

    @Test
    public void testValidateExpiryDate_PastDate() {
        creditCard = new CreditCard("1234567890123456", "12/2020", "123");
        assertFalse(creditCard.validateExpiryDate());
    }

    @Test
    public void testValidateCVV_Valid() {
        assertTrue(creditCard.validateCVV());
    }

    @Test
    public void testValidateCVV_Null() {
        creditCard = new CreditCard("1234567890123456", "12/2024", null);
        assertFalse(creditCard.validateCVV());
    }

    @Test
    public void testValidateCVV_IncorrectLength() {
        creditCard = new CreditCard("1234567890123456", "12/2024", "12");
        assertFalse(creditCard.validateCVV());
    }

    @Test
    public void testValidateCVV_NonNumeric() {
        creditCard = new CreditCard("1234567890123456", "12/2024", "12a");
        assertFalse(creditCard.validateCVV());
    }

    @Test
    public void testIsValidNo_Valid() {
        assertTrue(creditCard.isValidNo());
    }

    @Test
    public void testIsValidNo_Invalid() {
        creditCard = new CreditCard(null, "12/2024", "123");
        assertFalse(creditCard.isValidNo());
    }
}