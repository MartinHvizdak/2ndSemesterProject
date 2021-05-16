package model;

import java.util.ArrayList;

public class LTD extends Customer{
 private ArrayList<CustomerEmployee> employees;
 private ArrayList<Owner> owners;
 private String marketRegistrationNumber;
 private String marketNumber;
 private boolean arePayers;
 
public LTD(String email, String name, String phoneNumber, String city, String zipCode, String street, String streetNumber,
		   ArrayList<CustomerEmployee> employees, ArrayList<Owner> owners, String marketRegistrationNumber,
		   String marketNumber, boolean arePayers) {
	super(email, name, phoneNumber, city, zipCode, street, streetNumber);
	this.employees = employees;
	this.owners = owners;
	this.marketRegistrationNumber = marketRegistrationNumber;
	this.marketNumber = marketNumber;
	this.arePayers = arePayers;
}

public ArrayList<CustomerEmployee> getEmployees() {
	return employees;
}

public void setEmployees(ArrayList<CustomerEmployee> employees) {
	this.employees = employees;
}

public ArrayList<Owner> getOwners() {
	return owners;
}

public void setOwners(ArrayList<Owner> owners) {
	this.owners = owners;
}

public String getMarketRegistrationNumber() {
	return marketRegistrationNumber;
}

public void setMarketRegistrationNumber(String marketRegistrationNumber) {
	this.marketRegistrationNumber = marketRegistrationNumber;
}

public String getMarketNumber() {
	return marketNumber;
}

public void setMarketNumber(String marketNumber) {
	this.marketNumber = marketNumber;
}

public boolean arePayers() {
	return arePayers;
}

public void setArePayers(boolean arePayers) {
	this.arePayers = arePayers;
}

}
