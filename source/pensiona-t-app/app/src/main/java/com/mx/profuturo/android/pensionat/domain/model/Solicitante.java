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
import io.realm.RealmObject;

/**
 * <h1>Solicitante</h1>
 * Clase que contiene la estructura principa
 * devuelta por el servicio de detalle cliente.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-26
 */
public class Solicitante extends RealmObject {
    @SerializedName("curp")
    private String curp;
    @SerializedName("id_oferta")
    private Integer idOferta;
    @SerializedName("id_poliza")
    private Integer idPoliza;
    @SerializedName("id_grupo_pago")
    private Integer idGrpPago;
    @SerializedName("folio_oferta")
    private String folioOferta;
    @SerializedName("rfc")
    private String rfc;
    @SerializedName("apellido_paterno")
    private String apPaterno;
    @SerializedName("apellido_materno")
    private String apMaterno;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("ocupacion")
    private String ocupacion;
    @SerializedName("domicilio")
    private Domicilio domicilio;
    @SerializedName("telefonos")
    private RealmList<Telefono> telefonos;
    @SerializedName("referencias")
    private RealmList<Referencia> referencias;
    @SerializedName("datos_bancarios")
    private Banco banco;
    @SerializedName("fecha_nacimiento")
    private String fechaNacimiento;
    @SerializedName("e_firma")
    private String eFirma;
    @SerializedName("correo")
    private String correo;
    @SerializedName("id_titular_cobro")
    private Integer idTitularCobro;
    private Integer idOcupacion;

    // Datos Formulario
    @SerializedName("")
    private Integer idNacionalidad;
    private String nacionalidad;

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Integer getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(Integer idPoliza) {
        this.idPoliza = idPoliza;
    }

    public Integer getIdGrpPago() {
        return idGrpPago;
    }

    public void setIdGrpPago(Integer idGrpPago) {
        this.idGrpPago = idGrpPago;
    }

    public String getFolioOferta() {
        return folioOferta;
    }

    public void setFolioOferta(String folioOferta) {
        this.folioOferta = folioOferta;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public RealmList<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(RealmList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public RealmList<Referencia> getReferencias() {
        return referencias;
    }

    public void setReferencias(RealmList<Referencia> referencias) {
        this.referencias = referencias;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String geteFirma() {
        return eFirma;
    }

    public void seteFirma(String eFirma) {
        this.eFirma = eFirma;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getIdTitularCobro() {
        return idTitularCobro;
    }

    public void setIdTitularCobro(Integer idTitularCobro) {
        this.idTitularCobro = idTitularCobro;
    }

    public Integer getIdOcupacion() {
        return idOcupacion;
    }

    public void setIdOcupacion(Integer idOcupacion) {
        this.idOcupacion = idOcupacion;
    }

    public Integer getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Integer idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
