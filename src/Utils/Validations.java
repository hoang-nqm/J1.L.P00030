package Utils;

import Model.Guest;
import Model.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Validations {
    public static Scanner sc = new Scanner(System.in);
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String inputNationalID(List<Guest> guestList) {
        while (true) {
            System.out.print("Enter 12-digit National ID: ");
            String nationalID = sc.nextLine().trim();

            if (!nationalID.matches("\\d{12}")) {
                System.out.println(" Invalid format. Must be exactly 12 digits.");
                continue;
            }

            boolean exists = guestList.stream()
                    .anyMatch(g -> g.getNationalID().equals(nationalID));
            if (exists) {
                System.out.println(" National ID already exists in the system.");
                continue;
            }

            return nationalID;
        }
    }

    public static String inputNationalIDReserva() {
        while (true) {
            System.out.print("Enter National ID to cancel reservation: ");
            String nationalID = sc.nextLine().trim();

            if (!nationalID.matches("\\d{12}")) {
                System.out.println(" Invalid format. Must be exactly 12 digits.");
                continue;
            }
            return nationalID;
        }
    }
    public static Guest getGuestByNationalID(List<Guest> listGuests) {
        while (true) {
            System.out.print("Enter National ID: ");
            String nationalID = sc.nextLine().trim();

            if (!nationalID.matches("\\d{12}")) {
                System.out.println(" Invalid format. Must be exactly 12 digits.");
                continue;
            }
            Guest existingGuest = listGuests.stream()
                    .filter(g -> g.getNationalID().equals(nationalID))
                    .findFirst()
                    .orElse(null);

            if (existingGuest == null) {
                System.out.println("Guest with ID " + nationalID + " not found in system.");
            }
            return existingGuest;
        }
    }

    public static String inputFullName() {
        while (true) {
            System.out.print("Enter full name: ");
            String fullName = sc.nextLine().trim();


            if (fullName.isEmpty() || fullName.length() < 2 || fullName.length() > 25) {
                System.out.println("Name must be between 2-50 characters.");
                continue;
            }


            if (!fullName.matches("^[\\p{L}][\\p{L}\\s]*[\\p{L}]$|^[\\p{L}]$")) {
                System.out.println("Invalid name format. Only letters and spaces allowed.");
                continue;
            }

            fullName = fullName.replaceAll("\\s+", " ");
            return fullName;
        }
    }

    public static LocalDate inputBirthDate() {
        while (true) {
            System.out.print("Enter birthdate (dd-MM-yyyy): ");
            String input = sc.nextLine().trim();

            try {
                LocalDate birthDate = LocalDate.parse(input, formatter);
                LocalDate today = LocalDate.now();


                if (birthDate.isAfter(today)) {
                    System.out.println("Birth date cannot be in the future.");
                    continue;
                }


                if (birthDate.isAfter(today.minusYears(18))) {
                    System.out.println("Guest must be at least 18 years old.");
                    continue;
                }


                if (birthDate.isBefore(today.minusYears(120))) {
                    System.out.println("Please enter a valid birth date (not over 120 years old).");
                    continue;
                }

                return birthDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy (e.g., 15-01-1990).");
            }
        }
    }

    public static String inputGender() {
        String[] validGenders = {"Male", "Female", "Other"};

        while (true) {
            System.out.print("Enter gender (Male/Female/Other): ");
            String gender = sc.nextLine().trim();

            for (String validGender : validGenders) {
                if (gender.equalsIgnoreCase(validGender)) {
                    return validGender;
                }
            }
            System.out.println("Please enter 'Male', 'Female', or 'Other'.");
        }
    }

    public static String inputPhoneNumber() {
        while (true) {
            System.out.print("Enter phone number: ");
            String phone = sc.nextLine().trim();

            if (!phone.matches("^(03[2-9]|07[06789]|08[1-5]|05[68]|059)\\d{7}$")) {
                System.out.println("Invalid phone number. Must be 10 digits starting with 0 (e.g., 0123456789).");
                continue;
            }

            return phone;
        }
    }


    public static int inputRentalDays() {
        while (true) {
            System.out.print("Enter number of rental days (1-30): ");
            try {
                int rentalDays = Integer.parseInt(sc.nextLine().trim());

                if (rentalDays <= 0) {
                    System.out.println(" Rental days must be a positive number.");
                    continue;
                }

                if (rentalDays > 30) {
                    System.out.println(" Maximum rental period is 365 days.");
                    continue;
                }

                return rentalDays;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number.");
            }
        }
    }
    public static LocalDate inputStartDate() {
        while (true) {
            System.out.print("Enter start date (dd-MM-yyyy): ");
            String input = sc.nextLine().trim();
            try {
                LocalDate startDate = LocalDate.parse(input, formatter);
                LocalDate tomorrow = LocalDate.now();

                if (startDate.isBefore(tomorrow)) {
                    System.out.println("Start date must be at least tomorrow (" + tomorrow + ").");
                    continue;
                }

                return startDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }
    public static String inputCoTenant() {
        System.out.print("Enter co-tenant name (optional, press Enter to skip): ");
        String coTenant = sc.nextLine().trim();

        if (!coTenant.isEmpty()) {
            if (coTenant.length() > 50) {
                System.out.println("Co-tenant name is too long, truncating to 50 characters.");
                coTenant = coTenant.substring(0, 50);
            }

            if (!coTenant.matches("^[\\p{L}][\\p{L}\\s]*[\\p{L}]$|^[\\p{L}]$")) {
                System.out.println("Invalid co-tenant name format, clearing field.");
                coTenant = "";
            } else {
                coTenant = coTenant.replaceAll("\\s+", " ");
            }
        }

        return coTenant;
    }
    public static boolean isAvailable(LocalDate desiredCheckIn, int rentalDays, Room room) {
        LocalDate desiredCheckOut = desiredCheckIn.plusDays(rentalDays);

        for (Guest g : room.getGuestStays()) {
            LocalDate existingCheckIn = g.getCheckInDate();
            LocalDate existingCheckOut = g.getCheckOutDate();

            if (!(desiredCheckOut.isBefore(existingCheckIn) || desiredCheckIn.isAfter(existingCheckOut.minusDays(1)))) {
                return false;
            }
        }
        return true;
    }
    public static Room selectAvailableRoom(List<Room> listRooms,LocalDate startDate, int rentalDays) {
        Room selectedRoom = null;
        while (true) {
            System.out.print("Enter Room ID to book: ");
            String roomID = sc.nextLine().trim();

            selectedRoom = listRooms.stream()
                    .filter(r -> r.getRoomID().equalsIgnoreCase(roomID))
                    .findFirst()
                    .orElse(null);

            if (selectedRoom == null) {
                System.out.println("Room ID not found. Please choose from the list above.");
                continue;
            }

            if (!isAvailable(startDate, rentalDays,selectedRoom)) {
                System.out.println("⚠️ Room is not available in the selected period. Choose another.");
                continue;
            }

            break;
        }

        return selectedRoom;
    }
    public static List<Guest> getBookingByID(List<Guest> listGuests, String nationalID) {
        return listGuests.stream()
                .filter(g -> g.getNationalID().equals(nationalID)
                        && g.getCheckInDate() != null
                        && g.getCheckInDate().isAfter(LocalDate.now()))
                .toList();
    }

    public static Room getRoomByID(List<Room> roomList, String roomID) {
        return roomList.stream()
                .filter(r -> r.getRoomID().equalsIgnoreCase(roomID))
                .findFirst()
                .orElse(null);
    }
}
