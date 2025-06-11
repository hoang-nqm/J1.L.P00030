package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private String roomID;
    private String roomName;
    private String roomType;
    private double dailyRate;
    private int capacity;
    private String furnitureDescription;
    private List<Guest> guestStays = new ArrayList<>();

    public Room(int capacity, double dailyRate, String furnitureDescription, List<Guest> guestStays, String roomID, String roomName, String roomType) {
        this.capacity = capacity;
        this.dailyRate = dailyRate;
        this.furnitureDescription = furnitureDescription;
        this.guestStays = guestStays;
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public Room(String roomID, String roomName, String roomType, double dailyRate, int capacity, String furnitureDescription) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomType = roomType;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.furnitureDescription = furnitureDescription;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    public List<Guest> getGuestStays() {
        return guestStays;
    }

    public void setGuestStays(List<Guest> guestStays) {
        this.guestStays = guestStays;
    }
}
