package model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Order <T extends Customer> {
	private T customer;
	private ArrayList<OrderLine> orderLines;
	private LocalDate payday;
	private int id;

	public Order(T customer) {
		this.customer = customer;
		this.orderLines = null;
		this.payday = null;
		this.id = 0;
	}

	public Order(T customer, ArrayList<OrderLine> orderLines, LocalDate payday){
		this.customer = customer;
		this.orderLines = orderLines;
		this.payday = payday;
	}
	
	public Order(T customer, ArrayList<OrderLine> orderLines, LocalDate payday, int id) {
		this.customer = customer;
		this.orderLines = orderLines;
		this.payday = payday;
		this.id = id;
	}

	public T getCustomer() {
		return customer;
	}

	public void setCustomer(T customer) {
		this.customer = customer;
	}

	public ArrayList<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public LocalDate getPayday() {
		return payday;
	}

	public void setPayday(LocalDate payday) {
		this.payday = payday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
