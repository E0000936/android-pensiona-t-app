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
 * <h1>BusquedaClienteServicio</h1>
 * Clase que ejecuta el servicio de busqueda por nss, curp, póliza o folio.
 * Obtiene la relación grupo familiar y componentes en base a la solicitud de datos enviada.
 * Método POST
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class BusquedaClienteServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private Context contexto;
    private IServicioRespuesta delegado;
    private String valorBusqueda;
    private Constantes.TipoBusqueda tipoBusqueda;

    public BusquedaClienteServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    // Metodos setter para tests unitarios
    public void setValorBusqueda(String valorBusqueda) {
        this.valorBusqueda = valorBusqueda;
    }

    public void setTipoBusqueda(Constantes.TipoBusqueda tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param valorBusqueda Cadena capturada por el asesor, el cual
     *                      hace referencia al valor a buscar.
     * @param tipoBusqueda  Hace referencia al tipo de valor a buscar
     *                      definido en la clase de constantes con los valores
     *                      de NSS, CURP, FOLIO y POLIZA.
     */
    public void ejecutar(String valorBusqueda, Constantes.TipoBusqueda tipoBusqueda) {
        this.valorBusqueda = valorBusqueda;
        this.tipoBusqueda = tipoBusqueda;
        JSONObject parametros = obtenerParametros();
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.BUSQUEDA,
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
     *
     * @return json de la petición
     */
    public JSONObject obtenerParametros() {
        JSONObject parametros = new JSONObject();

        try {
            switch (tipoBusqueda) {
                case NSS:
                    parametros.put("nss", valorBusqueda);
                    break;
                case CURP:
                    parametros.put("curp", valorBusqueda);
                    break;
                case FOLIO:
                    parametros.put("folioOferta", valorBusqueda);
                    break;
                case POLIZA:
                    parametros.put("poliza", valorBusqueda);
                    break;
                default:
                    break;
            }
            return parametros;
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.BUSQUEDA,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200
     * regresará el listado de pólizas, grupos de pago y componentes
     * familiares asociados a la búsqueda.
     */
    @Override
    public void onResponse(JSONObject jsonObjeto) {
        delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.BUSQUEDA, jsonObjeto);
    }

    /**
     * En caso de error en la petición
     * se procesa y se valida si es de tipo invalid token
     * se hace el llamado automático para obtener un nuevo token
     * en caso de ser un error diferente se regresa el mensaje a mostrar
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.no_existe_registro));
        if (errorServicio.getMensaje().equals(Constantes.HTTPError.INVALID_TOKEN)) {
            new OauthServicio(contexto, this).ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.BUSQUEDA, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(valorBusqueda, tipoBusqueda);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.BUSQUEDA, error.getTitulo(), error.getMensaje());
        }
    }
}

