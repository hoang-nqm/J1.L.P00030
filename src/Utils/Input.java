package Utils;

import java.util.Scanner;

public class Input {
    public static Scanner scanner = new Scanner(System.in);
    public int inputMenuChoice(){
        int choice;
        System.out.println("Please enter your choice: ");
        choice = Integer.parseInt(scanner.nextLine());
        return choice;
    }

    public static String inputString(){
        return scanner.nextLine();
    }
}
