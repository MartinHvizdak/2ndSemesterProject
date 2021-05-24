package controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import db.DBException;
import db.DBOrder;
import model.*;

public class OrderController {

	private DBOrder dbOrder;
	private CustomerController customerController;
	private OrderLineController orderLineController;
	private ServiceController serviceController;
	
	public OrderController () {
		dbOrder = new DBOrder();
		customerController =  new CustomerController();
		orderLineController = new OrderLineController();
		serviceController =  new ServiceController();
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

	public void setCustomer(Order order, Customer customer) { order.setCustomer(customer); }

	public void setOrderLines(Order order, ArrayList<OrderLine> orderLines) {
		order.setOrderLines(orderLines);
	}

	public void setPayday(Order order, LocalDate payDay) { order.setPayday(payDay); }

	public void deleteOrderFromDBByID(int orderID) throws DBException {
		dbOrder.deleteOrderByID(orderID);
	}

	public ArrayList<Order<Customer>> getAllOrdersFromDB() throws DBException {
		return dbOrder.retrieveAllOrders();
	}

	public Order getOrderFromDB(int orderID) throws DBException {
		return dbOrder.retrieveOrderByID(orderID);
	}

	public int saveOrderWithUserInputInDB(String customerEmail, HashMap<Service, Integer> servicesAndQuantity, LocalDate payday) throws DBException {
		Customer customer = customerController.getCustomerByEmailFromDB(customerEmail);
		ArrayList<OrderLine> orderlines =  new ArrayList<>();
		for(Service service : servicesAndQuantity.keySet()) {
			int quantity = servicesAndQuantity.get(service);
			orderlines.add(orderLineController.createOrderLine(service, quantity));
		}
		String idString = "" + Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + Calendar.getInstance().get(Calendar.YEAR) % 2000 +
				(int)(Math.random() * 1000);
		while (idString.length()<10)
			idString += "0";
		int id = Integer.parseInt(idString);

		Order order = new Order(customer, orderlines, payday, id);
		dbOrder.saveOrder(order);
		return id;
	}

	public void updateOrderWithUserInput(Order order, String customerEmail, HashMap<Service, Integer> serviceQuantity, LocalDate payday) throws DBException{
		Customer customer = customerController.getCustomerByEmailFromDB(customerEmail);
		order.setCustomer(customer);

		ArrayList<OrderLine> orderlines =  new ArrayList<>();
		for(Service service : serviceQuantity.keySet())
			orderlines.add(orderLineController.createOrderLine(service, serviceQuantity.get(service)));
		order.setOrderLines(orderlines);

		order.setPayday(payday);

		dbOrder.updateOrder(order);
	}
}
