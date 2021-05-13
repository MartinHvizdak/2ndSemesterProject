package db;

import model.Customer;
import model.Order;

import java.time.LocalDate;
import java.util.Currency;

public interface IDBOrder {

	void saveOrder(Order<Customer> order) throws DBException;
	
	Order retrieveOrderByID(int id) throws DBException;
	
	void deleteOrderByID(int id) throws DBException;

	void updateOrder(Order<Customer> order) throws DBException;
}
