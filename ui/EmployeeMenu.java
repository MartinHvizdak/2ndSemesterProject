package ui;

import java.util.ArrayList;
import java.util.Scanner;

import controller.EmployeeController;
import db.DBException;
import model.CustomerEmployee;

public class EmployeeMenu {

    private InputValidation inputValidation;
    private ArrayList<String> menuOptions;
    private EmployeeController employeeController;

    public EmployeeMenu() {

        employeeController = new EmployeeController();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Create employee");
        menuOptions.add("Show employee");
        menuOptions.add("Update employee");
        menuOptions.add("Delete employee");
        menuOptions.add("Go to main menu");
    }

    private void createEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate employee:");

        System.out.println("Enter employee personal ID: ");
        String employeePersonalID = scanner.next();

        scanner.nextLine();
        System.out.println("Enter employee first name: ");
        String employeeFirstName = scanner.next();

        System.out.println("Enter employee second name: ");
        String employeeLastName = scanner.next();

        System.out.println("Enter employee salary: ");
        double employeeSalary = scanner.nextDouble();

        System.out.println("Enter generated income by employee: ");
        double employeeGeneratedIncome = scanner.nextDouble();

        System.out.println("Enter employee's ltd email: ");
        String employeeLtdEmail = scanner.next();

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        System.out.println("Employee personal ID: '" + employeePersonalID + "'");
        System.out.println("Name: " + employeeFirstName);
        System.out.println("Salary: " + employeeSalary);
        System.out.println("Generated income: " + employeeGeneratedIncome);
        System.out.println("=Employee's ltd email: " + employeeLtdEmail);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                employeeController.saveEmployeeWithUserInputInDB(employeePersonalID, employeeFirstName, employeeLastName, employeeSalary, employeeGeneratedIncome, employeeLtdEmail);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Employee not saved");
    }

    private void showEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tEmployee details:");
        System.out.println("Enter employee personal id: ");
        String employeePersonalID = scanner.next();
        CustomerEmployee employee;
        try {
            employee = employeeController.getEmployeeByIDFromDB(employeePersonalID);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Employee personal ID: '" + employeeController.getID(employee) + "'");
        System.out.println("Name: " + employee.getFirstName()+ employee.getSecondName());
        System.out.println("Salary: " + employeeController.getSalary(employee));
        System.out.println("Generated income: " + employeeController.getGeneratedIncome(employee));
    }

    private void updateEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tEmployee details:");
        System.out.println("Enter employee id: ");
        String employeePersonalID = scanner.nextLine();
        CustomerEmployee employee;
        try {
            employee = employeeController.getEmployeeByIDFromDB(employeePersonalID);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\n\tService information:");
        System.out.println("Employee personal ID: '" + employeeController.getID(employee) + "'");
        System.out.println("Name: " + employeeController.getFirstName(employee));
        System.out.println("Salary: " + employeeController.getSalary(employee));
        System.out.println("Generated income: " + employeeController.getGeneratedIncome(employee));

        System.out.println("Enter new employee personal ID: ");
        String newEmployeePersonalID = scanner.next();

        System.out.println("Enter new employee first name: ");
        String newEmployeeFirstName = scanner.next();

        System.out.println("Enter new employee second name: ");
        String newEmployeeSecondName = scanner.next();

        System.out.println("Enter new employee salary: ");
        double newEmployeeSalary = scanner.nextDouble();

        System.out.println("Enter new generated income by employee: ");
        double newEmployeeGeneratedIncome = scanner.nextDouble();

        System.out.println("Enter employee's ltd email: ");
        String newEmployeeLtdEmail = scanner.next();


        System.out.println("\n\tEnter 'c' to confirm  and save an employee or any other key to cancel: ");
        System.out.println("Employee personal ID: '" + newEmployeePersonalID + "'");
        System.out.println("Name: " + newEmployeeFirstName + newEmployeeSecondName);
        System.out.println("Salary: " + newEmployeeSalary);
        System.out.println("Generated income: " + newEmployeeGeneratedIncome);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                employeeController.updateEmployeeWithUserInputInDB(employee, newEmployeePersonalID, newEmployeeFirstName, newEmployeeSecondName, newEmployeeSalary, newEmployeeGeneratedIncome, newEmployeeLtdEmail);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Employee changes declined");
    }

    private void deleteEmployee() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("Enter employee personal id: ");
        String employeePersonalID = scanner.next();

        System.out.println("\n\tEnter 'c' to confirm deleting the employee with name" + employeePersonalID + ": ");
        if (scanner.next().toLowerCase().equals("c")) {
            try {
                employeeController.deleteEmployeeByIDFromDB(employeePersonalID);
            }catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Removal declined");
    }

    private void displayMenu(){
        System.out.println("\n\t Employee Menu:");
        for(String option : menuOptions)
            System.out.println((menuOptions.indexOf(option)+1) + ". " + option);
    }

    public void openMenu() {
        boolean quit = false;
        while(!quit) {
            displayMenu();
            int choice = inputValidation.getIntFromUser("Choose an option: ");

            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    showEmployee();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong Choice!");
                    break;
            }
        }
    }
}
