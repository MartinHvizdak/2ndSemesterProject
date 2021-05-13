package model;

public class CustomerEmployee {
	private String id;
	private String name;
	private double salary;
	private double income;
	
	public CustomerEmployee(String id, String name, double salary, double income) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.income = income;
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
