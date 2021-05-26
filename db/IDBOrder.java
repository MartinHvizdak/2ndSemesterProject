package db;

import model.Customer;
import model.Order;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IDBOrder {

	boolean saveOrder(Order<Customer> order) throws DBException;

	ArrayList<Order<Customer>> retrieveAllOrders() throws DBException;
	
	Order retrieveOrderByID(int id) throws DBException;
	
	boolean deleteOrderByID(int id) throws DBException;

	boolean updateOrder(Order<Customer> order) throws DBException;

	ArrayList<Order> retrieveOrdersByEmailAndPeriod(String email, LocalDate startDate, LocalDate endDate)
			throws DBException;
}
