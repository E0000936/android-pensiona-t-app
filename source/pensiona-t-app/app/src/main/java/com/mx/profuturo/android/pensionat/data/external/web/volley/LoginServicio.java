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
 * <h1>LoginServicio</h1>
 * Clase que ejecuta el servicio para validar las credenciales de un usuario CUSP.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-1
 */
public class LoginServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {

    public String numeroEmpleado;
    private Context contexto;
    private IServicioRespuesta delegado;
    private String contrasena;

    public LoginServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param numeroEmpleado Cadena capturada por el asesor, que corresponde
     *                       a sus credenciales de logueo internas.
     * @param contrasena     Cadena capturada por el asesor, que corresponde
     *                       a sus credenciales de logueo internas.
     */
    public void ejecutar(String numeroEmpleado, String contrasena) {
        this.numeroEmpleado = numeroEmpleado;
        this.contrasena = contrasena;
        JSONObject parametros = obtenerParametros(numeroEmpleado, contrasena);
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.LOGIN,
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
    }

    /**
     * Crea el cuerpo de la petición.
     * - aplicacion: "expedientePensiones" valor dado por Profuturo.
     *
     * @return json de la petición
     */
    public JSONObject obtenerParametros(String numeroEmpleado,
                                        String contrasena) {
        try {
            JSONObject objetoBase = new JSONObject();
            JSONObject objetoInterno = new JSONObject();
            objetoInterno.put("aplicacion", "expedientePensiones");
            objetoInterno.put("contrasena", contrasena);
            objetoInterno.put("usuario", numeroEmpleado);

            return objetoBase.put("rqt", objetoInterno);
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.LOGIN,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200
     * regresará el número de empleado, de ser incorrectas
     * las credenciales retornará un mensaje de excepción.
     */
    @Override
    public void onResponse(JSONObject jsonObjeto) {
        String mensajeError = jsonObjeto.optString("Exception");
        numeroEmpleado = jsonObjeto.optString("usuario");

        if (mensajeError != null && !mensajeError.isEmpty()) {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.LOGIN,
                    contexto.getString(R.string.datosIncorrectos),
                    mensajeError);
        } else {
            delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.LOGIN, jsonObjeto);
        }
    }

    /**
     * En caso de error en la petición
     * se procesa y se valida si es de tipo invalid token
     * se hace el llamado automático para obtener un nuevo token
     * en caso de ser un error diferente se regresa el mensaje a mostrar
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.datosIncorrectos));
        if (errorServicio.getMensaje().equals(Constantes.HTTPError.INVALID_TOKEN)) {
            new OauthServicio(contexto, this).ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.LOGIN, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(numeroEmpleado, contrasena);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.LOGIN, error.getTitulo(), error.getMensaje());
        }
    }
}
