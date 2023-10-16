package com.mx.profuturo.grupo.launchermau.main.consumer.service;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.Resource;
import com.mx.profuturo.grupo.launchermau.core.RetrofitClient;
import com.mx.profuturo.grupo.launchermau.data.external.UniqueIdFatherService;
import com.mx.profuturo.grupo.launchermau.data.model.unique_id_father.DataUniqueIdFather;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UniqueIdFather {

    public void requestGenerateUniqueIdFather(Resource<DataUniqueIdFather> resource) {
        RetrofitClient.createService(UniqueIdFatherService.class).requestGenerateUuidFather(RetrofitClient.getTokenAccessKey()).
                enqueue(new Callback<DataUniqueIdFather>() {
                    @Override
                    public void onResponse(@NonNull Call<DataUniqueIdFather> call, @NonNull Response<DataUniqueIdFather> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                            resource.success(response.body());
                        } else {
                            resource.error(response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DataUniqueIdFather> call, @NonNull Throwable t) {
                        resource.error();
                    }
                });
    }

}
