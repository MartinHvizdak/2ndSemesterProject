package db;

import java.util.ArrayList;

import model.CustomerEmployee;
import model.Employee;
import model.LTD;


public interface IDBEmployee {
    boolean saveEmployee(Employee employee, LTD ltd) throws DBException;

    ArrayList<Employee> retrieveAllEmployees() throws DBException;

    Employee retrieveEmployeeByID(String employeePersonalID) throws DBException;

    String retrieveEmployeeLTDEmail(String employeePersonalID) throws DBException;

    boolean updateEmployee(String oldPersonalID, Employee employee, LTD ltd) throws DBException;

    boolean deleteEmployeeByID(String employeePersonalID) throws DBException;
}
