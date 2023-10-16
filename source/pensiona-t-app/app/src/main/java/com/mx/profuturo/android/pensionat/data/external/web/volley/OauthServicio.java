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
import com.android.volley.toolbox.StringRequest;
import com.mx.profuturo.android.pensionat.BuildConfig;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.VolleySingleton;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>OauthServicio</h1>
 * Clase que ejecuta el servicio para la obtención de token.
 * Método POST
 *
 * @author Everis
 * @version 1.0
 * @since 2019-06-01
 */
public class OauthServicio implements Response.ErrorListener, Response.Listener<String> {
    private Context contexto;
    private IOauthServicio delegado;

    public OauthServicio(Context context, IOauthServicio iOauthServicio) {
        this.contexto = context;
        this.delegado = iOauthServicio;
    }

    public void ejecutar() {
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        StringRequest sr = new StringRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.OAUTH,
                this,
                this) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "client_credentials");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", BuildConfig.BASIAUTH2_0);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(Constantes.Configuraciones.MILISEGUNDOS_REINTENTO,
                Constantes.Configuraciones.REINTENTOS,
                0));
        pila.addToRequestqueue(sr);
    }

    /**
     * En caso de error en la petición
     * se regresa el mensaje a mostrar
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.error_oauth));
        delegado.authenticate(errorServicio, false);
    }

    /**
     * En caso de ser exitosa la petición http 200
     * regresará el valor del access token el cual será
     * concatenada a la autorización Bearer y
     * almacenado en una variable global.
     */
    @Override
    public void onResponse(String response) {
        try {
            JSONObject result = new JSONObject(response);
            VariablesGlobales.getInstance().token = "Bearer ".concat(result.optString("access_token"));
            delegado.authenticate(null, true);
        } catch (JSONException e) {
            ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, new VolleyError(), contexto.getString(R.string.error_oauth));
            delegado.authenticate(errorServicio, false);
        }
    }

    @FunctionalInterface
    public interface IOauthServicio {
        void authenticate(ErrorServicio error, boolean esExitoso);
    }
}
