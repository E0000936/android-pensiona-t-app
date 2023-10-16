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
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.Required;

/**
 * <h1>Categoria</h1>
 * Clase que contiene la estructura de elementos
 * devuelta por el servicio consulta de catálogos.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class Categoria extends RealmObject {

    @LinkingObjects("elementos")
    private final RealmResults<Catalogo> catalogos = null;
    @Required
    @SerializedName("idCategoria")
    private Integer id;
    @SerializedName("cveNumerica")
    private Integer clave;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("descripcionCompleta")
    private String descCompleta;
    @SerializedName("idInstitucion")
    private String bancoDigitos;

    public Categoria() {
    }

    public Categoria(Integer id, Integer clave, String descripcion, String descCompleta) {
        this.id = id;
        this.clave = clave;
        this.descripcion = descripcion;
        this.descCompleta = descCompleta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescCompleta() {
        return descCompleta;
    }

    public void setDescCompleta(String descCompleta) {
        this.descCompleta = descCompleta;
    }

    public String getBancoDigitos() {
        return bancoDigitos;
    }

    public void setBancoDigitos(String bancoDigitos) {
        this.bancoDigitos = bancoDigitos;
    }
}
