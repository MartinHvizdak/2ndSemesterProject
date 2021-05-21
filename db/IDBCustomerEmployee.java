package db;

import model.CustomerEmployee;
import model.LTD;

public interface IDBCustomerEmployee {

    boolean saveEmployee(CustomerEmployee employee, LTD ltd) throws DBException;

    CustomerEmployee retrieveEmployeeByID(String employeePersonalID) throws DBException;

    boolean updateEmployee(String oldPersonalID, CustomerEmployee employee, LTD ltd) throws DBException;

    boolean deleteEmployeeByID(String employeePersonalID) throws DBException;
}
