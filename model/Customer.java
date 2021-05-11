package model;

public class Customer {
	private String name;
	private String street;
	private String streetNumber;
	private String zipCode;
	private String email;
	private int phoneNumber;
	
	public Customer() {
		
	}
	public Customer(int phoneNumber, String name, String street, String streetNumber, String zipCode, String email) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet(){
		return street;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreetNumber(){
		return streetName;
	}

	public void setStreetNumber(String streetNumber){
		this.streetNumber = streetNumber;
	}

	public String getZipCode(){
		return zipCode;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
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
