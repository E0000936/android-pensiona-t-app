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
 * <h1>Grupo</h1>
 * Clase que contiene la estructura de elementos
 * asociadas a los grupos de pagos devuelta por el servicio
 * de "BusquedaClienteServicio".
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-27
 */
public class Grupo {
    // Oferta
    @SerializedName("id_oferta")
    private Integer idOferta;
    @SerializedName("numero_oferta")
    private String numeroOferta;
    @SerializedName("id_sexo_of")
    private Integer idSexoOferta;
    @SerializedName("desc_sexo_of")
    private String sexoOferta;
    @SerializedName("appaterno_of")
    private String apPaternoOferta;
    @SerializedName("apmaterno_of")
    private String apMaternoOferta;
    @SerializedName("nombre_of")
    private String nombreOferta;
    @SerializedName("desc_tipo_regimen_of")
    private String descRegimenOferta;
    @SerializedName("id_tipo_regimen_of")
    private Integer idTipoRegimenOferta;
    @SerializedName("tipo_pension_ofe")
    private String tipoPensionOferta;
    @SerializedName("estatus_oferta")
    private String estatusOferta;
    @SerializedName("nss_of")
    private String nssOferta;
    @SerializedName("folio_identificador_of")
    private String folioOferta;

    // Poliza
    @SerializedName("id_grupo_pago")
    private Integer idGrupoPago;
    @SerializedName("gpo_pago")
    private Integer grupoPago;
    @SerializedName("id_sexo_pol")
    private Integer idSexoPol;
    @SerializedName("desc_sexo_pol")
    private String sexoPoliza;
    @SerializedName("amaterno_pol")
    private String apMaternoPoliza;
    @SerializedName("apaterno_pol")
    private String apPaternoPoliza;
    @SerializedName("nombre_pol")
    private String nombrePoliza;
    @SerializedName("nss_pol")
    private String nssPoliza;

    // Generales
    @SerializedName("numero_renta_vitalicia")
    private String numRenta;
    @SerializedName("apellido_paterno_tc")
    private String apPaternoTc;
    @SerializedName("apellido_materno_tc")
    private String apMaternoTc;
    @SerializedName("nombre_tc")
    private String nombreTc;

    @SerializedName("beneficiario")
    private RealmList<Componente> beneficiarios;

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getNumeroOferta() {
        return numeroOferta;
    }

    public void setNumeroOferta(String numeroOferta) {
        this.numeroOferta = numeroOferta;
    }

    public Integer getIdSexoOferta() {
        return idSexoOferta;
    }

    public void setIdSexoOferta(Integer idSexoOferta) {
        this.idSexoOferta = idSexoOferta;
    }

    public String getSexoOferta() {
        return sexoOferta;
    }

    public void setSexoOferta(String sexoOferta) {
        this.sexoOferta = sexoOferta;
    }

    public String getApPaternoOferta() {
        return apPaternoOferta;
    }

    public void setApPaternoOferta(String apPaternoOferta) {
        this.apPaternoOferta = apPaternoOferta;
    }

    public String getApMaternoOferta() {
        return apMaternoOferta;
    }

    public void setApMaternoOferta(String apMaternoOferta) {
        this.apMaternoOferta = apMaternoOferta;
    }

    public String getNombreOferta() {
        return nombreOferta;
    }

    public void setNombreOferta(String nombreOferta) {
        this.nombreOferta = nombreOferta;
    }

    public String getDescRegimenOferta() {
        return descRegimenOferta;
    }

    public void setDescRegimenOferta(String descRegimenOferta) {
        this.descRegimenOferta = descRegimenOferta;
    }

    public Integer getIdTipoRegimenOferta() {
        return idTipoRegimenOferta;
    }

    public void setIdTipoRegimenOferta(Integer idTipoRegimenOferta) {
        this.idTipoRegimenOferta = idTipoRegimenOferta;
    }

    public String getTipoPensionOferta() {
        return tipoPensionOferta;
    }

    public void setTipoPensionOferta(String tipoPensionOferta) {
        this.tipoPensionOferta = tipoPensionOferta;
    }

    public String getEstatusOferta() {
        return estatusOferta;
    }

    public void setEstatusOferta(String estatusOferta) {
        this.estatusOferta = estatusOferta;
    }

    public String getNssOferta() {
        return nssOferta;
    }

    public void setNssOferta(String nssOferta) {
        this.nssOferta = nssOferta;
    }

    public String getFolioOferta() {
        return folioOferta;
    }

    public void setFolioOferta(String folioOferta) {
        this.folioOferta = folioOferta;
    }

    public Integer getIdGrupoPago() {
        return idGrupoPago;
    }

    public void setIdGrupoPago(Integer idGrupoPago) {
        this.idGrupoPago = idGrupoPago;
    }

    public Integer getGrupoPago() {
        return grupoPago;
    }

    public void setGrupoPago(Integer grupoPago) {
        this.grupoPago = grupoPago;
    }

    public Integer getIdSexoPol() {
        return idSexoPol;
    }

    public void setIdSexoPol(Integer idSexoPol) {
        this.idSexoPol = idSexoPol;
    }

    public String getSexoPoliza() {
        return sexoPoliza;
    }

    public void setSexoPoliza(String sexoPoliza) {
        this.sexoPoliza = sexoPoliza;
    }

    public String getApMaternoPoliza() {
        return apMaternoPoliza;
    }

    public void setApMaternoPoliza(String apMaternoPoliza) {
        this.apMaternoPoliza = apMaternoPoliza;
    }

    public String getApPaternoPoliza() {
        return apPaternoPoliza;
    }

    public void setApPaternoPoliza(String apPaternoPoliza) {
        this.apPaternoPoliza = apPaternoPoliza;
    }

    public String getNombrePoliza() {
        return nombrePoliza;
    }

    public void setNombrePoliza(String nombrePoliza) {
        this.nombrePoliza = nombrePoliza;
    }

    public String getNssPoliza() {
        return nssPoliza;
    }

    public void setNssPoliza(String nssPoliza) {
        this.nssPoliza = nssPoliza;
    }

    public String getNumRenta() {
        return numRenta;
    }

    public void setNumRenta(String numRenta) {
        this.numRenta = numRenta;
    }

    public String getApPaternoTc() {
        return apPaternoTc;
    }

    public void setApPaternoTc(String apPaternoTc) {
        this.apPaternoTc = apPaternoTc;
    }

    public String getApMaternoTc() {
        return apMaternoTc;
    }

    public void setApMaternoTc(String apMaternoTc) {
        this.apMaternoTc = apMaternoTc;
    }

    public String getNombreTc() {
        return nombreTc;
    }

    public void setNombreTc(String nombreTc) {
        this.nombreTc = nombreTc;
    }

    public RealmList<Componente> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(RealmList<Componente> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }
}
