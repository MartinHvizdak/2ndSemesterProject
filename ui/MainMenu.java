package ui;

import java.util.ArrayList;

public class MainMenu {
    private OrderMenu orderMenu;
    private ServiceMenu serviceMenu;
    private EmployeeMenu employeeMenu;
    private OwnerMenu ownerMenu;
    private ArrayList<String> menuOptions;
    private InputValidation inputValidation;

    public MainMenu(){
        orderMenu = new OrderMenu();
        serviceMenu =  new ServiceMenu();
        employeeMenu =  new EmployeeMenu();
        ownerMenu =  new OwnerMenu();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Order menu");
        menuOptions.add("Service menu");
        menuOptions.add("Employee menu");
        menuOptions.add("Owner menu");
        menuOptions.add("Quit");
    }

    private void displayMenu(){
        System.out.println("\n\t Main Menu:");
        for(String option : menuOptions)
            System.out.println((menuOptions.indexOf(option)+1) + ". " + option);
    }

    public void openMenu(){
        boolean quit = false;
        while(!quit){
            displayMenu();
            int choice = inputValidation.getIntFromUser("Choose an option: ");
            switch (choice) {
                case 1:
                    orderMenu.openMenu();
                    break;
                case 2:
                    serviceMenu.openMenu();
                    break;
                case 3:
                    employeeMenu.openMenu();
                    break;
                case 4:
                    ownerMenu.openMenu();
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
