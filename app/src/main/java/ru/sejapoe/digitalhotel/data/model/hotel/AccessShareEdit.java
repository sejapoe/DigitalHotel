package ru.sejapoe.digitalhotel.data.model.hotel;

import javax.annotation.Nullable;

public class AccessShareEdit {
    private final short rights;
    @Nullable
    private final Integer budget;

    public AccessShareEdit(short rights, @Nullable Integer budget) {
        this.rights = rights;
        this.budget = budget;
    }

    public short getRights() {
        return rights;
    }

    @Nullable
    public Integer getBudget() {
        return budget;
    }
}
