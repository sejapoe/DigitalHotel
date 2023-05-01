package ru.sejapoe.digitalhotel.data.model.hotel;

import ru.sejapoe.digitalhotel.data.model.user.User;

public class Payment {
    private final int id;
    private final User user;
    private final int amount;
    private final long timestamp;

    public Payment(int id, User user, int amount, long timestamp) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
