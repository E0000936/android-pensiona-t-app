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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>CatalogosServicio</h1>
 * Clase que ejecuta el servicio de obtención de todos los catálogos.
 * Método GET
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-04
 */
public class CatalogosServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private Context contexto;
    private IServicioRespuesta delegado;

    public CatalogosServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    /**
     * Ejecuta el llamado al servicio.
     */
    public void ejecutar() {
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.CATALOGO,
                null, this, this) {
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
     * En caso de ser exitosa la petición http 200
     * regresará el listado de catálogos y sus elementos asociados.
     */
    @Override
    public void onResponse(JSONObject response) {
        delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.CATALOGO, response);
    }

    /**
     * En caso de error en la petición
     * se procesa y se valida si es de tipo invalid token
     * se hace el llamado automático para obtener un nuevo token
     * en caso de ser un error diferente se regresa el mensaje a mostrar
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.error_catalogos));
        if (errorServicio.getMensaje().equals(Constantes.HTTPError.INVALID_TOKEN)) {
            new OauthServicio(contexto, this).ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CATALOGO, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CATALOGO, error.getTitulo(), error.getMensaje());
        }
    }
}
