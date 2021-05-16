package model;

public class CustomerEmployee {
	private String id;
	private String firstName;
	private String secondName;
	private double salary;
	private double income;
	
	public CustomerEmployee(String id, String firstName, String secondName, double salary, double income) {
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.salary = salary;
		this.income = income;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

}
