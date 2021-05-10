package model;

public class Customer {
	private String name;
	private String address;
	private String email;
	private int phoneNumber;
	
	public Customer(String name, String address, String email, int phoneNumber) {
		this.name=name; 
		this.address=address;
		this.email=email;
		this.phoneNumber=phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
