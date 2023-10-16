package com.mx.profuturo.grupo.launchermau.core.token_refresh;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitToken {

    private static Retrofit retrofit;

    private static final String PROFUTURO_API = BuildConfig.MAU_SERVER;
    private static Retrofit createRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(logging);
        long TIMEOUT = 10L;
        retrofit = new Retrofit.Builder()
                .client(okBuilder
                        .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                        .readTimeout(TIMEOUT, TimeUnit.MINUTES)
                        .build())
                .baseUrl(PROFUTURO_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @NonNull
    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) return createRetrofit().create(serviceClass);
        else return retrofit.create(serviceClass);
    }
}
