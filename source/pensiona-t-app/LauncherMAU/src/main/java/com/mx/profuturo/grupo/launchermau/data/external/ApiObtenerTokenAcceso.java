package com.mx.profuturo.grupo.launchermau.data.external;


import com.mx.profuturo.grupo.launchermau.BuildConfig;
import com.mx.profuturo.grupo.launchermau.data.model.tokenacceso.ResponseToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Interface para consumo de servicios de ObtenerToken
 *
 */

public interface ApiObtenerTokenAcceso {

    @Headers({
            "Authorization: " + BuildConfig.API_TOKEN, // debug IElrakdYS0FOVkhuejVvRHJ2R2RCQm55SnNGaUM4WmltOklHZmZjcERtTXlReWlXb0k=" release => WW5aeWhORlhhUUE4QnFHQmwzNDJqS0N0SUJGUllvWlU6WHJ3WkJ4S0t1R0FRZTBQdQ==
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<ResponseToken> sendRequestToken(@Field("grant_type") String grantType);


    @Headers({
            "Authorization: Q2xsTUd4OTEwRWJZTWZZZFhkMll4QVVHN0VHQ29LNEQ6ZzI2VVJBYVZCQkY5bVNvOQ==", // debug IElrakdYS0FOVkhuejVvRHJ2R2RCQm55SnNGaUM4WmltOklHZmZjcERtTXlReWlXb0k=" release => WW5aeWhORlhhUUE4QnFHQmwzNDJqS0N0SUJGUllvWlU6WHJ3WkJ4S0t1R0FRZTBQdQ==
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<ResponseToken> sendRequestProdToken(@Field("grant_type") String grantType);
}
