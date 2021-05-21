package controller;

import db.DBException;
import model.*;
import db.DBCustomer;

import java.util.ArrayList;

public class CustomerController {
    DBCustomer dbCustomer;

    public CustomerController(){
        dbCustomer = new DBCustomer();
    }

    public String getEmail(Customer customer){
        return customer.getEmail();
    }

    public Customer getCustomerByEmailFromDB(String customerEmail) throws DBException {
        return dbCustomer.retrieveCustomerByEmail(customerEmail);
    }

    public LTD getLTDByEmailFromDB(String customerEmail) throws DBException {
        return dbCustomer.retrieveLTDByEmail(customerEmail);
    }

    public PrivateIndividual getPrivateIndividual(String customerEmail) throws DBException {
        return dbCustomer.retrievePrivateIndividualByEmail(customerEmail);
    }

    public SelfEmployed getSelfEmployed(String customerEmail) throws DBException {
        return dbCustomer.retrieveSelfEmployedByEmail(customerEmail);
    }

    public Customer createCustomer(){
        return new Customer();
    }

    public boolean savePrivateIndividualWithUserInputInDB(String name, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String ID, String VAT) throws DBException {
        PrivateIndividual privateIndividual = new PrivateIndividual(email, name, phoneNumber, city, zipCode, street, streetNumber, ID, VAT);

        return dbCustomer.savePrivateIndividualWithUserInputInDB(privateIndividual);
    }

    public boolean saveSelfEmployedWithUserInputInDB(String name, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String VAT, String marketNumber) throws DBException{
        SelfEmployed selfEmployed = new SelfEmployed(email, name, phoneNumber, city, zipCode, street, streetNumber, VAT, marketNumber);


        return dbCustomer.saveSelfEmployedWithUserInputInDB(selfEmployed);
    }

    public boolean saveLTDUserInputInDB(String name, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, ArrayList<CustomerEmployee> employees, ArrayList<Owner> owners, String marketRegisteredNumber, String marketNumber, boolean arePayers) throws DBException{
        LTD ltd = new LTD(email, name, phoneNumber, city, zipCode, street, streetNumber, employees, owners, marketRegisteredNumber, marketNumber, arePayers);


        return dbCustomer.saveLTDUserInputInDB(ltd);
    }
}
