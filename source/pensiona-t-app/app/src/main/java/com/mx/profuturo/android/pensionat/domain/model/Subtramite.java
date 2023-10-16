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
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.Required;

/**
 * <h1>Subtramite</h1>
 * Clase que contiene la estructura subtramite
 * devuelta por el servicio de consulta documentos asociados
 * a un trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class Subtramite extends RealmObject {
    @LinkingObjects("subtramites")
    private final RealmResults<Tramite> tramites = null;
    @Required
    @SerializedName("id_tramite_sucursal")
    private Integer id;
    @SerializedName("nombre_tramite_sucursal")
    private String nombre;
    @SerializedName("id_param_envio")
    private Integer idParamEnvio;
    @SerializedName("documentos")
    private RealmList<Documento> documentos;

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

    public Integer getIdParamEnvio() {
        return idParamEnvio;
    }

    public void setIdParamEnvio(Integer idParamEnvio) {
        this.idParamEnvio = idParamEnvio;
    }

    public RealmList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(RealmList<Documento> documentos) {
        this.documentos = documentos;
    }
}
