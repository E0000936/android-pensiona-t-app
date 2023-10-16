package com.mx.profuturo.grupo.launchermau.main.consumer.logic;

import androidx.lifecycle.MutableLiveData;

import com.mx.profuturo.grupo.launchermau.core.Resource;
import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;
import com.mx.profuturo.grupo.launchermau.data.model.check_procedure.ResponseGetStatusAccion;
import com.mx.profuturo.grupo.launchermau.main.consumer.service.CheckProcedure;

import java.net.HttpURLConnection;

public class CheckProcedureConsumer {

    public MutableLiveData<String> status_procedure = new MutableLiveData<>();
    public MutableLiveData<Integer> onError = new MutableLiveData<>();
    private final CheckProcedure checkProcedure = new CheckProcedure();

    public void consumeCheckProcedure(String curp) {
        checkProcedure.getSessionStatus(curp, new Resource<ResponseGetStatusAccion>() {
            @Override
            public void success(ResponseGetStatusAccion data) {
                status_procedure.setValue(data.getTramite().getEstado());
            }

            @Override
            public void error(int code) {
                if (code == HttpURLConnection.HTTP_NOT_FOUND)
                    status_procedure.setValue(TagsBuilder.TAG_SESSION_INACTIVE);
                else
                    onError.setValue(code);
            }

            @Override
            public void error() {
                onError.setValue(-1);
            }
        });
    }
}
