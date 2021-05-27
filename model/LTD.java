package model;

import java.util.ArrayList;

public class LTD extends Customer{
 private ArrayList<Employee> employees;
 private ArrayList<Owner> owners;
 private String marketRegisterNumber;
 private String marketNumber;
 private String companyName;
 private boolean arePayers;
 
public LTD(String email, String company_name, String phoneNumber, String city, String zipCode, String street, String streetNumber,
           ArrayList<Employee> employees, ArrayList<Owner> owners, String marketRegisterNumber,
           String marketNumber, boolean arePayers) {
	super(email, phoneNumber, city, zipCode, street, streetNumber);
	this.companyName = company_name;
	this.employees = employees;
	this.owners = owners;
	this.marketRegisterNumber = marketRegisterNumber;
	this.marketNumber = marketNumber;
	this.arePayers = arePayers;
}

public String getCompanyName(){return companyName;}

public void setCompanyName(String companyName){this.companyName = companyName;}

public ArrayList<Employee> getEmployees() {
	return employees;
}

public void setEmployees(ArrayList<Employee> employees) {
	this.employees = employees;
}

public ArrayList<Owner> getOwners() {
	return owners;
}

public void setOwners(ArrayList<Owner> owners) {
	this.owners = owners;
}

public String getMarketRegistrationNumber() {
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
