package Services.Implements;

import Model.Guest;
import Model.Room;
import Services.Interfaces.IRoomServices;
import Utils.FileUltils;
import Utils.Validations;

import java.time.LocalDate;
import java.util.*;

public class RoomServices implements IRoomServices {
    List<Room> listRooms;
    public static Scanner sc = new Scanner(System.in);

    public final String roomFilePath="src/Resources/Active_Room_List.txt";
    public final String roomFilePathDAT="src/Resources/roomList.dat";

public RoomServices() {
    listRooms= FileUltils.loadRoomsFromFile(roomFilePath);
}

    @Override
    public void loadRoomsFromFile() {
        FileUltils.importRoomData(roomFilePath);
        saveRoomToFile(listRooms);
    }

    @Override
    public void displayRoomsAvailable() {
        if (listRooms == null || listRooms.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet.");
            return;
        }

        System.out.printf("%-6s | %-15s | %-8s | %-6s | %-8s | %-30s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("----------------------------------------------------------------------");

        for (Room room : listRooms) {
            System.out.printf("%-6s | %-15s | %-8s | %-6.2f | %-8d | %-30s\n",
                    room.getRoomID(), room.getRoomName(), room.getRoomType(),
                    room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
        }
    }

    @Override
    public void displayAvailableRoomsOnDate(List<Room> roomList, LocalDate targetDate, int rentalDays) {
        boolean hasAvailable = false;
        LocalDate targetCheckout = targetDate.plusDays(rentalDays);

        System.out.println("Available Rooms from " + targetDate + " to " + targetCheckout.minusDays(1) + ":");
        System.out.printf("%-6s | %-15s | %-8s | %-6s | %-8s | %s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("-----------------------------------------------------------------------");

        for (Room room : roomList) {
            if (Validations.isAvailable(targetDate,rentalDays,room)) {
                System.out.printf("%-6s | %-15s | %-8s | %-6.2f | %-8d | %s\n",
                        room.getRoomID(), room.getRoomName(), room.getRoomType(),
                        room.getDailyRate(), room.getCapacity(), room.getFurnitureDescription());
                hasAvailable = true;
            }
        }

        if (!hasAvailable) {
            System.out.println("No rooms are available in that period.");
        }
    }

    @Override
    public void saveRoomToFile(List<Room> list) {
        FileUltils.saveRoomList(list,roomFilePathDAT);
    }

    @Override
    public void generateMonthlyRevenueReport(List<Guest> listGuests, List<Room> listRooms) {


        System.out.print("Enter month (1-12): ");
        int month = Integer.parseInt(sc.nextLine());

        System.out.print("Enter year: ");
        int year = Integer.parseInt(sc.nextLine());

        double totalRevenue = 0;
        int count = 0;

        System.out.println("\n📊 Monthly Revenue Report (COMPLETED BOOKINGS) for " + String.format("%02d/%d", month, year));
        System.out.println("------------------------------------------------------------");

        for (Guest guest : listGuests) {
            LocalDate checkIn = guest.getCheckInDate();
            LocalDate checkOut = guest.getCheckOutDate();

            // ⚠️ Bỏ qua nếu thiếu dữ liệu (huỷ hoặc chưa đặt)
            if (checkIn == null || checkOut == null) continue;

            // ✅ Chỉ tính booking đã kết thúc (check-out trong quá khứ hoặc hôm nay)
            if (checkOut.isAfter(LocalDate.now())) continue;

            // ✅ Chỉ lấy các booking đã hoàn tất trong tháng yêu cầu
            if (checkOut.getMonthValue() == month && checkOut.getYear() == year) {
                Room room = listRooms.stream()
                        .filter(r -> r.getRoomID().equalsIgnoreCase(guest.getRoomID()))
                        .findFirst()
                        .orElse(null);

                if (room != null) {
                    double amount = room.getDailyRate() * guest.getRentalDays();
                    totalRevenue += amount;
                    count++;

                    System.out.printf("Guest: %-20s | Room: %-6s | %s → %s | %d days | $%.2f\n",
                            guest.getFullName(), guest.getRoomID(),
                            guest.getCheckInDate(), guest.getCheckOutDate(),
                            guest.getRentalDays(), amount);
                }
            }
        }

        if (count == 0) {
            System.out.println("⚠️ No completed bookings found for this month.");
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("✅ Completed bookings: %d\n", count);
        System.out.printf("💰 Total revenue      : $%.2f\n", totalRevenue);
        System.out.println("------------------------------------------------------------");
    }

    @Override
    public void generateRevenueByRoomType(List<Guest> listGuests, List<Room> listRooms) {

        // Bước 1: Nhập roomType hoặc để trống
        System.out.print("Enter room type to report (or press Enter to show ALL): ");
        String roomTypeInput = sc.nextLine().trim();

        Map<String, Double> revenueMap = new HashMap<>(); // Lưu tổng doanh thu theo từng loại phòng
        Map<String, Integer> bookingCountMap = new HashMap<>();

        for (Guest guest : listGuests) {
            LocalDate checkOut = guest.getCheckOutDate();
            if (checkOut == null || checkOut.isAfter(LocalDate.now())) continue;

            Room room = listRooms.stream()
                    .filter(r -> r.getRoomID().equalsIgnoreCase(guest.getRoomID()))
                    .findFirst()
                    .orElse(null);

            if (room == null) continue;

            String roomType = room.getRoomType();

            // Nếu người dùng có nhập roomType thì chỉ lấy loại phù hợp
            if (!roomTypeInput.isEmpty() && !roomType.equalsIgnoreCase(roomTypeInput)) {
                continue;
            }

            double amount = room.getDailyRate() * guest.getRentalDays();

            // Cộng vào map
            revenueMap.put(roomType, revenueMap.getOrDefault(roomType, 0.0) + amount);
            bookingCountMap.put(roomType, bookingCountMap.getOrDefault(roomType, 0) + 1);
        }

        // Bước 2: Hiển thị kết quả
        if (revenueMap.isEmpty()) {
            System.out.println("⚠️ No completed bookings found for the selected room type.");
            return;
        }

        System.out.println("\nRevenue Report by Room Type");
        System.out.println("----------------------------------------");
        System.out.printf("%-15s | %-10s | %-12s\n", "Room Type", "Bookings", "Revenue");
        System.out.println("----------------------------------------");

        for (String type : revenueMap.keySet()) {
            System.out.printf("%-15s | %-10d | $%,.2f\n",
                    type,
                    bookingCountMap.get(type),
                    revenueMap.get(type));
        }

        System.out.println("----------------------------------------");
    }


}
