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

import io.realm.RealmObject;

/**
 * <h1>Expediente</h1>
 * Clase que contiene los datos principales
 * asociados al trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-20
 */
public class Expediente extends RealmObject {
    private String poliza;
    private Integer grupoPago;
    private String idBeneficiarioOferta;
    private Integer idBeneficiarioPoliza;
    private String parentescoSolicitante;
    private String nombreTitular;
    private String apPaternoTitular;
    private String apMaternoTitular;
    private String nss;
    private String peticion;
    private Integer idPeticion;
    private Integer idTramite;
    private String tramite;
    private Integer idSubtramite;
    private Integer idParamEnvio;
    private String subtramite;
    private String observaciones;
    private String regimen;
    private Integer idTipoRegimen;
    private String folioMit;
    private String excepcion;
    private Integer idExcepcion;
    private Integer idSexo;
    private String Sexo;

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public Integer getGrupoPago() {
        return grupoPago;
    }

    public void setGrupoPago(Integer grupoPago) {
        this.grupoPago = grupoPago;
    }

    public String getIdBeneficiarioOferta() {
        return idBeneficiarioOferta;
    }

    public void setIdBeneficiarioOferta(String idBeneficiarioOferta) {
        this.idBeneficiarioOferta = idBeneficiarioOferta;
    }

    public Integer getIdBeneficiarioPoliza() {
        return idBeneficiarioPoliza;
    }

    public void setIdBeneficiarioPoliza(Integer idBeneficiarioPoliza) {
        this.idBeneficiarioPoliza = idBeneficiarioPoliza;
    }

    public String getParentescoSolicitante() {
        return parentescoSolicitante;
    }

    public void setParentescoSolicitante(String parentescoSolicitante) {
        this.parentescoSolicitante = parentescoSolicitante;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getApPaternoTitular() {
        return apPaternoTitular;
    }

    public void setApPaternoTitular(String apPaternoTitular) {
        this.apPaternoTitular = apPaternoTitular;
    }

    public String getApMaternoTitular() {
        return apMaternoTitular;
    }

    public void setApMaternoTitular(String apMaternoTitular) {
        this.apMaternoTitular = apMaternoTitular;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Integer getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Integer idTramite) {
        this.idTramite = idTramite;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public Integer getIdSubtramite() {
        return idSubtramite;
    }

    public void setIdSubtramite(Integer idSubtramite) {
        this.idSubtramite = idSubtramite;
    }

    public Integer getIdParamEnvio() {
        return idParamEnvio;
    }

    public void setIdParamEnvio(Integer idParamEnvio) {
        this.idParamEnvio = idParamEnvio;
    }

    public String getSubtramite() {
        return subtramite;
    }

    public void setSubtramite(String subtramite) {
        this.subtramite = subtramite;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public Integer getIdTipoRegimen() {
        return idTipoRegimen;
    }

    public void setIdTipoRegimen(Integer idTipoRegimen) {
        this.idTipoRegimen = idTipoRegimen;
    }

    public String getFolioMit() {
        return folioMit;
    }

    public void setFolioMit(String folioMit) {
        this.folioMit = folioMit;
    }

    public String getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(String excepcion) {
        this.excepcion = excepcion;
    }

    public Integer getIdExcepcion() {
        return idExcepcion;
    }

    public void setIdExcepcion(Integer idExcepcion) {
        this.idExcepcion = idExcepcion;
    }

    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }
}
