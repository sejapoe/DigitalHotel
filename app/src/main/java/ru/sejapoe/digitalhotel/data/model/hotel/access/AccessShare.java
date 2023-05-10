package ru.sejapoe.digitalhotel.data.model.hotel.access;

import java.util.Objects;

import ru.sejapoe.digitalhotel.data.model.user.UserLess;

public class AccessShare implements Comparable<AccessShare> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessShare that = (AccessShare) o;
        return id == that.id && rightsValue == that.rightsValue && budget == that.budget && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rightsValue, user, budget);
    }

    @Override
    public int compareTo(AccessShare o) {
        return Integer.compare(id, o.id);
    }
}
