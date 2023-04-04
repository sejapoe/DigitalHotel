package ru.sejapoe.digitalhotel.ui.main.booking.guestcount;

import android.view.View;

import androidx.core.math.MathUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuestCountViewModel extends ViewModel {
    public static final int MAX_ADULTS = 4;
    public static final int MIN_ADULTS = 1;

    public static final int MAX_CHILDREN = 4;
    public static final int MIN_CHILDREN = 0;
    private final MutableLiveData<Status> status = new MutableLiveData<>(new Status(1, 0));

    public LiveData<Status> getStatus() {
        return status;
    }


    public void increaseAdultsCount(View ignoredView) {
        Status value = status.getValue();
        if (value == null) return;
        status.postValue(new Status(value.adultsCount + 1, value.childrenCount));
    }

    public void decreaseAdultsCount(View ignoredView) {
        Status value = status.getValue();
        if (value == null) return;
        status.postValue(new Status(value.adultsCount - 1, value.childrenCount));
    }

    public void increaseChildrenCount(View ignoredView) {
        Status value = status.getValue();
        if (value == null) return;
        status.postValue(new Status(value.adultsCount, value.childrenCount + 1));
    }

    public void decreaseChildrenCount(View ignoredView) {
        Status value = status.getValue();
        if (value == null) return;
        status.postValue(new Status(value.adultsCount, value.childrenCount - 1));
    }

    public void setCounts(int adultCount, int childrenCount) {
        status.postValue(new Status(adultCount, childrenCount));
    }

    public static class Status {

        private final int adultsCount;
        private final int childrenCount;

        private final boolean canDecreaseChildren;
        private final boolean canIncreaseChildren;
        private final boolean canDecreaseAdults;

        private final boolean canIncreaseAdults;

        public Status(int adultsCount, int childrenCount) {
            this.adultsCount = MathUtils.clamp(adultsCount, MIN_ADULTS, MAX_ADULTS);
            this.childrenCount = MathUtils.clamp(childrenCount, MIN_CHILDREN, MAX_CHILDREN);
            this.canIncreaseAdults = adultsCount < MAX_ADULTS;
            this.canDecreaseAdults = adultsCount > MIN_ADULTS;
            this.canIncreaseChildren = childrenCount < MAX_CHILDREN;
            this.canDecreaseChildren = childrenCount > MIN_CHILDREN;
        }

        public int getAdultsCount() {
            return adultsCount;
        }

        public int getChildrenCount() {
            return childrenCount;
        }

        public boolean canDecreaseChildren() {
            return canDecreaseChildren;
        }

        public boolean canIncreaseChildren() {
            return canIncreaseChildren;
        }

        public boolean canDecreaseAdults() {
            return canDecreaseAdults;
        }

        public boolean canIncreaseAdults() {
            return canIncreaseAdults;
        }
    }
}
