package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Guest extends Person implements Serializable {

    private String roomID;
    private int rentalDays;
    private LocalDate startDate;
    private String coTenAntName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Guest(LocalDate birthDate, String fullName, String gender, String nationalID, String phoneNumber, LocalDate checkInDate, String coTenAntName, int rentalDays, String roomID) {
        super(birthDate, fullName, gender, nationalID, phoneNumber);
        this.roomID = roomID;
        this.rentalDays = rentalDays;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkInDate.plusDays(rentalDays);
        this.coTenAntName = coTenAntName;

    }

    public String getCoTenAntName() {
        return coTenAntName;
    }

    public void setCoTenAntName(String coTenAntName) {
        this.coTenAntName = coTenAntName;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
