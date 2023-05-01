package ru.sejapoe.digitalhotel.data.model.user;

public class UserLess {
    private final int id;
    private final String username;

    private final String fullName;

    public UserLess(int id, String username, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
