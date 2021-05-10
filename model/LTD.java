package model;

import java.util.ArrayList;

public class LTD {
 private ArrayList<CustomerEmployee> employees = new ArrayList<CustomerEmployee>();
 private ArrayList<Owner> owners = new ArrayList<Owner>();
 private int marketRegisterNumber;
 private int marketNumber;
 private boolean arePayers;
 
public LTD(ArrayList<CustomerEmployee> employees, ArrayList<Owner> owners, int marketRegisterNumber, int marketNumber, boolean arePayers) {
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

public int getMarketRegisterNumber() {
	return marketRegisterNumber;
}

public void setMarketRegisterNumber(int marketRegisterNumber) {
	this.marketRegisterNumber = marketRegisterNumber;
}

public int getMarketNumber() {
	return marketNumber;
}

public void setMarketNumber(int marketNumber) {
	this.marketNumber = marketNumber;
}

public boolean arePayers() {
	return arePayers;
}

public void setArePayers(boolean arePayers) {
	this.arePayers = arePayers;
}

}
