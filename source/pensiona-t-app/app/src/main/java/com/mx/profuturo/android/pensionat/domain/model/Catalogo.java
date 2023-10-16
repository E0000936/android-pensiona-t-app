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
 * <h1>Catalogo</h1>
 * Clase que contiene la estructura principal
 * devuelta por el servicio consulta de catálogos.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-15
 */
public class Catalogo extends RealmObject {
    @SerializedName("idCatalogo")
    private Integer id;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("elementos")
    private RealmList<Categoria> elementos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public RealmList<Categoria> getElementos() {
        return elementos;
    }

    public void setElementos(RealmList<Categoria> elementos) {
        this.elementos = elementos;
    }
}
