package model;

public class PrivateIndividual extends Customer{
	private String id;
	private String vat;
	private String secondName;
	private String firstName;

	public PrivateIndividual(String email, String firstName, String secondName, String phoneNumber, String city, String zipCode, String street, String streetNumber, String id, String vat) {
		super(email, phoneNumber, city, zipCode, street, streetNumber);
		this.id = id;
		this.vat = vat;
		this.firstName = firstName;
		this.secondName =  secondName;
	}

	public String getFirstName(){return firstName;}

	public void setFirstName(String firstName){this.firstName = firstName;}

	public String getSecondName(){return secondName;}

	public void setSecondName(String secondName){this.secondName = secondName;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}
	
	

}
