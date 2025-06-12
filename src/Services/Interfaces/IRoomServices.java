package Services.Interfaces;

import Model.Guest;
import Model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomServices {

    void loadRoomsFromFile();
    void displayRoomsAvailable();
    void displayAvailableRoomsOnDate(List<Room> roomList, LocalDate targetDate, int rentalDays);
    void saveRoomToFile(List<Room> list);
    void generateMonthlyRevenueReport(List<Guest> listGuests, List<Room> listRooms);
    void generateRevenueByRoomType(List<Guest> listGuests, List<Room> listRooms);
}
