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
 * <h1>DetalleClienteServicio</h1>
 * Clase que ejecuta el servicio que obtiene la información asociada a un grupo familiar.
 * Método POST
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-16
 */
public class DetalleClienteServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private Context contexto;
    private IServicioRespuesta delegado;
    private Integer idPoliza;
    private Integer idOferta;
    private Integer idGrpPago;

    public DetalleClienteServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    // Metodos setter para tests unitarios
    public void setIdPoliza(Integer idPoliza) {
        this.idPoliza = idPoliza;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public void setIdGrpPago(Integer idGrpPago) {
        this.idGrpPago = idGrpPago;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param idPoliza  Valor id_poliza del servicio "BusquedaClienteServicio".
     * @param idOferta  Valor id_oferta del servicio "BusquedaClienteServicio".
     * @param idGrpPago Valor id_grupo_pago del servicio "BusquedaClienteServicio".
     */
    public void ejecutar(Integer idPoliza,
                         Integer idOferta,
                         Integer idGrpPago) {
        this.idPoliza = idPoliza;
        this.idOferta = idOferta;
        this.idGrpPago = idGrpPago;
        JSONObject parametros = obtenerParametros();
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.DETALLE,
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
        JSONObject objeto = new JSONObject();

        try {
            objeto.put("consecutivo_beneficiario", 0);
            objeto.put("id_beneficiario_poliza", 0);
            objeto.put("id_grupo_pago", idGrpPago);
            objeto.put("id_oferta", idOferta);
            objeto.put("id_poliza", idPoliza);

            return objeto;
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.DETALLE,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200
     * regresará el listado de datos precapturas asociados al solicitante
     * datos generales, domicilio, referencias y bancarios.
     */
    @Override
    public void onResponse(JSONObject jsonObjeto) {
        delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.DETALLE, jsonObjeto);
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
            delegado.obtenerRespuestaError(Constantes.TipoServicio.DETALLE, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(idPoliza, idOferta, idGrpPago);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.DETALLE, error.getTitulo(), error.getMensaje());
        }
    }
}