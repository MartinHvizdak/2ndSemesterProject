package model;

public class Owner {
	private String id;
	private String name;
	private String relation;
	
	public Owner(String id, String name, String relation) {
		this.id = id;
		this.name = name;
		this.relation = relation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	

}
