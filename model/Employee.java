package model;

public class Employee {
	private String id;
	private String firstName;
	private String surName;
	private double salary;
	private double income;

	public Employee(String id, String firstName, String surName, double salary, double income) {
		this.id = id;
		this.firstName = firstName;
		this.surName = surName;
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

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
}
