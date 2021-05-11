package controller;

import db.DBException;
import model.Customer;
import db.DBCustomer;

public class CustomerController {
    DBCustomer dbCustomer;

    public CustomerController(){
        dbCustomer = new DBCustomer();
    }

    public String getEmail(Customer customer){
        return customer.getEmail();
    }

    public Customer getCustomer(String customerEmail) throws DBException {
        return dbCustomer.retrieveCustomerByEmail(customerEmail);
    }
}
