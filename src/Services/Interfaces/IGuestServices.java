package Services.Interfaces;

import Model.Guest;
import Model.Room;

import java.util.List;

public interface IGuestServices {
    void enterGuestInformation();
    void cancelReservationByID();
    void displayListGuests();
    void saveGuestToFile();
    void bookRoomForExistingGuest();
    void monthlyRevenueReport();
    void revenueByRoomType();
}
