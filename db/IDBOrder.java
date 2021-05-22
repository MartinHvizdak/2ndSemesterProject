package db;

import java.time.LocalDate;
import java.util.List;

import model.Customer;
import model.Order;

public interface IDBOrder {

	boolean saveOrder(Order<Customer> order) throws DBException;
	
	Order retrieveOrderByID(int id) throws DBException;
	
	boolean deleteOrderByID(int id) throws DBException;

	boolean updateOrder(Order<Customer> order) throws DBException;
	
	List<Order> retrieveOrdersByEmailAndPeriod(String email, LocalDate startDate, LocalDate endDate) throws DBException;
}
