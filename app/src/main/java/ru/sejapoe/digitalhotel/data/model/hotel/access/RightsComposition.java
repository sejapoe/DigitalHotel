package ru.sejapoe.digitalhotel.data.model.hotel.access;

import androidx.annotation.NonNull;

public class RightsComposition {
    private final int value;

    public RightsComposition(int value) {
        this.value = value;
    }

    public boolean satisfy(@NonNull Rights right) {
        return (value & right.getValue()) == 0;
    }
}
