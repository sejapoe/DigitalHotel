package ru.sejapoe.digitalhotel.data.source.network;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Invocation;
import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.repository.SessionRepository;

public class TokenInterceptor implements Interceptor {

    private final SessionRepository sessionRepository;

    public TokenInterceptor(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newBuilder = originalRequest.newBuilder();


        try {
            Method method = Objects.requireNonNull(originalRequest.tag(Invocation.class)).method();
            if (method.isAnnotationPresent(Authenticated.class) || method.getDeclaringClass().isAnnotationPresent(Authenticated.class)) {
                String token = getToken();
                if (token == null) return new Response.Builder()
                        .request(originalRequest)
                        .body(ResponseBody.create("", MediaType.get("text/plain")))
                        .protocol(Protocol.HTTP_1_1)
                        .code(401)
                        .message("Unauthorized")
                        .build();
                newBuilder.addHeader("Authorization", "Bearer " + token);
            }
        } catch (NullPointerException ignored) {
        }

        return chain.proceed(newBuilder.build());
    }

    private String getToken() {
        Session session = sessionRepository.getSession();
        if (session == null) return null;
        Key key = Keys.hmacShaKeyFor(session.getSessionKey().asByteArray());
        JwtBuilder jwtBuilder = Jwts.builder().setSubject(String.valueOf(session.getSessionId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key);
        return jwtBuilder.compact();
    }
}
