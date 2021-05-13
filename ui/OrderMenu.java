package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controller.CustomerController;
import controller.OrderController;
import controller.OrderLineController;
import controller.ServiceController;
import db.DBException;
import model.Customer;
import model.Order;
import model.OrderLine;
import model.Service;

public class OrderMenu {

    private OrderController orderController;
    private OrderLineController orderLineController;
    private CustomerController customerController;
    private ServiceController serviceController;
    private InputValidation inputValidation;
    private ArrayList<String> menuOptions;

    public OrderMenu() {
        orderLineController = new OrderLineController();
        orderController = new OrderController();
        customerController = new CustomerController();
        serviceController =  new ServiceController();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Create order");
        menuOptions.add("Show order");
        menuOptions.add("Update order");
        menuOptions.add("Delete order");

        serviceController = new ServiceController();
        customerController = new CustomerController();
        orderController = new OrderController();
    }

    private void createOrder() {
        HashMap<Service, Integer> servicesAndQuantity = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate order:");

        System.out.println("Enter email of customer: ");
        String customerEmail = scanner.next();

        System.out.println("Available services: ");
        ArrayList<Service> services = null;
        try {
            services = serviceController.getAllServicesFromDB();
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        while (true){
            for (Service service : services)
                System.out.println((services.indexOf(service)+1) + ". " + serviceController.getName(service));
            int chosenServiceNumber = inputValidation.getIntFromUser("Enter number of chosen service: ");
            int quantity = inputValidation.getIntFromUser("Enter quantity of service");
            servicesAndQuantity.put(services.get(chosenServiceNumber-1), quantity);

            System.out.println("Press '1' to add next service or any other key to end adding");
            if (!scanner.next().equals("1"))
                break;
        }


        System.out.println("Enter payday date(dd-mm-yyy): ");
        String paydayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale( Locale.UK );
        LocalDate payday = LocalDate.parse(paydayString, formatter);

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to cancel: ");
        System.out.println("Customer email: '" + customerEmail + "'");
        System.out.println("Added services and their quantity: ");
        for (Service service : servicesAndQuantity.keySet())
            System.out.println("\t" + serviceController.getName(service) + " x" + servicesAndQuantity.get(service));
        System.out.println("Payday: " + paydayString);

        if (scanner.next().toLowerCase().equals("c")) {
            try {
                int id = orderController.saveOrderWithUserInputInDB(customerEmail, servicesAndQuantity, payday);
                System.out.println("Order got id number: " + id);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Order declined");
    }

    private void showOrder() {
        System.out.println("\tOrder details:");
        int id = inputValidation.getIntFromUser("Enter id of order: ");
        Order<Customer> order = null;
        try {
            order = orderController.getOrderFromDB(id);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\n\tOrder information:");
        System.out.println("Customer email: " + customerController.getEmail(orderController.getCustomer(order)));
        for(OrderLine orderLine : orderController.getOrderLines(order)){
            System.out.println("Service name and quantity: "+ serviceController.getName(orderLineController.getService(orderLine)) + " x" + orderLineController.getQuantity(orderLine));
        }
        System.out.println("Order payday: " + orderController.getPayday(order));
    }

    private void updateOrder() {
        Scanner scanner =  new Scanner(System.in);
        System.out.println("\tUpdate order:");
        int id = inputValidation.getIntFromUser("Enter id of order: ");
        Order<Customer> order = null;
        try {
            order = orderController.getOrderFromDB(id);
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("\n\tCurrent order information:");
        System.out.println("Customer email: " + customerController.getEmail(orderController.getCustomer(order)));
        for(OrderLine orderLine : orderController.getOrderLines(order)){
            System.out.println("Service name and quantity: "+ serviceController.getName(orderLineController.getService(orderLine)) + " x" + orderLineController.getQuantity(orderLine));
        }
        System.out.println("Order payday: " + orderController.getPayday(order));

        System.out.println("Enter new information for order:");
        System.out.println("Enter email of a new customer: ");
        String customerEmail = scanner.next();

        System.out.println("Enter new payday date(dd-mm-yyyy): ");
        String paydayString = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter = formatter.withLocale( Locale.UK );
        LocalDate payday = LocalDate.parse(paydayString, formatter);

        ArrayList<Service> services = null;
        try {
            services = serviceController.getAllServicesFromDB();
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\tChoose services for order:");
        HashMap<Service, Integer> serviceQuantity = new HashMap<>();
        while (true){
            System.out.println("\tAll services available:");
            for (Service service : services)
                System.out.println((services.indexOf(service)+1) + ". " + serviceController.getName(service));
            int chosenServiceNumber = inputValidation.getIntFromUser("Enter number of chosen service: ");
            int quantity = inputValidation.getIntFromUser("Enter quantity of service");

            serviceQuantity.put(services.get(chosenServiceNumber - 1), quantity);
            System.out.println("Press '1' to add next service or any other key to end adding");
            if (!scanner.next().equals("1"))
                break;
        }

        System.out.println("\n\tEnter 'c' to confirm changing the order with id" + id + ": ");
        if (scanner.next().toLowerCase().equals("c")) {
            try {
                orderController.updateOrderWithUserInput(order, customerEmail, serviceQuantity, payday);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Changes declined");
    }

    private void deleteOrder() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("\tDelete order:");
        int id =  inputValidation.getIntFromUser("Enter id the order");

        System.out.println("\n\tEnter 'c' to confirm deleting the order with id" + id + ": ");
        if (scanner.next().toLowerCase().equals("c")) {
            try {
                orderController.deleteOrderFromDBByID(id);
            } catch (DBException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        else
            System.out.println("Order declined");
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
                    createOrder();
                    break;
                case 2:
                    showOrder();
                    break;
                case 3:
                    updateOrder();
                    break;
                case 4:
                    deleteOrder();
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
