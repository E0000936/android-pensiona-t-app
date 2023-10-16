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
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>FolioTramiteServicio</h1>
 * Clase que ejecuta el servicio para la obtener un folio mit.
 * Método POST
 *
 * @author Jose Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-03-27
 */
public class FolioTramiteServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private Context contexto;
    private IServicioRespuesta delegado;
    private Expediente expediente;
    private Solicitante solicitante;
    private String numEmpleado;
    private int idEnvio;
    private int idTramiteSucursal;

    public FolioTramiteServicio(Context contexto, IServicioRespuesta listener) {
        this.contexto = contexto;
        this.delegado = listener;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param expediente  Objeto con valores referentes al trámite.
     * @param solicitante Objeto con valores referentes al solicitante
     *                    del trámite.
     * @param numEmpleado numeroEmpleado obtenido del servicio "LoginServicio"
     *                    eliminando ceros a la izquierda.
     */
    public void ejecutar(Expediente expediente,
                         Solicitante solicitante,
                         String numEmpleado,
                         int idEnvio,
                         int idTramiteSucursal) {
        this.expediente = expediente;
        this.solicitante = solicitante;
        this.numEmpleado = numEmpleado;
        this.idEnvio = idEnvio;
        this.idTramiteSucursal = idTramiteSucursal;

        JSONObject parametros = obtenerParametros(expediente, solicitante, numEmpleado, idEnvio, idTramiteSucursal);
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.FOLIO,
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
     * <p>
     * - id_beneficario_oferta: id_beneficiario_oferta del servicio "BusquedaClienteServicio".
     * en caso de que la busqueda corresponda a un folio oferta y se haya seleccionado un componente.
     * - id_beneficiario_pol: id_beneficiario_poliza del servicio "BusquedaClienteServicio".
     * en caso de que la busqueda corresponda a una póliza y se haya seleccionado un componente.
     * - id_grupo_pago_pol: id_grupo_pago del servicio "DetalleClienteServicio".
     * - id_poliza: id_poliza del servicio "DetalleClienteServicio".
     * - id_oferta: id_oferta del servicio "DetalleClienteServicio".
     * - id_param_envio: id_param_envio del servicio "TramitesServicio".
     * - id_tipo_regimen: id_tipo_regimen_pol ó id_tipo_regimen_of del servicio "BusquedaClienteServicio".
     * - id_tramite_sucursal: id_tramite_sucursal del servicio "TramitesServicio".
     * - id_usuario: numeroEmpleado obtenido del servicio "LoginServicio" eliminando ceros a la izquierda.
     * - nota: cadena vacía ya que de momento no existe un componente para la captura de dicho mensaje.
     *
     * @return json de la petición.
     */
    public JSONObject obtenerParametros(Expediente expediente,
                                        Solicitante solicitante,
                                        String numEmpleado,
                                        int idEnvio,
                                        int idTramiteSucursal) {

        JSONObject objetoInterno = new JSONObject();
        try {
            objetoInterno.put("id_beneficario_oferta", expediente.getIdBeneficiarioOferta());
            objetoInterno.put("id_beneficiario_pol", expediente.getIdBeneficiarioPoliza());
            if (solicitante.getIdGrpPago() != 0) {
                objetoInterno.put("id_grupo_pago_pol", solicitante.getIdGrpPago());
            }
            if (solicitante.getIdPoliza() != 0) {
                objetoInterno.put("id_poliza", solicitante.getIdPoliza());
            }
            objetoInterno.put("id_oferta", solicitante.getIdOferta());
            objetoInterno.put("id_param_envio", idEnvio);
            objetoInterno.put("id_tipo_regimen", expediente.getIdTipoRegimen());
            objetoInterno.put("id_tramite_sucursal", idTramiteSucursal);
            objetoInterno.put("id_usuario", numEmpleado);
            objetoInterno.put("nota", "");

            return objetoInterno;
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
     * regresará el folio mit con el que será asociado el trámite.
     */
    @Override
    public void onResponse(JSONObject jsonObjeto) {
        String mensajeError = jsonObjeto.optString("rootErrorMessage");
        String tituloError = contexto.getString(R.string.datosIncorrectos);

        if (mensajeError != null && !mensajeError.equals("")) {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.FOLIO, tituloError, mensajeError);
        } else {
            delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.FOLIO, jsonObjeto);
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
            delegado.obtenerRespuestaError(Constantes.TipoServicio.FOLIO, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(expediente, solicitante, numEmpleado, idEnvio, idTramiteSucursal);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.FOLIO, error.getTitulo(), error.getMensaje());
        }
    }
}
