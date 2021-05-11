package controller;

import db.DBException;
import db.DBOrder;
import model.*;
import db.IDBOrder;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderController {
	
	private DBOrder dbOrder;
	private ServiceController serviceController;
	
	public OrderController () {
		dbOrder = new DBOrder();
	}

	public void setCustomer(Order order, Customer customer) throws DBException {
		order.setCustomer(customer);
		dbOrder.saveOrder(order);
	}
	
	public Order createOrder (Customer customer) {
		return new Order(customer);
	}
	
	public void addOrderLine (Order order, OrderLine orderLine) throws DBException {
		order.addOrderLine(orderLine);
		dbOrder.saveOrder(order);
	}
	
	public void setPayday (Order order, LocalDate payday) throws DBException {
		order.setPayday(payday);
		dbOrder.saveOrder(order);
	}
	
	public void saveOrderInDB (Order order) throws DBException {
		dbOrder.saveOrder(order);
	}
	
	public Order getOrder(int id) throws DBException {
		return dbOrder.retrieveOrderByID(id);
	}

	public Customer getCustomer(Order order) {
		return order.getCustomer();
	}

	public ArrayList<OrderLine> getOrderLines(Order order) {
		return order.getOrderLines();
	}

	public LocalDate getPayday(Order order) {
		return order.getPayday();
	}
	
	public void removeOrderLine(Order order,  OrderLine orderLine) throws DBException {
		order.removeOrderLine(orderLine);
		dbOrder.saveOrder(order);
	}
	
	public void deleteOrderFromDB(int id) throws DBException {
		dbOrder.deleteOrderByID(id);
	}
}
