package controller;

import db.DBEmployee;
import db.DBException;
import model.Employee;
import model.LTD;

import java.util.ArrayList;

public class EmployeeController {
    private CustomerController customerController;
    private DBEmployee dbEmployee;

    public EmployeeController(){
        customerController =  new CustomerController();
        dbEmployee =  new DBEmployee();
    }

    public boolean saveEmployeeWithUserInputInDB(String employeePersonalID, String employeeFirstName, String employeeLastName, double employeeSalary, double employeeGeneratedIncome, String employeeLtdEmail) throws DBException {
        Employee employee =  new Employee(employeePersonalID, employeeFirstName, employeeLastName, employeeSalary, employeeGeneratedIncome);
        LTD ltd =  null;
        if (employeeLtdEmail != null){
            ltd = customerController.getLTDByEmailFromDB(employeeLtdEmail);
        }
        return dbEmployee.saveEmployee(employee, ltd);
    }

    public ArrayList<Employee> getAllEmployeesFromDB() throws DBException {
        return dbEmployee.retrieveAllEmployees();
    }

    public Employee getEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbEmployee.retrieveEmployeeByID(employeePersonalID);
    }

    public String getEmployeeLTDEmailByID(String employeePersonalID) throws DBException {
        return dbEmployee.retrieveEmployeeLTDEmail(employeePersonalID);
    }

    public boolean updateEmployeeWithUserInputInDB(Employee employee, String newEmployeePersonalID, String newEmployeeFirstName, String newEmployeeLastName, double newEmployeeSalary, double newEmployeeGeneratedIncome, String newEmployeeLtdEmail) throws DBException {
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
        return dbEmployee.updateEmployee(oldPersonalID, employee, ltd);
    }

    public boolean deleteEmployeeByIDFromDB(String employeePersonalID) throws DBException {
        return dbEmployee.deleteEmployeeByID(employeePersonalID);
    }
}
