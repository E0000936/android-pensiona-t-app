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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <h1>Utilidades</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-07
 */
public class Utilidades {

    public Utilidades() {
        throw new IllegalStateException("Clase de ayuda");
    }

    public static void limpiarDatosTramite(Context contexto) {
        VariablesGlobales.getInstance().limpiar();
        ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathPdf());
        ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathMotor());
        DBHelperRealm.limpiar();
    }

    public static void limpiarDatosBusqueda(Context contexto) {
        List<Poliza> polizas = VariablesGlobales.getInstance().polizas;
        VariablesGlobales.getInstance().limpiar();
        VariablesGlobales.getInstance().polizas = polizas;
        ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathPdf());
        ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathMotor());
        DBHelperRealm.limpiar();
    }

    public static AlertaDialogo crearDialogoCancelar(Context contexto) {
        String titulo = contexto.getString(R.string.componente_familiar_cancelar_tramite);
        String mensaje = contexto.getString(R.string.componente_familiar_cancelar_aviso);
        String confirmacion = contexto.getString(R.string.componente_familiar_cancelar_confirmacion);
        return AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
    }

    public static String obtenerTiempoConFormato(int hora, int minuto) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a", Locale.forLanguageTag("es-ES"));
        return format1.format(calendar.getTime()).toUpperCase();
    }

    public static String obtenerFechaConFormato(int ano, int mes, int dia) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));
        return format1.format(calendar.getTime());
    }

    public static ErrorServicio obtenerErrorVolley(Context contexto, VolleyError error, String msjDefault) {
        String mensaje = contexto.getString(R.string.error_conexion);
        String titulo = contexto.getString(R.string.error_conexion_titulo);
        int errorHttp = 200;

        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null && networkResponse.data != null) {
            String jsonError = new String(networkResponse.data);
            errorHttp = networkResponse.statusCode;
            try {
                JSONObject obj = new JSONObject(jsonError);
                titulo = msjDefault;
                if (obj.optJSONObject("error") != null &&
                        obj.optJSONObject("error").optString("descripcion") != null) {
                    mensaje = obj.optJSONObject("error").optString("descripcion");

                    if (error instanceof AuthFailureError) {
                        titulo = contexto.getString(R.string.error_oauth);
                        if (obj.optJSONObject("error") != null &&
                                obj.optJSONObject("error").optString("codigo") != null) {
                            String codigo = obj.optJSONObject("error").optString("codigo");
                            if (codigo.equals(Constantes.HTTPError.INVALID_TOKEN)) {
                                mensaje = Constantes.HTTPError.INVALID_TOKEN;
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return new ErrorServicio(errorHttp, titulo, mensaje);
    }

    public static JSONObject obtenerObjetoTelefono(Telefono telefono) {
        JSONObject telefonoJSON = new JSONObject();
        try {
            telefonoJSON.put("numero", telefono.getNumero());
            telefonoJSON.put("id_tipo_telefono", telefono.getIdTipo());
            telefonoJSON.put("descripcion_tipo_telefono", telefono.getTipo());

            String horaHasta = null;
            String horaDe = null;
            if (telefono.getDisponibleHasta() != null) {
                horaHasta = telefono.getDisponibleHasta();
            }
            if (telefono.getDisponibleDesde() != null) {
                horaDe = telefono.getDisponibleDesde();
            }
            telefonoJSON.put("hora_de", horaDe);
            telefonoJSON.put("hora_hasta", horaHasta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return telefonoJSON;
    }

    public static String dateFormatter(Date fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));
        return formatter.format(fecha);
    }

    public static String timeFormatter(Date fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a", Locale.forLanguageTag("es-ES"));
        return formatter.format(fecha);
    }
}
