package ui;

import java.util.ArrayList;
import java.util.Scanner;

import controller.ServiceController;
import db.DBException;
import model.Service;

public class ServiceMenu {

    private ServiceController serviceController;
    private InputValidation inputValidation;
    private ArrayList<String> menuOptions;

    public ServiceMenu() {

        serviceController =  new ServiceController();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Create service");
        menuOptions.add("Show service");
        menuOptions.add("Update service");
        menuOptions.add("Delete service");
        menuOptions.add("Go to main menu");
    }

    private void createService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate service:");

        System.out.println("Enter service name: ");
        String serviceName = scanner.nextLine();

        System.out.println("Enter service description: ");
        String serviceDescription = scanner.nextLine();

        System.out.println("Enter service price: ");
        double servicePrice = scanner.nextDouble();

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        System.out.println("Service name: '" + serviceName + "'");
        System.out.println("Description: " + serviceDescription);
        System.out.println("Price: " + servicePrice);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                serviceController.saveServiceWithUserInputInDB(serviceName, serviceDescription, servicePrice);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Service not saved");
    }

    private void showService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tService details:");
        System.out.println("Enter service name: ");
        String serviceName = scanner.nextLine();
        Service service;
        try {
            service = serviceController.getServiceByNameFromDB(serviceName);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\n\tService information:");
        System.out.println("Service name: " + serviceController.getName(service));
        System.out.println("Description: '" + serviceController.getDescription(service) + "'");
        System.out.println("Price: " + serviceController.getPrice(service));
    }

    private void updateService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tService details:");
        System.out.println("Enter service name: ");
        String serviceName = scanner.nextLine();
        Service service;
        try {
            service = serviceController.getServiceByNameFromDB(serviceName);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\n\tService information:");
        System.out.println("Service name: " + serviceController.getName(service));
        System.out.println("Description: '" + serviceController.getDescription(service) + "'");
        System.out.println("Price: " + serviceController.getPrice(service));

        System.out.println("Enter new service name: ");
        String newServiceName = scanner.nextLine();

        System.out.println("Enter new service description: ");
        String newServiceDescription = scanner.nextLine();

        System.out.println("Enter new service price: ");
        double newServicePrice = scanner.nextDouble();

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        System.out.println("Sew service name: '" + newServiceName + "'");
        System.out.println("New description: " + newServiceDescription);
        System.out.println("Mew price: " + newServicePrice);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                serviceController.updateServiceWithUserInputInDB(service, newServiceName, newServiceDescription, newServicePrice);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Service changes declined");
    }

    private void deleteService() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("Enter new service name: ");
        String serviceName = scanner.nextLine();

        System.out.println("\n\tEnter 'c' to confirm deleting the service with name" + serviceName + ": ");
        if (scanner.next().toLowerCase().equals("c")) {
            try {
                serviceController.deleteServiceByNameFromDB(serviceName);
            }catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Removal declined");
    }

    private void displayMenu(){
        System.out.println("\n\t Service Menu:");
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
                    createService();
                    break;
                case 2:
                    showService();
                    break;
                case 3:
                    updateService();
                    break;
                case 4:
                    deleteService();
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
