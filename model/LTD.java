package model;

import java.util.ArrayList;

public class LTD extends Customer{
 private ArrayList<CustomerEmployee> employees;
 private ArrayList<Owner> owners;
 private String marketRegisterNumber;
 private String marketNumber;
 private boolean arePayers;
 
public LTD(String email, String name, String phoneNumber, String city, String zipCode, String street, String streetNumber,
		   ArrayList<CustomerEmployee> employees, ArrayList<Owner> owners, String marketRegisterNumber,
		   String marketNumber, boolean arePayers) {
	super(email, name, phoneNumber, city, zipCode, street, streetNumber);
	this.employees = employees;
	this.owners = owners;
	this.marketRegisterNumber = marketRegisterNumber;
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

public String getMarketRegisterNumber() {
	return marketRegisterNumber;
}

public void setMarketRegisterNumber(String marketRegisterNumber) {
	this.marketRegisterNumber = marketRegisterNumber;
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
