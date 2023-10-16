package com.mx.profuturo.grupo.launchermau.data.model.check_procedure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetStatusAccion {
    @SerializedName("tramite")
    @Expose
    private Tramite tramite;

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
}