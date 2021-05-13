package db;

import model.Customer;

public interface IDBCustomer {
	Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

}
