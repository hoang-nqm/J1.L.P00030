package UI;


import Services.Implements.RoomServices;
import Services.Interfaces.IRoomServices;
import Utils.Input;

public class MainMenu {

IRoomServices roomServices;

    public MainMenu() {
roomServices = new RoomServices();
    }

    public void mainMenu() {
        int choice;
        do {
            System.out.println("Welcome to the main menu");
            System.out.println("Please select one of the following options:");
            System.out.println("1. Register Customer");
            System.out.println("2. Update Customer Information");
            System.out.println("3. Search Customer");
            System.out.println("4. Display Feats Menu");
            System.out.println("5. Place Order");
            System.out.println("6. Update order information");
            System.out.println("7. Save data to file");
            System.out.println("8. Display Customer or Orders Lists");
            System.out.println("Orther. Exit");

            Input input = new Input();
            choice = input.inputMenuChoice();


            switch (choice) {
                case 1:
                   roomServices.loadRoomsFromFile();
                    break;
                case 2:
                   roomServices.displayRoomsAvailable();
                    break;
                case 3:
                    System.out.println("Enter Customer ID");
                    break;
                case 4:
                    System.out.println("Enter Customer ID");
                    break;
                case 5:
                    System.out.println("Enter Customer ID");
                    break;
                case 6:
                    System.out.println("Update order information");
                    break;
                case 7:
                    System.out.println("Enter Customer ID");
                    break;
                case 8:
                    System.out.println("Enter Customer ID");
                    break;
                default:
                    System.out.println("Goodbye!!!");
            }
        } while (choice >= 1 && choice <= 8);

    }
}
