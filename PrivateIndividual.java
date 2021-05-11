package model;

public class PrivateIndividual {
	private String id;
	private String vat;
	public PrivateIndividual(String id, String vat) {
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
