package ru.sejapoe.digitalhotel.data.model.user;

import java.util.Collections;
import java.util.List;

public class FriendRequests {
    private final List<UserLess> income;
    private final List<UserLess> outcome;

    public FriendRequests(List<UserLess> income, List<UserLess> outcome) {
        this.income = income;
        this.outcome = outcome;
    }

    public FriendRequests() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public List<UserLess> getIncome() {
        return income;
    }

    public List<UserLess> getOutcome() {
        return outcome;
    }
}
