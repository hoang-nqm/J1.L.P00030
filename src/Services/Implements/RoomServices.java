package Services.Implements;

import Model.Room;
import Services.Interfaces.IRoomServices;
import Utils.FileUltils;
import Utils.Validations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomServices implements IRoomServices {
    List<Room> listRooms;
    public final String roomFilePath="src/Resources/Active_Room_List.txt";
public RoomServices() {
    listRooms= FileUltils.loadRoomsFromFile(roomFilePath);
}

    @Override
    public void loadRoomsFromFile() {
        FileUltils.importRoomData(roomFilePath);
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
}
