package ru.sejapoe.digitalhotel.data.model.user;

public class User {
    private final int id;
    private final String username;

    private final UserInfo userInfo;

    public User(int id, String username, UserInfo userInfo) {
        this.id = id;
        this.username = username;
        this.userInfo = userInfo;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
