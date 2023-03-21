package ru.sejapoe.digitalhotel.data.model;

import androidx.annotation.StringRes;

public final class LoginFormState {
    @StringRes
    private final int usernameError;

    @StringRes
    private final int passwordError;

    public LoginFormState(@StringRes int usernameError, @StringRes int passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
    }

    public LoginFormState() {
        this(android.R.string.ok, android.R.string.ok);
    }

    public boolean isOk() {
        return !hasUsernameError() && !hasPasswordError();
    }

    public boolean hasUsernameError() {
        return usernameError != android.R.string.ok;
    }

    public int getUsernameError() {
        return usernameError;
    }

    public boolean hasPasswordError() {
        return passwordError != android.R.string.ok;
    }

    public int getPasswordError() {
        return passwordError;
    }

    public enum AuthState {
        NOTHING, WAITING, WRONG_PASSWORD, INTERNAL_ERROR, FINE
    }
}
