package Services.Implements;

import Model.Guest;
import Model.Room;
import Services.Interfaces.IGuestServices;
import Services.Interfaces.IRoomServices;
import Utils.FileUltils;
import Utils.Validations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestServices implements IGuestServices {

    List<Guest> listGuests;
    IRoomServices roomServices;

    List<Room> listRooms;
    public final String roomFilePath="src/Resources/Active_Room_List.txt";

    public GuestServices() {
        listGuests=  new ArrayList<>();
        roomServices = new RoomServices();
        listRooms= FileUltils.loadRoomsFromFile(roomFilePath);
    }



    @Override
    public void enterGuestInformation() {
        String nationalID = Validations.inputNationalID(listGuests);

        String fullName =Validations.inputFullName();

        LocalDate birthDate = Validations.inputBirthDate();

        String gender = Validations.inputGender();

        String phone = Validations.inputPhoneNumber();

        LocalDate startDate = Validations.inputStartDate();

        int rentalDays = Validations.inputRentalDays();

        roomServices.displayAvailableRoomsOnDate(listRooms,startDate,rentalDays);
        Room selectedRoom = Validations.selectAvailableRoom(listRooms,startDate,rentalDays);

        String coTenant = Validations.inputCoTenant();

        Guest guest = new Guest(birthDate, fullName, gender, nationalID, phone,
                startDate,coTenant, rentalDays, selectedRoom.getRoomID());
        listGuests.add(guest);
        selectedRoom.getGuestStays().add(guest);
        System.out.println("✅ Guest registered successfully for room " + selectedRoom.getRoomID());
        System.out.println("📅 Check-in  : " + guest.getCheckInDate());
        System.out.println("📅 Check-out : " + guest.getCheckOutDate());

    }
}
