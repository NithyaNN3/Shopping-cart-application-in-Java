package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCard {
	private String creditCardNo;
	private String expiryDate;
	private String CVV;
	
	public CreditCard(String creditCardNo, String expiryDate, String CVV) {
		// credit card constructor
		this.creditCardNo = creditCardNo;
		this.expiryDate = expiryDate;
		this.CVV = CVV;
	}
	
	public boolean validateCreditCardNo() {
		// validates card no.
        if (this.creditCardNo == null || this.creditCardNo.length() != 16) {
            return false;
        }
        for (char c : this.creditCardNo.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
	
	public boolean validateExpiryDate() {
		// checks validity of expiry date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            YearMonth inputDate = YearMonth.parse(this.expiryDate, formatter);
            YearMonth currentDate = YearMonth.now();
            return inputDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
	}
        
    public boolean validateCVV() {
    	// checks validity of cvv
        if (this.CVV == null || this.CVV.length() != 3) {
            return false;
        }
        for (char c : this.CVV.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isValidNo() {
    	// checks validity
    	return validateCreditCardNo() && validateExpiryDate() && validateCVV();
    }

}
