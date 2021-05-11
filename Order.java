package model;

import java.util.ArrayList;
import java.time.LocalDate;

public class Order {
	private Customer customer;
	private ArrayList<OrderLine> orderLines;
	private LocalDate payday;
	private int id;

	public Order(Customer customer) {
		this.customer = customer;
		this.orderLines = null;
		this.payday = null;
		this.id = 0;
	}
	
	public Order(Customer customer, ArrayList<OrderLine> orderLines, LocalDate payday, int id) {
		this.customer = customer;
		this.orderLines = orderLines;
		this.payday = payday;
		this.id = id;
	}

	public void addOrderLine(OrderLine ol){this.orderLines.add(ol);}

	public void removeOrderLine(OrderLine ol){this.orderLines.remove(ol);}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
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
