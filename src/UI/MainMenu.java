package UI;


import Services.Implements.GuestServices;
import Services.Implements.RoomServices;
import Services.Interfaces.IGuestServices;
import Services.Interfaces.IRoomServices;
import Utils.Input;

public class MainMenu {

    IRoomServices roomServices;
    IGuestServices guestServices;
    public MainMenu() {
        roomServices = new RoomServices();
        guestServices = new GuestServices();
    }

    public void mainMenu() {
        int choice;
        do {
            System.out.println("Welcome to the main menu");
            System.out.println("Please select one of the following options:");
            System.out.println("1. Import Room Data from Text File");
            System.out.println("2. Display Available Room List");
            System.out.println("3. Enter Guest Information");
            System.out.println("4. Update Guest Stay Information");
            System.out.println("5. Search Guest by National ID");
            System.out.println("6. Delete Guest Reservation Before Arrival");
            System.out.println("7. List Vacant Rooms");
            System.out.println("8. Monthly Revenue Report");
            System.out.println("9. Revenue Report by Room Type");
            System.out.println("10. Save Guest Information");
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
                    guestServices.enterGuestInformation();
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
                case 9:
                    System.out.println("Enter Customer ID");
                    break;
                case 10:
                    System.out.println("Enter Customer ID");
                    break;
                default:
                    System.out.println("Goodbye!!!");
            }
        } while (choice >= 1 && choice <= 8);

    }
}
