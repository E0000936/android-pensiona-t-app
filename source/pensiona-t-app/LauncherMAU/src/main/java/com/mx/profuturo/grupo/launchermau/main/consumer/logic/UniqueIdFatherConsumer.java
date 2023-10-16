package com.mx.profuturo.grupo.launchermau.main.consumer.logic;

import androidx.lifecycle.MutableLiveData;

import com.mx.profuturo.grupo.launchermau.core.Resource;
import com.mx.profuturo.grupo.launchermau.data.model.unique_id_father.DataUniqueIdFather;
import com.mx.profuturo.grupo.launchermau.main.consumer.service.UniqueIdFather;

public class UniqueIdFatherConsumer {

    public MutableLiveData<String> uuid_session = new MutableLiveData<>();
    public MutableLiveData<Integer> onError = new MutableLiveData<>();
    private final UniqueIdFather uniqueIdFather = new UniqueIdFather();

    public UniqueIdFatherConsumer() {}

    public void consumeUniqueIdFather() {
        uniqueIdFather.requestGenerateUniqueIdFather(new Resource<DataUniqueIdFather>() {
            @Override
            public void success(DataUniqueIdFather data) {
                uuid_session.setValue(data.getIdentificador());
            }

            @Override
            public void error(int code) {
                onError.setValue(code);
            }

            @Override
            public void error() {
                onError.setValue(-1);
            }
        });
    }

}
