package db;

import model.Customer;
import model.LTD;
import model.PrivateIndividual;
import model.SelfEmployed;

public interface IDBCustomer {
	Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

	public String getCustomerType(String customerEmail) throws DBException;

	public LTD retrieveLTDByEmail(String customerEmail) throws DBException;

	public SelfEmployed retrieveSelfEmployedByEmail(String customerEmail) throws DBException;

	public PrivateIndividual retrievePrivateIndividualByEmail(String customerEmail) throws DBException;
	
	public boolean savePrivateIndividualWithUserInputInDB(PrivateIndividual privateIndividual) throws DBException;
	
	public boolean saveSelfEmployedWithUserInputInDB(SelfEmployed selfEmployed) throws DBException;

	public boolean saveLTDUserInputInDB(LTD ltd) throws DBException;
}
