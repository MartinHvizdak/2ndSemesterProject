package model;

public class PrivateIndividual extends Customer{
	private String id;
	private String vat;
	public PrivateIndividual(String email, String name, String phoneNumber, String city, String zipCode, String street, String streetNumber, String id, String vat) {
		super(email, name, phoneNumber, city, zipCode, street, streetNumber);
		this.id = id;
		this.vat = vat;
	}
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
