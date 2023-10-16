package com.mx.profuturo.grupo.launchermau.core.rules;

import androidx.annotation.NonNull;

public enum BusinessLine {

    AFORE("922", "191","PA01", "534", "02"),
    ASEGURADORA("939", "193","PG01", "601", "05"),
    SOFOM("938", "192", "PS01", "602", "05"),;
    private String businessLine;
    private String businessLineOrgin;
    private String cveOperacion;
    private String cveEntSolicitante;
    private String cveOrigen;

    public static final String AFORE_LINE = "922";
    public static final String ASEGURADORA_LINE = "939";
    public static final String SOFOM_LINE = "938";

    BusinessLine(String businessLine, String businessLineOrgin, String cveOperacion, String cveEntSolicitante, String cveOrigen) {
        this.businessLine = businessLine;
        this.cveOperacion = cveOperacion;
        this.cveEntSolicitante = cveEntSolicitante;
        this.cveOrigen = cveOrigen;
        this.businessLineOrgin = businessLineOrgin;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public String getBusinessLineOrgin() {
        return businessLineOrgin;
    }

    public String getCveOperacion() {
        return cveOperacion;
    }

    public String getCveEntSolicitante() {
        return cveEntSolicitante;
    }

    public String getCveOrigen() {
        return cveOrigen;
    }
}
