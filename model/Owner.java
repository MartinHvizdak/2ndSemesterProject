package model;

public class Owner {
	private String id;
	private String firstName;
	private String surName;
	private String relation;
	
	public Owner(String id, String firstName, String surName, String relation) {
		this.id = id;
		this.firstName = firstName;
		this.surName =  surName;
		this.relation = relation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() { return surName; }

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	

}
