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
 * <h1>Captura</h1>
 * Modelo que almacena los datos referentes a
 * la captura de una imagén que será enviada por
 * el servicio de "CargarDocumentosServicio".
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-24
 */
public class Captura extends RealmObject {
    private Integer numero;
    private String ruta;
    private String descripcion;
    private String nombre;
    private Integer estatus;
    private Integer idParamEnvioProceso;
    private Integer idTipoDoc;
    private Integer cargaExitosa;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getIdParamEnvioProceso() {
        return idParamEnvioProceso;
    }

    public void setIdParamEnvioProceso(Integer idParamEnvioProceso) {
        this.idParamEnvioProceso = idParamEnvioProceso;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public Integer getCargaExitosa() {
        return cargaExitosa;
    }

    public void setCargaExitosa(Integer cargaExitosa) {
        this.cargaExitosa = cargaExitosa;
    }
}
