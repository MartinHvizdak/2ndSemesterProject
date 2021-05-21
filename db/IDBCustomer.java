package db;

import model.Customer;
import model.LTD;
import model.PrivateIndividual;
import model.SelfEmployed;

public interface IDBCustomer {
    String getCustomerType(String customerEmail) throws DBException;

    Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

    LTD retrieveLTDByEmail(String customerEmail) throws DBException;

    PrivateIndividual retrievePrivateIndividualByEmail(String customerEmail) throws DBException;

    SelfEmployed retrieveSelfEmployedByEmail(String customerEmail) throws DBException;

    public boolean savePrivateIndividualWithUserInputInDB(PrivateIndividual privateIndividual) throws DBException;

    public boolean saveSelfEmployedWithUserInputInDB(SelfEmployed selfEmployed) throws DBException;

    public boolean saveLTDUserInputInDB(LTD ltd) throws DBException;
}
