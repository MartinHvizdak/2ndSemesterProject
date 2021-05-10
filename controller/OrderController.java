package controller;

import model.*;
import db.IDBOrder;

import java.time.LocalDateTime;

public class OrderController {
	
	private IDBOrder iDBOrder;
	private ServiceController serviceController;
	
	public OrderController () {
		
	}
	
	public Order createOrder (Order order) {
		// to be implemented
		return null;
	}
	
	public void addService (Order order, String nameOfChosenService, int quantity) {
		// to be implemented
	}
	
	public void setPayDay (Order order, LocalDateTime payday) {
		// to be implemented
	}
	
	public void saveOrderInDB (boolean ifProceed, Order order) {
		// to be implemented
	}
	
	public Order readOrder(int id) {
		// to be implemented
		return null;
	}
	
	public void setCustomer(Order order, Customer customer) {
		// to be implemented
	}
	
	public void removeOrderLine(Order order, String serviceName) {
		// to be implemented
	}
	
	public void deleteOrderFromDB(int id) {
		// to be implemented
	}
}
