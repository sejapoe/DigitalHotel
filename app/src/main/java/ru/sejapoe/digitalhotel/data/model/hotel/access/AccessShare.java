package ru.sejapoe.digitalhotel.data.model.hotel.access;

import ru.sejapoe.digitalhotel.data.model.user.UserLess;

public class AccessShare {
    private final int id;

    private final short rightsValue;
    private final UserLess user;
    private final int budget;

    public AccessShare(int id, short rightsValue, UserLess user, int budget) {
        this.id = id;
        this.rightsValue = rightsValue;
        this.user = user;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public RightsComposition getRights() {
        return new RightsComposition(rightsValue);
    }

    public UserLess getUser() {
        return user;
    }

    public int getBudget() {
        return budget;
    }
}
