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
package com.mx.profuturo.android.pensionat.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.CodigoPostal;
import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.Colonia;

import java.util.ArrayList;

/**
 * <h1>DbHelperEstados</h1>
 * Clase de utilidad Profuturo para obtener la
 * dirección retornada a partir de un código postal.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-21
 */
public class DbHelperEstados {
    private static final String CODIGO_POSTAL = "codigo_postal";
    private static final String ID_ENTIDAD_FEDERATIVA = "id_entidad_federativa";
    private static final String CLAVE_ALFANUMERICA_ENTIDAD = "clave_alfanumerica_entidad";
    private static final String ENTIDAD_FEDERATIVA = "entidad_federativa";
    private static final String ID_MUNICIPIO = "id_municipio";
    private static final String MUNICIPIO = "municipio";
    private static final String ID_COLONIA = "id_colonia";
    private static final String COLONIA = "colonia";
    private static final String ID_CIUDAD = "id_ciudad";
    private static final String CIUDAD = "ciudad";
    private Context context;

    public DbHelperEstados(Context context) {
        this.context = context;
    }

    public CodigoPostal obtenerDireccion(String codigoPostalID) {
        CodigoPostal codigoPostal = null;
        Uri uriMotor = ConsultaMotorCodigosPostales.obtenerUriCodigoPostal(codigoPostalID);
        if (uriMotor != null) {
            Cursor c = ConsultaMotorCodigosPostales.consultaCodigosPostales(context, uriMotor);
            codigoPostal = recorrerCursorMotor(c);
        }

        return codigoPostal;
    }

    public CodigoPostal recorrerCursorMotor(Cursor c) {
        CodigoPostal codigoPostal = null;
        if (c != null && c.moveToFirst()) {
            ArrayList<Colonia> listaColonias = new ArrayList<>();
            codigoPostal = new CodigoPostal();
            int contador = 0;

            do {
                listaColonias.add(obtenerColonia(c));
                int codigoPostalRegistro = c.getInt(c.getColumnIndex(CODIGO_POSTAL));
                if (codigoPostalRegistro == 0) {
                    continue;
                }
                if (contador == 0) {
                    codigoPostal = obtenerCP(c, codigoPostalRegistro);
                }
                contador++;
            } while (c.moveToNext());
            codigoPostal.setListaColonias(listaColonias);
        }

        return codigoPostal;
    }

    public Colonia obtenerColonia(Cursor c) {
        Colonia colonia = new Colonia();
        colonia.setIdColonia(c.getInt(c.getColumnIndex(ID_COLONIA)));
        colonia.setNombre(c.getString(c.getColumnIndex(COLONIA)));
        return colonia;
    }

    public CodigoPostal obtenerCP(Cursor c, int codigoPostalRegistro) {
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal(String.valueOf(codigoPostalRegistro));
        codigoPostal.setIdEntidadFederativa(c.getInt(c.getColumnIndex(ID_ENTIDAD_FEDERATIVA)));
        codigoPostal.setEntidadFederativa(c.getString(c.getColumnIndex(ENTIDAD_FEDERATIVA)));
        codigoPostal.setIdMunicipio(c.getInt(c.getColumnIndex(ID_MUNICIPIO)));
        codigoPostal.setMunicipio(c.getString(c.getColumnIndex(MUNICIPIO)));
        codigoPostal.setClaveAlfanumericaEntidad(c.getString(c.getColumnIndex(CLAVE_ALFANUMERICA_ENTIDAD)));
        codigoPostal.setIdCiudad(c.getInt(c.getColumnIndex(ID_CIUDAD)));
        codigoPostal.setCiudad(c.getString(c.getColumnIndex(CIUDAD)));

        return codigoPostal;
    }
}
