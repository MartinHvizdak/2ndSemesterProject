package ui;

import java.util.Scanner;

public class InputValidation {

    public int getIntFromUser(String message){
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number!");
            scanner.next();
        }

        return scanner.nextInt();
    }

    public int getChoiceFromUser( int numberOfChoices) {
        int choice;
        do {
            System.out.print("\nChoose an option: ");
            choice = getIntFromUser("");
            if (choice > numberOfChoices || choice <= 0)
                System.out.println("Wrong choice!");
        }while(choice > numberOfChoices || choice <= 0);

        return choice;
    }

}
