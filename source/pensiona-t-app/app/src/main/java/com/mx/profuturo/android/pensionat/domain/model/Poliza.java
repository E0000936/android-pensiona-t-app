/*
 * Copyright (C) Profuturo All rights reserved. Todos los derechos reservados 2019.
 * mailto:
 *
 * Tramites digitales solo se puede distribuir con la autorización de Profuturo
 * License as published by Profuturo. Licencia publicada por Profuturo
 * version 1 .
 *
 * Tramites digitales is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.mx.profuturo.android.pensionat.domain.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

/**
 * <h1>Poliza</h1>
 * Clase que contiene la estructura principal
 * devuelta por el servicio consulta de búsqueda de clientes.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-06
 */
public class Poliza {
    @SerializedName("id_poliza")
    private Integer idPoliza = 0;
    @SerializedName("id_oferta")
    private Integer idOferta = 0;
    @SerializedName("id_tipo_regimen_pol")
    private Integer idRegimen = 0;
    @SerializedName("desc_tipo_regimen_pol")
    private String tipoRegimen = "";
    @SerializedName("id_estatus_poliza")
    private Integer idEstatus = 0;
    @SerializedName("estatus_poliza")
    private String estatus = "";
    @SerializedName("id_tipo_pension_pol")
    private Integer idTipoPension = 0;
    @SerializedName("tipo_pension_pol")
    private String tipoPension = "";
    @SerializedName("grupoPago")
    private RealmList<Grupo> grupos;

    public Integer getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(Integer idPoliza) {
        this.idPoliza = idPoliza;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Integer getIdRegimen() {
        return idRegimen;
    }

    public void setIdRegimen(Integer idRegimen) {
        this.idRegimen = idRegimen;
    }

    public String getTipoRegimen() {
        return tipoRegimen;
    }

    public void setTipoRegimen(String tipoRegimen) {
        this.tipoRegimen = tipoRegimen;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getIdTipoPension() {
        return idTipoPension;
    }

    public void setIdTipoPension(Integer idTipoPension) {
        this.idTipoPension = idTipoPension;
    }

    public String getTipoPension() {
        return tipoPension;
    }

    public void setTipoPension(String tipoPension) {
        this.tipoPension = tipoPension;
    }

    public RealmList<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(RealmList<Grupo> grupos) {
        this.grupos = grupos;
    }
}