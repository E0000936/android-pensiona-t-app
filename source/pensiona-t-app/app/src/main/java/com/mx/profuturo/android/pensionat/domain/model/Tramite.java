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
import io.realm.annotations.Required;

/**
 * <h1>Tramite</h1>
 * Clase que contiene la estructura de trámites
 * devuelta por el servicio de consulta documentos asociados
 * a un trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class Tramite extends RealmObject {
    @Required
    @SerializedName("id_tipo_tramite")
    private Integer id;
    @SerializedName("nombre_tramite")
    private String nombre;
    @SerializedName("id_tipo_peticion")
    private Integer idTipoPeticion;
    @SerializedName("subtramites")
    private RealmList<Subtramite> subtramites;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTipoPeticion() {
        return idTipoPeticion;
    }

    public void setIdTipoPeticion(Integer idTipoPeticion) {
        this.idTipoPeticion = idTipoPeticion;
    }

    public RealmList<Subtramite> getSubtramites() {
        return subtramites;
    }

    public void setSubtramites(RealmList<Subtramite> subtramites) {
        this.subtramites = subtramites;
    }
}
