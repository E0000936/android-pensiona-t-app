package com.mx.profuturo.grupo.launchermau.main.consumer.service;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.Resource;
import com.mx.profuturo.grupo.launchermau.core.RetrofitClient;
import com.mx.profuturo.grupo.launchermau.data.external.CheckProcedureService;
import com.mx.profuturo.grupo.launchermau.data.model.check_procedure.ResponseGetStatusAccion;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckProcedure {

    public CheckProcedure() {
    }

    public void getSessionStatus(String CURP, Resource<ResponseGetStatusAccion> resource) {
        RetrofitClient.createService(
                        CheckProcedureService.class
                ).sendRequestConsultarStatusUser(RetrofitClient.getTokenAccessKey(), CURP)
                .enqueue(new Callback<ResponseGetStatusAccion>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseGetStatusAccion> call, @NonNull Response<ResponseGetStatusAccion> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                            resource.success(response.body());
                        } else {
                            resource.error(response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseGetStatusAccion> call, @NonNull Throwable t) {
                        resource.error();
                    }
                });
    }
}