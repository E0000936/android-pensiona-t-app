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
 * <h1>Documento</h1>
 * Clase que contiene la estructura de documento
 * devuelta por el servicio de consulta lista
 * de documentos por trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-06
 */
public class Documento extends RealmObject {
    @SerializedName("id_tipo_doc")
    private Integer idTipoDoc;
    @SerializedName("id_param_envio_proceso")
    private Integer idParamEnvioProceso;
    @SerializedName("ayuda")
    private String ayuda;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("opcional_obligatorio")
    private Integer esObligatorio;
    @SerializedName("no_documentos")
    private Integer numeroDocumentos;

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDocumento) {

        this.idTipoDoc = idTipoDocumento;
    }

    public Integer getIdParamEnvioProceso() {
        return idParamEnvioProceso;
    }

    public void setIdParamEnvioProceso(Integer idParamEnvioProceso) {
        this.idParamEnvioProceso = idParamEnvioProceso;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEsObligatorio() {
        return esObligatorio;
    }

    public void setEsObligatorio(Integer esObligatorio) {
        this.esObligatorio = esObligatorio;
    }

    public Integer getNumeroDocumentos() {
        return numeroDocumentos;
    }

    public void setNumeroDocumentos(Integer numeroDocumentos) {
        this.numeroDocumentos = numeroDocumentos;
    }
}
