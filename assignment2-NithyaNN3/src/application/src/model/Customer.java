package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.*;


public class Customer {
	private static Customer instance = null;
	
	private StringProperty first_name = null;
	private StringProperty last_name = null;
	private StringProperty user_name = null;
	private StringProperty password = null;
	private StringProperty emailID;
	private StringProperty credits;
	
	public Customer() {
		this.first_name = new SimpleStringProperty();
		this.last_name = new SimpleStringProperty();
		this.user_name = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
		this.emailID = new SimpleStringProperty();
		this.credits = new SimpleStringProperty();
	}
	
	// Static method to retrieve the singleton instance
    public static Customer getInstance() {
        if (instance == null) {
            instance = new Customer();
        }
        return instance;
    }
    
    // Getters and setters for customer data model class

    public String getFirst_name() {
        return first_name.get();
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public String getUser_name() {
        return user_name.get();
    }

    public void setUser_name(String user_name) {
        this.user_name.set(user_name);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public String getEmailID() {
        return emailID.get();
    }

    public boolean setEmailID(String emailID) {
    	Pattern pattern = Pattern.compile("^.+@.+(\\.[^.]+)+$");
        Matcher matcher = pattern.matcher(emailID);
        if (matcher.matches()) {
            this.emailID.set(emailID);
            return true;
        } else {
            return false;
        }
    }
    
    public String getCredits() {
        return credits.get();
    }

    public void setCredits(String credits) {
        this.credits.set(credits);
    }

}
