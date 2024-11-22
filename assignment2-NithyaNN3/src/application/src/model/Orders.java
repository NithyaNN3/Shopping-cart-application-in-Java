package model;

public class Orders {
	private int id;
	private String dayTime;
    private double amount;
    private String itemString;
    private String status;
    private String preptime;

    public Orders(int id, String dayTime, double amount, String itemString, String status, String preptime) {
    	this.id = id;
        this.dayTime = dayTime;
        this.amount = amount;
        this.status = status;
        this.itemString = itemString;
        this.preptime = preptime;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
    
    public double getAmount() {
    	return amount;
    }
    
    public void setAmount(double Amount) {
    	this.amount = amount;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
    
    public String getItemString() {
        return itemString;
    }

    public void setItemString(String ItemString) {
        this.itemString = itemString;
    }
    
    public String getPreptime() {
        return preptime;
    }

    public void setPreptime(String Preptime) {
        this.preptime = preptime;
    }
}
