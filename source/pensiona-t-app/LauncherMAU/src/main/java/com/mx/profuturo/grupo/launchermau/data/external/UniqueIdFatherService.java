package com.mx.profuturo.grupo.launchermau.data.external;

import com.mx.profuturo.grupo.launchermau.data.model.unique_id_father.DataUniqueIdFather;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UniqueIdFatherService {
    /**
     * Interface para generar el id único para la autenticación
     *
     */
    @POST("mau/1/ga/autenticacion/identificador")
    Call<DataUniqueIdFather> requestGenerateUuidFather(@Header("Authorization") String token);
}
