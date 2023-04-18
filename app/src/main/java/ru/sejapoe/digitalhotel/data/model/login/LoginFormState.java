package ru.sejapoe.digitalhotel.data.model.login;

import androidx.annotation.StringRes;

public final class LoginFormState {
    @StringRes
    private final int usernameError;

    @StringRes
    private final int passwordError;
    @StringRes
    private final int repeatedPasswordError;

    public LoginFormState() {
        this(android.R.string.ok, android.R.string.ok, android.R.string.ok);
    }

    public LoginFormState(@StringRes int usernameError, @StringRes int passwordError, @StringRes int repeatedPasswordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.repeatedPasswordError = repeatedPasswordError;
    }

    public boolean isOk() {
        return !(hasUsernameError() || hasPasswordError() || hasRepeatedPasswordError());
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

    public boolean hasRepeatedPasswordError() {
        return repeatedPasswordError != android.R.string.ok;
    }

    public int getRepeatedPasswordError() {
        return repeatedPasswordError;
    }

    public enum AuthState {
        LOGIN, REGISTER, WAITING, WRONG_PASSWORD, INTERNAL_ERROR, FINE
    }
}
