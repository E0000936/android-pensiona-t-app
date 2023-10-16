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

import io.realm.RealmObject;

/**
 * <h1>Referencia</h1>
 * Clase que contiene la estructura de referencias
 * devuelta por el servicio de detalle cliente.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-26
 */
public class Referencia extends RealmObject {
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellido_paterno")
    private String apPaterno;
    @SerializedName("apellido_materno")
    private String apMaterno;
    @SerializedName("desc_parentesco")
    private String descParentesco;
    @SerializedName("id_parentesco")
    private String idParentesco;

    // Datos Formulario
    private String telefonoCasa;
    private String celular;

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(String idParentesco) {
        this.idParentesco = idParentesco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescParentesco() {
        return descParentesco;
    }

    public void setDescParentesco(String descParentesco) {
        this.descParentesco = descParentesco;
    }
}
