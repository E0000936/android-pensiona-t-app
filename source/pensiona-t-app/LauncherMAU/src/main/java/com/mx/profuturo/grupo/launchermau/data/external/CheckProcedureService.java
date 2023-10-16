package com.mx.profuturo.grupo.launchermau.data.external;

import com.mx.profuturo.grupo.launchermau.data.model.check_procedure.ResponseGetStatusAccion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CheckProcedureService {

    /**
     * Returns the status session
     *
     * @param curp CURP user that started the request
     * @return [ResponseGetStatusAccion]
     */
    @GET("mau/1/gu/usuarios/{curp}/check-tramite")
    Call<ResponseGetStatusAccion> sendRequestConsultarStatusUser(
            @Header("authorization") String token,
            @Path("curp") String curp);

}
