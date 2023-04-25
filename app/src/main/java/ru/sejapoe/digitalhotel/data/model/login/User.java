package ru.sejapoe.digitalhotel.data.model.login;

import ru.sejapoe.digitalhotel.data.model.UserInfo;

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
