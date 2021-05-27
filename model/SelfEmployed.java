package model;

public class SelfEmployed extends Customer{
	private String vat; 
	private String marketNumber;
	private String secondName;
	private String firstName;
	
	public SelfEmployed(String email, String firstName, String secondName, String phoneNumber, String city, String zipCode, String street, String streetNumber, String vat, String marketNumber) {
		super(email, phoneNumber, city, zipCode, street, streetNumber);
		this.vat=vat;
		this.marketNumber = marketNumber;
		this.firstName = firstName;
		this.secondName =  secondName;
	}

	public String getFirstName(){return firstName;}

	public void setFirstName(String firstName){this.firstName = firstName;}

	public String getSecondName(){return secondName;}

	public void setSecondName(String secondName){this.secondName = secondName;}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getMarketNumber() {
		return marketNumber;
	}

	public void setMarketNumber(String marketNumber) {
		this.marketNumber = marketNumber;
	}

}
