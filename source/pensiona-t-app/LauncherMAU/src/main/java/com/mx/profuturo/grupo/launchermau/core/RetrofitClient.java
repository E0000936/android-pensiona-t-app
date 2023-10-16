package com.mx.profuturo.grupo.launchermau.core;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.BuildConfig;
import com.mx.profuturo.grupo.launchermau.core.token_refresh.AccessTokenAuthenticator;
import com.mx.profuturo.grupo.launchermau.core.token_refresh.AccessTokenProvider;
import com.mx.profuturo.grupo.launchermau.core.token_refresh.RetrofitToken;
import com.mx.profuturo.grupo.launchermau.data.external.ApiObtenerTokenAcceso;
import com.mx.profuturo.grupo.launchermau.data.model.tokenacceso.ResponseToken;

import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    /**
     * time to wait at connect and disconnect at the service
     */
    private static final Long TIMEOUT = 10L;
    /**
     * Singleton instance for retrofit
     */
    private static Retrofit retrofit = null;
    /**
     * Base URL
     */
    private static final String URL = BuildConfig.MAU_SERVER;

    private static String TOKEN_ACCESS_KEY = "NOT_FOUND";
    private static final String businessLine = "939";

    private static Retrofit createRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        Interceptor interceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("linea-negocio", businessLine)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };

        okBuilder.addInterceptor(interceptor).addInterceptor(logging);
        AccessTokenAuthenticator accessTokenAuthenticator = new AccessTokenAuthenticator(accessTokenProvider);
        okBuilder.authenticator(accessTokenAuthenticator);
        retrofit = new Retrofit.Builder()
                .client(okBuilder
                        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                        .build())
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @NonNull
    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) return createRetrofit().create(serviceClass);
        else return retrofit.create(serviceClass);
    }

    @NonNull
    @Contract(pure = true)
    public static String getTokenAccessKey() {
        return "Bearer " + TOKEN_ACCESS_KEY;
    }

    private static final AccessTokenProvider accessTokenProvider = () -> {
        try {

            Call<ResponseToken> client_credentials;
            client_credentials = RetrofitToken
                    .createService(ApiObtenerTokenAcceso.class)
                    .sendRequestToken("client_credentials");

            Response<ResponseToken> tokenResponse = client_credentials.execute();

            assert tokenResponse.body() != null;
            TOKEN_ACCESS_KEY = tokenResponse.body().getAccessToken();
            return TOKEN_ACCESS_KEY;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    };
}
