package ru.sejapoe.digitalhotel.data.model.hotel.access;

import javax.annotation.Nullable;

public class AccessShareEdit {
    private final int rights;
    @Nullable
    private final Integer budget;

    public AccessShareEdit(int rights, @Nullable Integer budget) {
        this.rights = rights;
        this.budget = budget;
    }

    public int getRights() {
        return rights;
    }

    @Nullable
    public Integer getBudget() {
        return budget;
    }
}
