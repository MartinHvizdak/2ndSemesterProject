package model;

public class SelfEmployeed {
	private String vat; 
	private String marketNumber;
	
	public SelfEmployeed(String vat, String marketNumber) {
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
