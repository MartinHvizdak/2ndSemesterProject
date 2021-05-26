package model;

public class SelfEmployed extends Customer{
	private String vat; 
	private String marketNumber;
	
	public SelfEmployed(String email, String name, String phoneNumber, String city, String zipCode, String street, String streetNumber, String vat, String marketNumber) {
		super(email, name, phoneNumber, city, zipCode, street, streetNumber);
		this.vat=vat;
		this.marketNumber = marketNumber;
	}

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
