package ru.sejapoe.digitalhotel.data.net;

//import com.google.gson.GsonBuilder;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.sejapoe.digitalhotel.data.auth.BitArray256;

public class HttpProvider {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final MediaType TEXT = MediaType.get("text/plain; charset=utf-8");
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Response postAuth(String url, Object data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnauthorizedException {
        String json = new GsonBuilder().create().toJson(data);
        Cipher aes = Cipher.getInstance("RC4");
        if (session == null) throw new UnauthorizedException();
        Key key = new SecretKeySpec(session.getSessionKey().asByteArray(), "RC4");
        System.out.println(Arrays.toString(key.getEncoded()));
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = aes.doFinal(json.getBytes(StandardCharsets.UTF_8));
        String s = Base64.getEncoder().encodeToString(cipherText);
        System.out.println(s);
        return post(url, session.getSessionId() + ":" + s);
    }

    public Response post(String url, String data) {
        return post(url, RequestBody.create(data, TEXT));
    }

    public Response post(String url, Object data) {
        String json = new GsonBuilder().create().toJson(data);
        return post(url, RequestBody.create(json, JSON));
    }

    public Response post(String url, RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Session {
        private final String sessionId;
        private final BitArray256 sessionKey;

        public Session(String sessionId, BitArray256 sessionKey) {
            this.sessionId = sessionId;
            this.sessionKey = sessionKey;
        }

        public String getSessionId() {
            return sessionId;
        }

        public BitArray256 getSessionKey() {
            return sessionKey;
        }
    }

    private static class UnauthorizedException extends Exception {
    }
}
