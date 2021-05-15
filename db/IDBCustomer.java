package db;

import model.Customer;
import model.LTD;
import model.PrivateIndividual;
import model.SelfEmployeed;

public interface IDBCustomer {
	Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

	public String getCustomerType(String customerEmail) throws DBException;

	public LTD retrieveLTDByEmail(String customerEmail) throws DBException;

	public SelfEmployeed retrieveSelfEmployedByEmail(String customerEmail) throws DBException;

	public PrivateIndividual retrievePrivateIndividualByEmail(String customerEmail) throws DBException;
}
