package com.mx.profuturo.grupo.launchermau.data.model.unique_id_father;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUniqueIdFather {
    @SerializedName("identificador")
    @Expose
    private String identificador;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
