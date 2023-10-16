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
package com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado;

import java.util.ArrayList;

/**
 * <h1>CodigoPostal</h1>
 * Modelo que almacena la estructura devuelta por el motor de
 * códigos postales.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-24
 */
public class CodigoPostal {
    private String codigoPostal;
    private int idEntidadFederativa;
    private String entidadFederativa;
    private String claveAlfanumericaEntidad;
    private int idMunicipio;
    private String municipio;
    private int idCiudad;
    private String ciudad;
    private ArrayList<Colonia> listaColonias;

    public CodigoPostal() {
        super();
        codigoPostal = "";
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setIdEntidadFederativa(int idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    public void setEntidadFederativa(String entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public ArrayList<Colonia> getListaColonias() {
        return listaColonias;
    }

    public void setListaColonias(ArrayList<Colonia> listaColonias) {
        this.listaColonias = listaColonias;
    }

    public String getClaveAlfanumericaEntidad() {
        return claveAlfanumericaEntidad;
    }

    public void setClaveAlfanumericaEntidad(String claveAlfanumericaEntidad) {
        this.claveAlfanumericaEntidad = claveAlfanumericaEntidad;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}

