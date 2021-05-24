package ui;

import java.util.ArrayList;
import java.util.Scanner;

import controller.EmployeeController;
import controller.OwnerController;
import db.DBException;
import model.Owner;
import model.Service;

public class OwnerMenu {

    private InputValidation inputValidation;
    private ArrayList<String> menuOptions;
    private EmployeeController employeeController;
    private OwnerController ownerController;

    public OwnerMenu() {

        ownerController =  new OwnerController();
        employeeController = new EmployeeController();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Create owner");
        menuOptions.add("Show owner");
        menuOptions.add("Update owner");
        menuOptions.add("Delete owner");
        menuOptions.add("Go to main menu");
    }

    private void createOwner() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate owner:");

        System.out.println("Enter owner personal ID: ");
        String ownerPersonalID = scanner.next();

        scanner.nextLine();
        System.out.println("Enter owner first name: ");
        String ownerFirstName = scanner.nextLine();

        System.out.println("Enter owner surname: ");
        String ownerSurname = scanner.nextLine();

        System.out.println("Enter owner relation: ");
        String ownerRelation = scanner.nextLine();

        ArrayList<String> ownerLtdEmails =  new ArrayList<>();
        while (true){
            System.out.println("Enter owner LTD email: ");
            String ownerLTDEmail = scanner.next();
            ownerLtdEmails.add(ownerLTDEmail);

            System.out.println("Press '1' to add next service or any other key to end adding");
            if (!scanner.next().equals("1"))
                break;
        }

        String ownerName = ownerFirstName + " " + ownerSurname;

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        System.out.println("Owner personal ID: '" + ownerPersonalID + "'");
        System.out.println("Name: " + ownerName);
        System.out.println("Relation: " + ownerRelation);
        System.out.println("Owner's LTDs: ");
        for (String email : ownerLtdEmails)
            System.out.println(email);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                ownerController.saveOwnerWithUserInputInDB(ownerPersonalID, ownerFirstName, ownerSurname, ownerRelation, ownerLtdEmails);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Owner not saved");
    }

    private void showOwner() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tOwner details:");
        System.out.println("Enter owner personal ID: ");
        String ownerPersonalID = scanner.next();
        Owner owner;
        ArrayList<String> ltdEmails;
        try {
            owner = ownerController.getOwnerByIDFromDB(ownerPersonalID);
            ltdEmails = ownerController.getOwnerLTDEmailsByIDFromDB(ownerPersonalID);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Owner personal ID: '" + ownerPersonalID + "'");
        System.out.println("Name: " + ownerController.getName(owner));
        System.out.println("Relation: " + ownerController.getRelation(owner));
        System.out.println("\tOwner's LTDs: ");
        for(String ltd : ltdEmails)
            System.out.println(ltd);
    }

    private void updateOwner() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tOwner details:");
        System.out.println("Enter owner personal ID: ");
        String ownerPersonalID = scanner.next();
        Owner owner;
        ArrayList<String> ltdEmails;
        try {
            owner = ownerController.getOwnerByIDFromDB(ownerPersonalID);
            ltdEmails = ownerController.getOwnerLTDEmailsByIDFromDB(ownerPersonalID);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Owner personal ID: '" + ownerPersonalID + "'");
        System.out.println("Name: " + ownerController.getName(owner));
        System.out.println("Relation: " + ownerController.getRelation(owner));
        System.out.println("\tOwner LTDs: ");
        for(String ltd : ltdEmails)
            System.out.println(ltd);

        System.out.println("Enter new owner personal ID: ");
        String newOwnerPersonalID = scanner.next();

        scanner.nextLine();
        System.out.println("Enter owner first name: ");
        String newOwnerFirstName = scanner.nextLine();

        System.out.println("Enter owner surname: ");
        String newOwnerSurname = scanner.nextLine();

        System.out.println("Enter owner relation: ");
        String newOwnerRelation = scanner.nextLine();

        ArrayList<String> newOwnerLtdEmails =  new ArrayList<>();
        while (true){
            System.out.println("Enter owner LTD email: ");
            String ownerLTDEmail = scanner.next();
            newOwnerLtdEmails.add(ownerLTDEmail);

            System.out.println("Press '1' to add next service or any other key to end adding");
            if (!scanner.next().equals("1"))
                break;
        }

        String newOwnerName = newOwnerFirstName + " " + newOwnerSurname;

        System.out.println("\n\tEnter 'c' to confirm  and save an employee or any other key to cancel: ");
        System.out.println("Owner personal ID: '" + ownerPersonalID + "'");
        System.out.println("Name: " + newOwnerName);
        System.out.println("Relation: " + newOwnerRelation);
        System.out.println("Owner's LTDs: ");
        for (String email : newOwnerLtdEmails)
            System.out.println(email);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                ownerController.updateOwnerWithUserInputInDB(owner, newOwnerPersonalID, newOwnerFirstName, newOwnerSurname, newOwnerRelation, newOwnerLtdEmails);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Owner changes declined");
    }

    private void deleteOwner() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("Enter owner personal ID: ");
        String ownerPersonalID = scanner.next();

        System.out.println("\n\tEnter 'c' to confirm deleting the employee with name" + ownerPersonalID + ": ");
        if (scanner.next().toLowerCase().equals("c")) {
            try {
                ownerController.deleteOwnerByIDFromDB(ownerPersonalID);
            }catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Removal declined");
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
                    createOwner();
                    break;
                case 2:
                    showOwner();
                    break;
                case 3:
                    updateOwner();
                    break;
                case 4:
                    deleteOwner();
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
