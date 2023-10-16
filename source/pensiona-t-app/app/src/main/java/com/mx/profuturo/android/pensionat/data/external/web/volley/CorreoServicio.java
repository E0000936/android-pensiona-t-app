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
package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mx.profuturo.android.pensionat.BuildConfig;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.VolleySingleton;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>CorreoServicio</h1>
 * Clase que se encarga de enviar por correo electrónico un archivo.
 * Método POST
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-24
 */
public class CorreoServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private static final String CORREO_REMITENTE = "tu-tramite-pensiones@profuturo.com.mx";
    private Context contexto;
    private IServicioRespuesta delegado;
    private String correo;
    private String nombreDoc;
    private String nombre;
    private String archivo;
    private int intentos;
    private String folio;

    public CorreoServicio(Context context, IServicioRespuesta listener, String nombreDoc) {
        this.contexto = context;
        this.delegado = listener;
        this.nombreDoc = nombreDoc;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param correo   Correo al que será enviado el archivo.
     * @param nombre   Nombre del archivo a enviar.
     * @param archivo  Cadena base64 del archivo a enviar.
     * @param intentos Número de intentos de la petición.
     * @param folio    folio mit del trámite.
     */
    public Boolean ejecutar(String correo, String nombre, String archivo, int intentos, String folio) {
        Boolean servicio = true;
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        this.correo = correo;
        this.nombre = nombre;
        this.archivo = archivo;
        this.intentos = intentos;
        this.folio = folio;
        JSONObject parametros = obtenerParametros(correo, nombre, archivo, intentos, folio);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.CORREO,
                parametros, this, this) {
            /** Añade a la petición los headers de autorización con
             * el token obtenido en el servicio de "OauthServicio"
             *
             * @return headers de autorización Bearer token
             */
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", VariablesGlobales.getInstance().token);
                return params;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(Constantes.Configuraciones.MILISEGUNDOS_REINTENTO,
                Constantes.Configuraciones.REINTENTOS,
                0));
        pila.addToRequestqueue(jsObjRequest);
        return servicio;
    }

    /**
     * Crea el cuerpo de la petición.
     *
     * @return json de la petición
     */
    public JSONObject obtenerParametros(String correo, String nombre, String archivo, int intentos, String folio) {
        JSONObject objetoBase = new JSONObject();
        JSONObject objetoInterno = new JSONObject();
        JSONObject objetoEncabezado = new JSONObject();
        JSONObject objetoAdjunto = new JSONObject();

        try {
            objetoEncabezado.put("asunto", "Confirmacion de tramite");
            objetoEncabezado.put("remitente", CORREO_REMITENTE);
            objetoEncabezado.put("destinatario", correo);

            objetoAdjunto.put("nombre", nombre);
            objetoAdjunto.put("contenido", archivo);

            objetoInterno.put("encabezado", objetoEncabezado);
            objetoInterno.put("adjunto", objetoAdjunto);
            objetoInterno.put("folio", folio);
            objetoInterno.put("intentos", intentos);
            objetoBase.put("rqt", objetoInterno);
            return objetoBase;

        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CORREO,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200.
     * se retornará el nombre y correo a donde fue enviado el archivo.
     */
    @Override
    public void onResponse(JSONObject response) {
        delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.CORREO, obtenerRespuestaFormato());
    }

    public JSONObject obtenerRespuestaFormato() {
        JSONObject corrreoJSON = new JSONObject();
        try {
            corrreoJSON.put("correo", correo);
            corrreoJSON.put("nombreDoc", nombreDoc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return corrreoJSON;
    }

    /**
     * En caso de error en la petición
     * se procesa y se valida si es de tipo invalid token
     * se hace el llamado automático para obtener un nuevo token
     * en caso de ser un error diferente se regresa el mensaje a mostrar
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.error_envio));
        if (errorServicio.getMensaje().equals(Constantes.HTTPError.INVALID_TOKEN)) {
            new OauthServicio(contexto, this).ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CORREO, errorServicio.getTitulo(), errorServicio.getMensaje());
        }
    }

    /**
     * Delegado del servicio de obtención de token
     * en caso de ser exitoso se reanuda la petición trunca
     * en caso de ser un error se regresa el mensaje a mostrar
     */
    @Override
    public void authenticate(ErrorServicio error, boolean esExitoso) {
        if (esExitoso) {
            ejecutar(correo, nombre, archivo, intentos, folio);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CORREO, error.getTitulo(), error.getMensaje());
        }
    }
}