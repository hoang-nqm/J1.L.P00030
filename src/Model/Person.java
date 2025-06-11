package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private String nationalID;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phoneNumber;


    public Person(LocalDate birthDate, String fullName, String gender, String nationalID, String phoneNumber) {
        this.birthDate = birthDate;
        this.fullName = fullName;
        this.gender = gender;
        this.nationalID = nationalID;
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
