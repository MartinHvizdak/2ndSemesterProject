package ui;
import java.util.ArrayList;

public class MainMenu {
    private OrderMenu orderMenu;
    private ArrayList<String> menuOptions;
    private InputValidation inputValidation;

    public MainMenu(){
        orderMenu = new OrderMenu();
        inputValidation = new InputValidation();
        menuOptions = new ArrayList<String>();
        menuOptions.add("Order menu");
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
