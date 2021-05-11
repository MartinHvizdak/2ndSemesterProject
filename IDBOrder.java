package db;

// Eventually will change to model.Order and model.Customer
import model.*;

public interface IDBOrder {

	public void saveOrder(Order order) throws DBException;
	
	public Order retrieveOrderByID(int id) throws DBException;
	
	public void deleteOrderByID(int id) throws DBException;
}
