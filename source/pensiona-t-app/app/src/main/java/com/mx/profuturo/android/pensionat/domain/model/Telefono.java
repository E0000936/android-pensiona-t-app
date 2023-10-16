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
 * <h1>Telefono</h1>
 * Clase que contiene la estructura de telefonos
 * devuelta por el servicio detalle cliente.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-9
 */
public class Telefono extends RealmObject {
    @SerializedName("numero")
    private String numero;
    @SerializedName("tipo_telefono")
    private String tipo;
    private Integer idTipo;
    @SerializedName("horario_de")
    private String disponibleDesde;
    @SerializedName("horario_hasta")
    private String disponibleHasta;

    public String getDisponibleDesde() {
        return disponibleDesde;
    }

    public void setDisponibleDesde(String disponibleDesde) {
        this.disponibleDesde = disponibleDesde;
    }

    public String getDisponibleHasta() {
        return disponibleHasta;
    }

    public void setDisponibleHasta(String disponibleHasta) {
        this.disponibleHasta = disponibleHasta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }
}
