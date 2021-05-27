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

    public ArrayList<Customer> getAllCustomersFromDB() throws DBException{
        return dbCustomer.retrieveAllCustomers();
    }

    public ArrayList<String> getAllLTDEmailsFromDB() throws DBException {
        return dbCustomer.retrieveAllLTDEmails();
    }

    public Customer getCustomerByEmailFromDB(String customerEmail) throws DBException {
        return dbCustomer.retrieveCustomerByEmail(customerEmail);
    }

    public String getCustomerTypeByEmailFromDB(String customerEmail) throws DBException {
        return dbCustomer.getCustomerType(customerEmail);
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

    public boolean savePrivateIndividualWithUserInputInDB(String firstName, String secondName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String ID, String VAT) throws DBException {
        PrivateIndividual privateIndividual = new PrivateIndividual(email, firstName, secondName, phoneNumber, city, zipCode, street, streetNumber, ID, VAT);

        return dbCustomer.savePrivateIndividualWithUserInputInDB(privateIndividual);
    }

    public boolean saveSelfEmployedWithUserInputInDB(String firstName, String secondName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String VAT, String marketNumber) throws DBException{
        SelfEmployed selfEmployed = new SelfEmployed(email, firstName, secondName, phoneNumber, city, zipCode, street, streetNumber, VAT, marketNumber);


        return dbCustomer.saveSelfEmployedWithUserInputInDB(selfEmployed);
    }

    public boolean saveLTDUserInputInDB(String companyName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, ArrayList<Employee> employees, ArrayList<Owner> owners, String marketRegistrationNumber, String marketNumber, boolean arePayers) throws DBException{
        LTD ltd = new LTD(email, companyName, phoneNumber, city, zipCode, street, streetNumber, employees, owners, marketRegistrationNumber, marketNumber, arePayers);


        return dbCustomer.saveLTDUserInputInDB(ltd);
    }

    public boolean updatePrivateIndividual(PrivateIndividual privateIndividual, String firstName, String secondName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String ID, String VAT) throws DBException {
        String oldEmail = privateIndividual.getEmail();
        privateIndividual.setFirstName(firstName);
        privateIndividual.setSecondName(secondName);
        privateIndividual.setCity(city);
        privateIndividual.setStreet(street);
        privateIndividual.setStreetNumber(streetNumber);
        privateIndividual.setZipCode(zipCode);
        privateIndividual.setEmail(email);
        privateIndividual.setPhoneNumber(phoneNumber);
        privateIndividual.setId(ID);
        privateIndividual.setVat(VAT);

        return dbCustomer.updatePrivateIndividual(oldEmail, privateIndividual);
    }

    public boolean updateSelfEmployed(SelfEmployed selfEmployed, String firstName, String secondName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, String VAT, String marketNumber) throws DBException {
        String oldEmail = selfEmployed.getEmail();
        selfEmployed.setFirstName(firstName);
        selfEmployed.setSecondName(secondName);
        selfEmployed.setCity(city);
        selfEmployed.setStreet(street);
        selfEmployed.setStreetNumber(streetNumber);
        selfEmployed.setZipCode(zipCode);
        selfEmployed.setEmail(email);
        selfEmployed.setPhoneNumber(phoneNumber);
        selfEmployed.setMarketNumber(marketNumber);
        selfEmployed.setVat(VAT);

        return dbCustomer.updateSelfEmployed(oldEmail, selfEmployed);
    }

    public boolean updateLTD(LTD ltd, String companyName, String city, String street, String streetNumber, String zipCode, String email, String phoneNumber, ArrayList<Employee> employees, ArrayList<Owner> owners, String marketRegistrationNumber, String marketNumber, boolean arePayers) throws DBException {
        String oldEmail = ltd.getEmail();
        ltd.setCompanyName(companyName);
        ltd.setCity(city);
        ltd.setStreet(street);
        ltd.setStreetNumber(streetNumber);
        ltd.setZipCode(zipCode);
        ltd.setEmail(email);
        ltd.setPhoneNumber(phoneNumber);
        ltd.setMarketNumber(marketNumber);
        ltd.setArePayers(arePayers);
        ltd.setMarketRegisterNumber(marketRegistrationNumber);
        ltd.setEmployees(employees);
        ltd.setOwners(owners);

        return dbCustomer.updateLTD(oldEmail, ltd);
    }

    public boolean deleteEveryCustomerTypeByEmailFromDB(String email) throws DBException {
        return dbCustomer.deleteEveryCustomerTypeByEmail(email);
    }
}
