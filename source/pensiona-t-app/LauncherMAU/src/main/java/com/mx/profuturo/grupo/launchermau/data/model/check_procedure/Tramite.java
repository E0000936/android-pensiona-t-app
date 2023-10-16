package com.mx.profuturo.grupo.launchermau.data.model.check_procedure;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tramite {
    @SerializedName("idProceso")
    @Expose
    private String idProceso;
    @SerializedName("idSubProceso")
    @Expose
    private String idSubProceso;
    @SerializedName("estado")
    @Expose
    private String estado;

    public String getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdSubProceso() {
        return idSubProceso;
    }

    public void setIdSubProceso(String idSubProceso) {
        this.idSubProceso = idSubProceso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}