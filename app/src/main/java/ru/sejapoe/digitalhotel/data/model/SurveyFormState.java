package ru.sejapoe.digitalhotel.data.model;

import androidx.annotation.StringRes;

public class SurveyFormState {
    @StringRes
    private final int firstNameError;

    @StringRes
    private final int lastNameError;

    @StringRes
    private final int patronymicError;

    @StringRes
    private final int phoneNumberError;

    @StringRes
    private final int dateError;

    public SurveyFormState() {
        this(android.R.string.ok, android.R.string.ok, android.R.string.ok, android.R.string.ok, android.R.string.ok);
    }

    public SurveyFormState(@StringRes int firstNameError, @StringRes int lastNameError, @StringRes int patronymicError, @StringRes int phoneNumberError, @StringRes int dateError) {
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.patronymicError = patronymicError;
        this.phoneNumberError = phoneNumberError;
        this.dateError = dateError;
    }

    public boolean isOk() {
        return !(hasFirstNameError() || hasLastNameError() || hasPatronymicError() || hasPhoneNumberError() || hasDateError());
    }

    public boolean hasFirstNameError() {
        return firstNameError != android.R.string.ok;
    }

    public int getFirstNameError() {
        return firstNameError;
    }

    public boolean hasLastNameError() {
        return lastNameError != android.R.string.ok;
    }

    public int getLastNameError() {
        return lastNameError;
    }

    public boolean hasPatronymicError() {
        return patronymicError != android.R.string.ok;
    }

    public int getPatronymicError() {
        return patronymicError;
    }

    public boolean hasPhoneNumberError() {
        return phoneNumberError != android.R.string.ok;
    }

    public int getPhoneNumberError() {
        return phoneNumberError;
    }

    public boolean hasDateError() {
        return dateError != android.R.string.ok;
    }

    public int getDateError() {
        return dateError;
    }
}
