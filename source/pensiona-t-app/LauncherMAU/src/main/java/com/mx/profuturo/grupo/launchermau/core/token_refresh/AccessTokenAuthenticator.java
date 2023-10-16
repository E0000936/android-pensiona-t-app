package com.mx.profuturo.grupo.launchermau.core.token_refresh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mx.profuturo.grupo.launchermau.core.RetrofitClient;

import java.net.HttpURLConnection;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AccessTokenAuthenticator implements Authenticator {

    private final AccessTokenProvider accessTokenProvider;

    public AccessTokenAuthenticator(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) {
        synchronized(this) {
            // verificamos el header si contiene Authorization o si exise un error 401
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                String updatedToken = accessTokenProvider.refreshToken();
                if (updatedToken == null) return null;
                // Retry the request with the new token.
                return response.request()
                        .newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer " + updatedToken)
                        .build();
            }
        }
        return null;
    }
}
