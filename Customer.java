package model;

public class Customer {
	private String name;
	private String City;
	private String street;
	private String streetNumber;
	private String zipCode;
	private String email;
	private String phoneNumber;
	
	public Customer() {
		
	}
	public Customer(String phoneNumber, String City, String name, String street, String streetNumber, String zipCode, String email) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.email = email;
		this.City = City;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {this.City = City;}

	public String getCity() {return this.City;}

	public String getStreet(){
		return street;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreetNumber(){
		return streetNumber;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}