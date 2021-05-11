package ui;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import controller.CustomerController;
import controller.OrderController;
import controller.OrderLineController;
import controller.ServiceController;
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
        menuOptions.add("Update customer");
        menuOptions.add("Update payday");
        menuOptions.add("Add service with quantity");
        menuOptions.add("Remove service with quantity");
        menuOptions.add("Delete order");

        serviceController = new ServiceController();
        customerController = new CustomerController();
        orderController = new OrderController();
    }

    private void createOrder() {
        ArrayList<Integer> orderLines = new ArrayList<Integer>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\tCreate order:");

        System.out.println("Enter email of customer: ");
        String customerEmail = scanner.next();
        Customer customer = customerController.getCustomer(customerEmail);
        Order order = orderController.createOrder(customer);

        ArrayList<Service> services = new ArrayList<Service>();
        services = serviceController.getAllServicesFromDB();
        HashMap<String, Integer> enteredServices =  new HashMap<>();
        while (true){
            for (Service service : services)
                System.out.println((services.indexOf(service)+1) + ". " + serviceController.getName(service));
            int chosenServiceNumber = inputValidation.getIntFromUser("Enter number of chosen service: ");
            int quantity = inputValidation.getIntFromUser("Enter quantity of service");
            enteredServices.put(serviceController.getName(services.get(chosenServiceNumber-1)), quantity);
            Service service = services.get(chosenServiceNumber - 1);
            OrderLine orderLine = orderLineController.createOrderLine(service, quantity);
            orderController.addOrderLine(order, orderLine);

            System.out.println("Press '1' to add next service or any other key to end adding");
            if (scanner.nextInt() != 1)
                break;
        }


        System.out.println("Enter payday date(dd-mm-yyy): ");
        String paydayString = scanner.next();
        LocalDate payday = new LocalDate(2000,2,1);
        orderController.setPayday(order,payday);

        System.out.println("\n\tEnter 'c' to confirm  and save an order or any other key to decline with such data: ");
        System.out.println("Customer email: '" + customerEmail + "'");
        System.out.println("Added services and their quantity: ");
        for (String service : enteredServices.keySet())
            System.out.println("\t" + service + " : " + enteredServices.get(service));
        System.out.println("Payday: " + paydayString);

        if (!scanner.next().toLowerCase().equals("c"))
            orderController.saveOrderInDB(order);
        else
            System.out.println("Order declined");
    }

    private void showOrder() {
        HashMap<String, ArrayList<String> > orderInformation = new HashMap<>();
        Scanner scanner =  new Scanner(System.in);
        System.out.println("\tOrder details:");
        int id = inputValidation.getIntFromUser("Enter id of order: ");
        Order order = orderController.getOrder(id);
        System.out.println("\n\tOrder information:");
        System.out.println("Customer email: " + customerController.getEmail(orderController.getCustomer(order)));
        for(OrderLine orderLine : orderController.getOrderLines(order)){
            System.out.println("Service name and quantity: "+ serviceController.getName(orderLineController.getService(orderLine)) + " x" + orderLineController.getQuantity(orderLine));
        }
        System.out.println("Order payday: " + orderController.getPayday(order));
    }

    private void updateCustomer(){
        Scanner  scanner =  new Scanner(System.in);
        System.out.println("\tUpdate order customer:");
        int id = inputValidation.getIntFromUser("Enter id of an order: ");
        Order order = orderController.getOrder(id);
        System.out.println("Enter email of a new customer: ");
        String customerEmail = scanner.next();
        Customer newCustomer = customerController.getCustomer(customerEmail);


        System.out.println("\n\tEnter 'c' to confirm changing customer in order with id" + id + ": ");
        if (!scanner.next().toLowerCase().equals("c"))
            orderController.setCustomer(order, newCustomer);
        else
            System.out.println("Service was not added");
    }

    private void updatePayday(){
        Scanner  scanner =  new Scanner(System.in);
        System.out.println("\tUpdate order payday:");
        int id = inputValidation.getIntFromUser("Enter id of an order: ");
        Order order = orderController.getOrder(id);
        System.out.println("Enter new payday date(dd/mm/yyyy): ");
        LocalDate payday = null;


        System.out.println("\n\tEnter 'c' to confirm changing payday in order with id" + id + ": ");
        if (!scanner.next().toLowerCase().equals("c"))
            orderController.setPayday(order, payday);
        else
            System.out.println("Service was not added");

    }

    private void addOrderLine(String serViceName){
        Scanner scanner =  new Scanner(System.in);
        System.out.println("\tAdd service and quantity to the order:");
        int id = inputValidation.getIntFromUser("Enter id of an order: ");
        Order order = orderController.getOrder(id);
        System.out.println("\tAll services available:");
        ArrayList<Service> services = serviceController.getAllServicesFromDB();
        for (Service service : services)
            System.out.println((services.indexOf(service)+1) + ". " + serviceController.getName(service));
        int chosenServiceNumber = inputValidation.getIntFromUser("Enter number of chosen service: ");
        Service service = services.get(chosenServiceNumber - 1);
        int quantity = inputValidation.getIntFromUser("Enter quantity of service");
        OrderLine orderLine =  orderLineController.createOrderLine(service, quantity);


        System.out.println("\n\tEnter 'c' to confirm adding service nr." + chosenServiceNumber + " from order with id" + id + ": ");
        if (!scanner.next().toLowerCase().equals("c"))
            orderController.addOrderLine(order, orderLine);
        else
            System.out.println("Service was not added");
    }

    private void removeOrderLine(){
        Scanner scanner =  new Scanner(System.in);
        System.out.println("\tRemove service with quantity from the order:");
        int id = inputValidation.getIntFromUser("Enter id of an order: ");
        Order order = orderController.getOrder(id);
        System.out.println("\tAll services in the order:");
        ArrayList<OrderLine> orderLines = orderController.getOrderLines(order);
        for (OrderLine orderLine : orderLines)
            System.out.println((orderLines.indexOf(orderLine)+1) + ". " + serviceController.getName(orderLineController.getService(orderLine)) + " x" + orderLineController.getQuantity(orderLine));
        int chosenOrderLineNumber = inputValidation.getIntFromUser("Enter number of service which you want to remove: ");
        OrderLine orderLine = orderLines.get(chosenOrderLineNumber - 1);

        System.out.println("\n\tEnter 'c' to confirm removing service nr." + chosenOrderLineNumber + " from order with id" + id + ": ");
        if (!scanner.next().toLowerCase().equals("c"))
            orderController.removeOrderLine(order, orderLine);
        else
            System.out.println("Service was not deleted");

    }

    private void deleteOrder() {
        Scanner scanner =  new Scanner(System.in);

        System.out.println("\tDelete order:");
        int id =  inputValidation.getIntFromUser("Enter id the order");

        System.out.println("\n\tEnter 'c' to confirm deleting the order with id" + id + ": ");
        if (!scanner.next().toLowerCase().equals("c"))
            orderController.deleteOrderFromDB(id);
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
                    updateCustomer();
                    break;
                case 4:
                    updatePayday();
                    break;
                case 5:
                    addOrderLine();
                    break;
                case 6:
                    removeOrderLine();
                    break;
                case 7:
                    deleteOrder();
                    break;
                case 8:
                    deleteOrder();
                    break;
                case 11:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong Choice!");
                    break;
            }
        }
    }
}
