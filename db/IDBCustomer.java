package db;

import model.Customer;
import model.LTD;
import model.PrivateIndividual;
import model.SelfEmployed;

import java.util.ArrayList;

public interface IDBCustomer {
    String getCustomerType(String customerEmail) throws DBException;

    ArrayList<Customer> retrieveAllCustomers() throws DBException;

    Customer retrieveCustomerByEmail(String customerEmail) throws DBException;

    ArrayList<LTD> retrieveAllLTDs() throws DBException;

    ArrayList<String> retrieveAllLTDEmails() throws DBException;

    LTD retrieveLTDByEmail(String customerEmail) throws DBException;

    PrivateIndividual retrievePrivateIndividualByEmail(String customerEmail) throws DBException;

    SelfEmployed retrieveSelfEmployedByEmail(String customerEmail) throws DBException;

    boolean savePrivateIndividualWithUserInputInDB(PrivateIndividual privateIndividual) throws DBException;

    boolean saveSelfEmployedWithUserInputInDB(SelfEmployed selfEmployed) throws DBException;

    public boolean saveLTDUserInputInDB(LTD ltd) throws DBException;

    boolean updatePrivateIndividual(String oldEmail, PrivateIndividual privateIndividual) throws DBException;

    boolean updateSelfEmployed(String oldEmail, SelfEmployed selfEmployed) throws DBException;

    boolean updateLTD(String oldEmail, LTD ltd) throws DBException;

    boolean deleteEveryCustomerTypeByEmail(String email) throws DBException;
}
