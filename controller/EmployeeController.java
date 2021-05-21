package controller;

import db.DBCustomer;
import db.DBCustomerEmployee;
import db.DBException;
import model.Customer;
import model.CustomerEmployee;
import model.LTD;

public class EmployeeController {
    private CustomerController customerController;
    private DBCustomerEmployee dbCustomerEmployee;

    public EmployeeController(){
        customerController =  new CustomerController();
        dbCustomerEmployee =  new DBCustomerEmployee();
    }

    public String getID(CustomerEmployee employee){return employee.getId();}

    public String getFirstName(CustomerEmployee employee){return employee.getFirstName();}

    public double getSalary(CustomerEmployee employee){return employee.getSalary();}

    public double getGeneratedIncome(CustomerEmployee employee){return employee.getIncome();}

    public boolean saveEmployeeWithUserInputInDB(String employeePersonalID, String employeeFirstName, String employeeLastName, double employeeSalary, double employeeGeneratedIncome, String employeeLtdEmail) throws DBException {
        CustomerEmployee employee =  new CustomerEmployee(employeePersonalID, employeeFirstName, employeeLastName, employeeSalary, employeeGeneratedIncome);
        LTD ltd =  customerController.getLTDByEmailFromDB(employeeLtdEmail);
        return dbCustomerEmployee.saveEmployee(employee, ltd);
    }

    public CustomerEmployee getEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.retrieveEmployeeByID(employeePersonalID);
    }

    public boolean updateEmployeeWithUserInputInDB(CustomerEmployee employee, String newEmployeePersonalID, String newEmployeeFirstName, String newEmployeeLastName, double newEmployeeSalary, double newEmployeeGeneratedIncome, String newEmployeeLtdEmail) throws DBException {
        String oldPersonalID = employee.getId();
        employee.setId(newEmployeePersonalID);
        employee.setFirstName(newEmployeeFirstName);
        employee.setSecondName(newEmployeeLastName);
        employee.setSalary(newEmployeeSalary);
        employee.setIncome(newEmployeeGeneratedIncome);
        LTD ltd =  customerController.getLTDByEmailFromDB(newEmployeeLtdEmail);
        return dbCustomerEmployee.updateEmployee(oldPersonalID, employee, ltd);
    }

    public boolean deleteEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.deleteEmployeeByID(employeePersonalID);
    }
}
