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

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * <h1>Digitalizacion</h1>
 * Clase que contiene la estructura de un
 * documento a digitalizar.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-28
 */
public class Digitalizacion extends RealmObject {
    private Integer idParametroEnvioProceso;
    private String ayudaDigitalizacion;
    private String descripcionDigitalizacion;
    private Integer obligatorio;
    private Integer capturasDigitalizacion;
    private Integer tomarCapturaDigitalizaion;
    private RealmList<Captura> capturasArchivo;

    public Integer getIdParamEnvioProceso() {
        return idParametroEnvioProceso;
    }

    public void setIdParamEnvioProceso(Integer idParamEnvioProceso) {
        this.idParametroEnvioProceso = idParamEnvioProceso;
    }

    public String getAyuda() {
        return ayudaDigitalizacion;
    }

    public void setAyuda(String ayuda) {
        this.ayudaDigitalizacion = ayuda;
    }

    public String getDescripcion() {
        return descripcionDigitalizacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcionDigitalizacion = descripcion;
    }

    public Integer getEsObligatorio() {
        return obligatorio;
    }

    public void setEsObligatorio(Integer esObligatorio) {
        this.obligatorio = esObligatorio;
    }

    public Integer getCapturas() {
        return capturasDigitalizacion;
    }

    public void setCapturas(Integer capturas) {
        this.capturasDigitalizacion = capturas;
    }

    public Integer getTomarCaptura() {
        return tomarCapturaDigitalizaion;
    }

    public void setTomarCaptura(Integer tomarCaptura) {
        this.tomarCapturaDigitalizaion = tomarCaptura;
    }

    public RealmList<Captura> getCapturasArchivo() {
        return capturasArchivo;
    }

    public void setCapturasArchivo(RealmList<Captura> capturasArchivo) {
        this.capturasArchivo = capturasArchivo;
    }
}
