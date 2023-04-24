package ru.sejapoe.digitalhotel.data.model;

import java.time.LocalDate;

public class UserInfo {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String parentheses;
    private final String phoneNumber;
    private final LocalDate birthDate;

    private final Sex sex;

    public UserInfo(int id, String firstName, String lastName, String parentheses, String phoneNumber, LocalDate birthDate, Sex sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.parentheses = parentheses;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getParentheses() {
        return parentheses;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public enum Sex {
        MALE, FEMALE
    }
}
