package controller;

import db.DBCustomerEmployee;
import db.DBException;
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

    public String getName(CustomerEmployee employee){return employee.getFirstName() + " " + employee.getSecondName();}
    
    public double getSalary(CustomerEmployee employee){return employee.getSalary();}

    public double getGeneratedIncome(CustomerEmployee employee){return employee.getIncome();}

    public boolean saveEmployeeWithUserInputInDB(String employeePersonalID, String employeeFirstName, String employeeSecondName, double employeeSalary, double employeeGeneratedIncome, String employeeLtdEmail) throws DBException {
        CustomerEmployee employee =  new CustomerEmployee(employeePersonalID, employeeFirstName, employeeSecondName, employeeSalary, employeeGeneratedIncome);
        LTD ltd =  customerController.getLTDByEmailFromDB(employeeLtdEmail);
        return dbCustomerEmployee.saveEmployee(employee, ltd);
    }

    public CustomerEmployee getEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.retrieveEmployeeByID(employeePersonalID);
    }

    public boolean updateEmployeeWithUserInputInDB(CustomerEmployee employee, String newEmployeePersonalID, String newEmployeeFirstName, String newEmployeeSecondName, double newEmployeeSalary, double newEmployeeGeneratedIncome, String newEmployeeLtdEmail) throws DBException {
        String oldPersonalID = employee.getId();
        employee.setId(newEmployeePersonalID);
        employee.setFirstName(newEmployeeFirstName);
        employee.setSecondName(newEmployeeSecondName);
        employee.setSalary(newEmployeeSalary);
        employee.setIncome(newEmployeeGeneratedIncome);
        LTD ltd =  customerController.getLTDByEmailFromDB(newEmployeeLtdEmail);
        return dbCustomerEmployee.updateEmployee(oldPersonalID, employee, ltd);
    }

    public boolean deleteEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbCustomerEmployee.deleteEmployeeByID(employeePersonalID);
    }
}
