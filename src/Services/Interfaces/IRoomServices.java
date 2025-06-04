package Services.Interfaces;

import Model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomServices {

    void loadRoomsFromFile();
    void displayRoomsAvailable();
    void displayAvailableRoomsOnDate(List<Room> roomList, LocalDate targetDate, int rentalDays);
}
