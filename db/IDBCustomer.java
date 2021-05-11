package db;

import model.Customer;

public interface IDBCustomer {
		 	public Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

}
