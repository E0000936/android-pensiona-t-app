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

/**
 * <h1>Componente</h1>
 * Clase que contiene la estructura de beneficiario
 * devuelta por el servicio de búsqueda de cliente.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-06
 */
public class Componente {
    @SerializedName("id_beneficiario_oferta")
    private String idBeneficiarioOferta;
    @SerializedName("id_beneficiario_poliza")
    private Integer idBeneficiarioPoliza;
    @SerializedName("consecutivo_beneficiario")
    private Integer consecutivoBeneficiario;
    @SerializedName("nombre_completo")
    private String nombreCompleto;
    @SerializedName("id_parentesco")
    private Integer idParentesco;
    @SerializedName("desc_parentesco")
    private String parentesco;
    @SerializedName("id_sexo")
    private Integer idSexo;
    @SerializedName("desc_sexo_ben")
    private String sexo;
    @SerializedName("fecha_nacimiento")
    private String fechaNacimiento;
    @SerializedName("id_estatus_beneficiario")
    private Integer idEstatus;
    @SerializedName("estatus_beneficiario")
    private String estatusBeneficiario;
    @SerializedName("id_grupo_pago")
    private Integer idGrupoPago;

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

    public Integer getConsecutivoBeneficiario() {
        return consecutivoBeneficiario;
    }

    public void setConsecutivoBeneficiario(Integer consecutivoBeneficiario) {
        this.consecutivoBeneficiario = consecutivoBeneficiario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(Integer idParentesco) {
        this.idParentesco = idParentesco;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatusBeneficiario() {
        return estatusBeneficiario;
    }

    public void setEstatusBeneficiario(String estatusBeneficiario) {
        this.estatusBeneficiario = estatusBeneficiario;
    }

    public Integer getIdGrupoPago() {
        return idGrupoPago;
    }

    public void setIdGrupoPago(Integer idGrupoPago) {
        this.idGrupoPago = idGrupoPago;
    }
}
