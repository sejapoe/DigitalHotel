package ru.sejapoe.digitalhotel.data.model.login;

import ru.sejapoe.digitalhotel.utils.BitArray256;

public class Session {
    private final int sessionId;
    private final BitArray256 sessionKey;

    public Session(int sessionId, BitArray256 sessionKey) {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
    }

    public int getSessionId() {
        return sessionId;
    }

    public BitArray256 getSessionKey() {
        return sessionKey;
    }

}
