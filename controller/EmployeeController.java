package controller;

import db.DBCustomerEmployee;
import db.DBException;
import model.CustomerEmployee;
import model.LTD;

import java.util.ArrayList;

public class EmployeeController {
    private CustomerController customerController;
    private DBCustomerEmployee dbCustomerEmployee;

    public EmployeeController(){
        customerController =  new CustomerController();
        dbCustomerEmployee =  new DBCustomerEmployee();
    }

    public String getFirstName(CustomerEmployee employee){return employee.getFirstName();}

    public boolean saveEmployeeWithUserInputInDB(String employeePersonalID, String employeeFirstName, String employeeLastName, double employeeSalary, double employeeGeneratedIncome, String employeeLtdEmail) throws DBException {
        CustomerEmployee employee =  new CustomerEmployee(employeePersonalID, employeeFirstName, employeeLastName, employeeSalary, employeeGeneratedIncome);
        LTD ltd =  null;
        if (employeeLtdEmail != null){
            ltd = customerController.getLTDByEmailFromDB(employeeLtdEmail);
        }
        return dbCustomerEmployee.saveEmployee(employee, ltd);
    }

    public ArrayList<CustomerEmployee> getAllEmployeesFromDB() throws DBException {
        return dbCustomerEmployee.retrieveAllEmployees();
    }

    public CustomerEmployee getEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.retrieveEmployeeByID(employeePersonalID);
    }

    public String getEmployeeLTDEmailByID(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.retrieveEmployeeLTDEmail(employeePersonalID);
    }

    public boolean updateEmployeeWithUserInputInDB(CustomerEmployee employee, String newEmployeePersonalID, String newEmployeeFirstName, String newEmployeeLastName, double newEmployeeSalary, double newEmployeeGeneratedIncome, String newEmployeeLtdEmail) throws DBException {
        String oldPersonalID = employee.getId();
        employee.setId(newEmployeePersonalID);
        employee.setFirstName(newEmployeeFirstName);
        employee.setSurName(newEmployeeLastName);
        employee.setSalary(newEmployeeSalary);
        employee.setIncome(newEmployeeGeneratedIncome);
        LTD ltd =  null;
        if (newEmployeeLtdEmail != null){
            ltd = customerController.getLTDByEmailFromDB(newEmployeeLtdEmail);
        }
        return dbCustomerEmployee.updateEmployee(oldPersonalID, employee, ltd);
    }

    public boolean deleteEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.deleteEmployeeByID(employeePersonalID);
    }
}
