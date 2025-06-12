package Services.Implements;

import Model.Guest;
import Model.Room;
import Services.Interfaces.IGuestServices;
import Services.Interfaces.IRoomServices;
import Utils.FileUltils;
import Utils.Validations;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GuestServices implements IGuestServices {
    public static Scanner sc = new Scanner(System.in);

    List<Guest> listGuests;
    IRoomServices roomServices;

    List<Room> listRooms;
    public final String roomFilePath="src/Resources/roomList.dat";
    public final String guestFilePath="src/Resources/guestInfo.dat";

    public GuestServices() {
        listGuests=  FileUltils.loadGuestListFromFile(guestFilePath);
        roomServices = new RoomServices();
        listRooms= FileUltils.loadRoomList(roomFilePath);
    }
    private void ensureRoomsLoaded() {
        if (listRooms == null || listRooms.isEmpty()) {
            listRooms = FileUltils.loadRoomList(roomFilePath);
        }
    }


    @Override
    public void enterGuestInformation() {
        ensureRoomsLoaded();
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
        roomServices.saveRoomToFile(listRooms);
    }

    @Override
    public void cancelReservationByID() {

        String nationalID = Validations.inputNationalIDReserva();
        List<Guest> bookings = Validations.getBookingByID(listGuests,nationalID);

        if (bookings == null || bookings.isEmpty()) {
            System.out.println(" Khách hàng chưa đặt phòng nào hoặc không có booking hợp lệ.");
            return;
        }
        System.out.println("\n Upcoming bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            Guest g = bookings.get(i);
            System.out.printf("[%d] Room: %s | Check-in: %s | Days: %d\n",
                    i + 1, g.getRoomID(), g.getCheckInDate(), g.getRentalDays());
        }
        int choice = -1;
        while (choice < 1 || choice > bookings.size()) {
            System.out.print("Select booking to cancel [1-" + bookings.size() + "]: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
        }

        Guest targetGuest = bookings.get(choice - 1);
        System.out.println("------------------------------------------------------------");
        System.out.printf("Guest information [National ID: %s]\n", targetGuest.getNationalID());
        System.out.println("------------------------------------------------------------");
        System.out.printf("Full name   : %s\n", targetGuest.getFullName());
        System.out.printf("Phone number: %s\n", targetGuest.getPhoneNumber());
        System.out.printf("Birth day   : %s\n", targetGuest.getBirthDate());
        System.out.printf("Gender      : %s\n", targetGuest.getGender());
        System.out.println("------------------------------------------------------------");
        System.out.printf("Rental room : %s\n", targetGuest.getRoomID());
        System.out.printf("Check in    : %s\n", targetGuest.getCheckInDate());
        System.out.printf("Rental days : %d\n", targetGuest.getRentalDays());
        System.out.printf("Check out   : %s\n", targetGuest.getCheckOutDate());
        System.out.println("------------------------------------------------------------");
        Room selectedRoom = Validations.getRoomByID(listRooms, targetGuest.getRoomID());
        System.out.println("Room information:");
        System.out.printf("+ ID       : %s\n", selectedRoom.getRoomID());
        System.out.printf("+ Room     : %s\n", selectedRoom.getRoomName());
        System.out.printf("+ Type     : %s\n", selectedRoom.getRoomType());
        System.out.println();
        System.out.printf("+ Daily rate: %.0f$\n", selectedRoom.getDailyRate());
        System.out.printf("+ Capacity  : %d\n", selectedRoom.getCapacity());
        System.out.printf("+ Funiture  : %s\n", selectedRoom.getFurnitureDescription());
        System.out.println("------------------------------------------------------------");
        System.out.print("Do you really want to cancel this guest's room booking? [Y/N]: ");
        String confirm = sc.nextLine().trim();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Cancellation aborted.");
            return;
        }
        for (Room room : listRooms) {
            if (room.getRoomID().equalsIgnoreCase(targetGuest.getRoomID())) {
                room.getGuestStays().removeIf(g -> g.getNationalID().equals(nationalID));
                targetGuest.setRoomID(null);
                targetGuest.setCheckInDate(null);
                targetGuest.setCheckOutDate(null);
                System.out.println("✅ The booking associated with ID " + targetGuest.getNationalID() + " has been successfully canceled.");
                return;
            }
        }
    }

    @Override
    public void displayListGuests() {
        if (listGuests.isEmpty()) {
            System.out.println("🔍 No guests found in the system.");
            return;
        }

        System.out.println("============================================================");
        System.out.println("                    GUEST LIST REPORT                      ");
        System.out.println("============================================================");
        System.out.printf("Total guests: %d\n\n", listGuests.size());

        int index = 1;
        for (Guest guest : listGuests) {
            System.out.println("------------------------------------------------------------");
            System.out.printf("[%d] Guest Information [National ID: %s]\n", index++, guest.getNationalID());
            System.out.println("------------------------------------------------------------");
            System.out.printf("Full name   : %s\n", guest.getFullName());
            System.out.printf("Phone number: %s\n", guest.getPhoneNumber());
            System.out.printf("Birth date  : %s\n", guest.getBirthDate());
            System.out.printf("Gender      : %s\n", guest.getGender());
            System.out.printf("Co-tenant   : %s\n", guest.getCoTenAntName() != null ? guest.getCoTenAntName() : "None");
            System.out.println("------------------------------------------------------------");
            String status = (guest.getCheckInDate() == null || guest.getCheckOutDate() == null)
                    ? "CANCELLED" : "ACTIVE";
            System.out.printf("Status      : %s\n", status);

            if (!status.equals("CANCELLED")) {
                System.out.printf("Room ID     : %s\n", guest.getRoomID());
                System.out.printf("Check-in    : %s\n", guest.getCheckInDate());
                System.out.printf("Check-out   : %s\n", guest.getCheckOutDate());
                System.out.printf("Rental days : %d days\n", guest.getRentalDays());
            } else {
                System.out.println("Room ID     : (booking canceled)");
            }

        }
        System.out.println("============================================================");
    }

    @Override
    public void saveGuestToFile() {
        FileUltils.saveGuestList(listGuests,guestFilePath);
    }

    @Override
    public void bookRoomForExistingGuest() {
        Guest guest = Validations.getGuestByNationalID(listGuests);
        System.out.println("Guest found: " + guest.getFullName() + " | Phone: " + guest.getPhoneNumber());


        LocalDate startDate = Validations.inputStartDate();
        int rentalDays = Validations.inputRentalDays();

        // Hiển thị các phòng trống
        roomServices.displayAvailableRoomsOnDate(listRooms, startDate, rentalDays);
        Room selectedRoom = Validations.selectAvailableRoom(listRooms,startDate,rentalDays);
        if (selectedRoom == null) {
            System.out.println("Booking cancelled.");
            return;
        }
        String coTenant = Validations.inputCoTenant();
        Guest newBooking = new Guest(
                guest.getBirthDate(),
                guest.getFullName(),
                guest.getGender(),
                guest.getNationalID(),
                guest.getPhoneNumber(),
                startDate,
                coTenant,
                rentalDays,
                selectedRoom.getRoomID()
        );
        listGuests.add(newBooking);
        selectedRoom.getGuestStays().add(newBooking);

        System.out.println("✅ New booking created for existing guest.");
        System.out.println("📅 Room: " + selectedRoom.getRoomID() + " | Check-in: " + newBooking.getCheckInDate());
        roomServices.saveRoomToFile(listRooms);
    }

    @Override
    public void monthlyRevenueReport() {
        roomServices.generateMonthlyRevenueReport(listGuests,listRooms);
    }

    @Override
    public void revenueByRoomType() {
        roomServices.generateRevenueByRoomType(listGuests,listRooms);
    }


}
