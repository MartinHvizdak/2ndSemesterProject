package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;

import controller.CustomerController;
import controller.OrderController;
import controller.OrderLineController;
import controller.ServiceController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;
import model.Service;

import model.*;

public class CustomerMenu {

    private OrderController orderController;
    private OrderLineController orderLineController;
    private CustomerController customerController;
    private ServiceController serviceController;
    private InputValidation inputValidation;
    private ArrayList<String> menuOptions;

    public CustomerMenu() {
        
        customerController = new CustomerController();
       
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Create customer");


        customerController = new CustomerController();
    }

    private void createCustomer() {
    	String customerName;
    	String customerCity;
    	String customerStreet;
    	String customerNumber;
    	String customerZip;
    	String customerEmail;
    	String customerPhone;
    	
    	//PrivateIndividual
    	String id;
    	String vat;
    	
    	//SelfEmployed
    	String vatSelfEmpl;
    	String marketNumber;
    	
    	//LTD
    	ArrayList<CustomerEmployee> employees = new ArrayList<CustomerEmployee>();
    	ArrayList<Owner> owners = new ArrayList<Owner>();
    	String marketRegisterNumber;
    	String marketNumberLtd;
    	boolean arePayers;
    	
    	
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate customer:");

        System.out.println("Enter Customer's name: ");
        customerName	 = scanner.next();
        System.out.println("Enter Customer's city: ");
        customerCity	 = scanner.next();
        System.out.println("Enter Customer's street: ");
        customerStreet	 = scanner.next();
        System.out.println("Enter Customer's house number: ");
        customerNumber 	 = scanner.next();
        System.out.println("Enter Customer's ZIP: ");
        customerZip 	 = scanner.next();
        System.out.println("Enter Customer's email: ");
        customerEmail 	 = scanner.next();
        System.out.println("Enter Customer's phone number: ");
        customerPhone	 = scanner.next();
        
        System.out.println("Enter Type of customer:\n1. Private Individual\n2. Self Employed\n3. LTD ");
        int customerType = scanner.nextInt();
        
        
        

        if(customerType == 1) {
        	System.out.println("You Have Chosen a Private Individual Customer");
        	
        	 System.out.println("Enter id: ");
             id	 = scanner.next();
             System.out.println("Enter VAT number: ");
             vat	 = scanner.next();
             try {
				if (customerController.savePrivateIndividualWithUserInputInDB(customerName, customerCity, customerStreet, customerNumber, customerZip, customerEmail, customerPhone, id, vat) == true) {
					 System.out.println("SUCESSFULLY SAVED");
				 }
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(customerType == 2) {
        	System.out.println("You Have Chosen a Self Employed Customer");
        	
            System.out.println("Enter VAT number: ");
            vatSelfEmpl	 = scanner.next();
            System.out.println("Enter market number: ");
            marketNumber	 = scanner.next();
            
            try {
				if (customerController.saveSelfEmployedWithUserInputInDB(customerName, customerCity, customerStreet, customerNumber, customerZip, customerEmail, customerPhone, vatSelfEmpl, marketNumber) == true) {
					 System.out.println("SUCESSFULLY SAVED");
				 }
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        else if(customerType == 3) {
        	System.out.println("You Have Chosen a LTD Customer");
        	
        	System.out.println("Enter LTD's market register number: ");
        	marketRegisterNumber = scanner.next();
        	System.out.println("Enter LTD's market number: ");
        	marketNumberLtd = scanner.next();
        	System.out.println("Are LTD's payers? (write y/n): ");
        	if(scanner.next().equals("y")) {
        		arePayers = true;
        		System.out.println("Bool true: ");
        	} else {
        		arePayers = false;
        	}
        	
        	System.out.println("Press 'y' to add an employee: ");
        	while(scanner.next().equals("y")) {
        		System.out.println("Write employee's ID: ");
        		String idEmployee = scanner.next();
        		System.out.println("Write employee's Name: ");
        		String nameEmployee = scanner.next();
        		System.out.println("Write employee's Salary: ");
        		Double salaryEmployee = scanner.nextDouble();
        		System.out.println("Write employee's Total generated income: ");
        		Double generatedIncomeEmployee = scanner.nextDouble();
        		
        		employees.add(new CustomerEmployee(idEmployee, nameEmployee, salaryEmployee, generatedIncomeEmployee));
        		

            	System.out.println("Press 'y' to add another employee: ");
        	}
        	
        	System.out.println("Press 'y' to add an owner: ");
        	while(scanner.next().equals("y")) {
        		System.out.println("Write owner's ID: ");
        		String idOwner = scanner.next();
        		System.out.println("Write owner's name: ");
        		String nameOwner = scanner.next();
        		System.out.println("Write owner's relation: ");
        		String relationOwner = scanner.next();
        		
        		
        		owners.add(new Owner(idOwner, nameOwner, relationOwner));
        		

            	System.out.println("Press 'y' to add another owner: ");
            	}
        	
        	try {
				if (customerController.saveLTDUserInputInDB(customerName, customerCity, customerStreet, customerNumber, customerZip, customerEmail, customerPhone, employees, owners, marketRegisterNumber, marketNumberLtd, arePayers) == true) {
					 System.out.println("SUCESSFULLY SAVED");
				 }
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        } else {
        	System.out.println("You Have Chosen invalid Customer type");
        }

        System.out.println("Enter payday date(dd-mm-yyy): ");
        String paydayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale( Locale.UK );
        LocalDate payday = LocalDate.parse(paydayString, formatter);

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        //System.out.println("Customer email: '" + customerEmail + "'");
        System.out.println("Added services and their quantity: ");
     
    }
        private void displayMenu(){
            System.out.println("\n\t Main Menu:");
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
                    createCustomer();
                    break;
                case 2:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong Choice!");
                    break;
            }
        }
    }
}
