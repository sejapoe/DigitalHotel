package ru.sejapoe.digitalhotel.data.auth;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import static ru.sejapoe.digitalhotel.data.auth.Auth.*;

import androidx.core.util.Pair;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import okhttp3.Response;
import ru.sejapoe.digitalhotel.data.net.HttpProvider;

public class LoginRepository {

    public static final String REGISTER_URL = "http://192.168.0.15:8080/register";
    public static final String LOGIN_URL = "http://192.168.0.15:8080/login";
    private final HttpProvider httpProvider = new HttpProvider();

    public void register(String login, String password) throws GeneralSecurityException, IOException, UserAlreadyExists {
        BitArray256 saltC = random256();

        BitArray256 saltS;
        try (Response post = httpProvider.post(REGISTER_URL + "/start", new Pair<>(login, saltC.asBase64()))) {

            if (post.code() == 302) throw new UserAlreadyExists();

            saltS = BitArray256.fromBase64(Objects.requireNonNull(post.body()).string());
        }
        BitArray256 salt = xorByteArrays(saltC, saltS);

        System.out.println("SaltC : " + Arrays.toString(saltC.asByteArray()));
        System.out.println("SaltS : " + Arrays.toString(saltS.asByteArray()));

        BitArray256 x = hash(password.getBytes(StandardCharsets.UTF_8), salt.asByteArray());
        BigInteger v = modPow(x);
        httpProvider.post(REGISTER_URL + "/finish", new Pair<>(login, v));
    }

    public void login(String login, String password) throws IOException, GeneralSecurityException, WrongPasswordException {
        BitArray256 a = random256();
        BigInteger A = modPow(a);
        Response post = httpProvider.post(LOGIN_URL + "/start", new Pair<>(login, A));
        String s = Objects.requireNonNull(post.body()).string();
        LoginServerResponse loginServerResponse = new Gson().fromJson(s, LoginServerResponse.class);
        BitArray256 u = hash(concatByteArrays(A.toByteArray(), loginServerResponse.getB().toByteArray()));
        byte[] salt = BitArray256.fromBase64(loginServerResponse.getSalt()).asByteArray();
        BitArray256 x = scrypt(password.getBytes(StandardCharsets.UTF_8), salt);
        BigInteger S = loginServerResponse.getB().subtract(k.multiply(modPow(x))).modPow(a.asBigInteger().add(u.asBigInteger().multiply(x.asBigInteger())), N);
        BitArray256 sessionKey = hash(S.toByteArray());

        System.out.println(sessionKey.asBase64());

        BitArray256 M = hash(
                concatByteArrays(
                        xorByteArrays(BitArray256.fromBigInteger(N), BitArray256.fromBigInteger(g)).asByteArray(),
                        login.getBytes(StandardCharsets.UTF_8),
                        salt,
                        A.toByteArray(),
                        loginServerResponse.getB().toByteArray(),
                        sessionKey.asByteArray()
                ));
        Response post2 = httpProvider.post(LOGIN_URL + "/finish", Base64.getEncoder().encodeToString(M.asByteArray()));
        if (post2.code() != 200) throw new WrongPasswordException();
        String sessionId = new BigInteger(Objects.requireNonNull(post2.body()).string()).toString(16);
        httpProvider.setSession(new HttpProvider.Session(sessionId, sessionKey));
    }

    public HttpProvider getHttpProvider() {
        return httpProvider;
    }

    private static class LoginServerResponse {
        @SerializedName("first")
        private final String salt;
        @SerializedName("second")
        private final BigInteger B;

        public LoginServerResponse(String salt, BigInteger b) {
            this.salt = salt;
            B = b;
        }

        public String getSalt() {
            return salt;
        }


        public BigInteger getB() {
            return B;
        }
    }

    public static class UserAlreadyExists extends Exception {
    }

    public static class WrongPasswordException extends Exception {
    }
}
