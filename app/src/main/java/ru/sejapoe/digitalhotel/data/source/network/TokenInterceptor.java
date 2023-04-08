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
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;
import ru.sejapoe.digitalhotel.data.model.Session;

public class TokenInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newBuilder = originalRequest.newBuilder();


        try {
            Method method = Objects.requireNonNull(originalRequest.tag(Invocation.class)).method();
            if (method.isAnnotationPresent(AuthorizationRequired.class)) {
                String token = getToken();
                if (token == null) return new Response.Builder()
                        .request(originalRequest)
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
        Session session = RetrofitProvider.getInstance().getSession();
        if (session == null) return null;
        Key key = Keys.hmacShaKeyFor(session.getSessionKey().asByteArray());
        JwtBuilder jwtBuilder = Jwts.builder().setSubject(String.valueOf(session.getSessionId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key);
        return jwtBuilder.compact();
    }
}
