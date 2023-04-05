package ru.sejapoe.digitalhotel.data.repository;

import static ru.sejapoe.digitalhotel.utils.AuthUtils.N;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.concatByteArrays;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.g;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.hash;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.k;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.modPow;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.random256;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.scrypt;
import static ru.sejapoe.digitalhotel.utils.AuthUtils.xorByteArrays;

import androidx.core.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Response;
import ru.sejapoe.digitalhotel.data.model.Session;
import ru.sejapoe.digitalhotel.data.source.db.SessionDao;
import ru.sejapoe.digitalhotel.data.source.network.LoginService;
import ru.sejapoe.digitalhotel.data.source.network.RetrofitProvider;
import ru.sejapoe.digitalhotel.utils.BitArray256;

public class LoginRepository {
    private final SessionDao sessionDao;
    private final LoginService loginService;

    public LoginRepository(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
        this.loginService = RetrofitProvider.getInstance().createLoginService();
    }

    public void register(String login, String password) throws GeneralSecurityException, IOException, UserAlreadyExists {
        BitArray256 saltC = random256();

        BitArray256 saltS;
        Call<String> startRegistration = loginService.startRegistration(new Pair<>(login, saltC.asBase64()));
        Response<String> post = startRegistration.execute();
        if (post.code() == 302) throw new UserAlreadyExists();

        saltS = BitArray256.fromBase64(post.body());
        BitArray256 salt = xorByteArrays(saltC, saltS);
        BitArray256 x = hash(password.getBytes(StandardCharsets.UTF_8), salt.asByteArray());
        BigInteger v = modPow(x);
        loginService.finishRegistration(new Pair<>(login, v)).execute();
    }

    public void login(String login, String password) throws IOException, GeneralSecurityException, WrongPasswordException, NoSuchUserException {
        BitArray256 a = random256();
        BigInteger A = modPow(a);
        Call<LoginServerResponse> serverResponseCall = loginService.startLogin(new Pair<>(login, A));
        Response<LoginServerResponse> response = serverResponseCall.execute();
        if (response.code() == 404) throw new NoSuchUserException();
        LoginServerResponse loginServerResponse = response.body();
        if (loginServerResponse == null) throw new IOException();
        BitArray256 u = hash(concatByteArrays(A.toByteArray(), loginServerResponse.getB().toByteArray()));
        byte[] salt = BitArray256.fromBase64(loginServerResponse.getSalt()).asByteArray();
        BitArray256 x = scrypt(password.getBytes(StandardCharsets.UTF_8), salt);
        BigInteger S = loginServerResponse.getB().subtract(k.multiply(modPow(x))).modPow(a.asBigInteger().add(u.asBigInteger().multiply(x.asBigInteger())), N);
        BitArray256 sessionKey = hash(S.toByteArray());
        BitArray256 M = hash(
                concatByteArrays(
                        xorByteArrays(BitArray256.fromBigInteger(N), BitArray256.fromBigInteger(g)).asByteArray(),
                        login.getBytes(StandardCharsets.UTF_8),
                        salt,
                        A.toByteArray(),
                        loginServerResponse.getB().toByteArray(),
                        sessionKey.asByteArray()
                ));
        String sessionId;
        Call<String> finishLogin = loginService.finishLogin(Base64.getEncoder().encodeToString(M.asByteArray()));
        Response<String> post2 = finishLogin.execute();
        if (post2.code() != 200) throw new WrongPasswordException();
        if (post2.body() == null) throw new IOException();
        sessionId = new BigInteger(post2.body()).toString(16);
        Session session = new Session(sessionId, sessionKey);
        RetrofitProvider.getInstance().setSession(session);
        sessionDao.set(session);
    }

    public void logOut() {
        try {
            loginService.logOut().execute();
        } catch (IOException ignored) {
        }
        RetrofitProvider.getInstance().setSession(null);
        sessionDao.drop();
    }

    public static class LoginServerResponse {
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

    public static class NoSuchUserException extends Exception {
    }
}
