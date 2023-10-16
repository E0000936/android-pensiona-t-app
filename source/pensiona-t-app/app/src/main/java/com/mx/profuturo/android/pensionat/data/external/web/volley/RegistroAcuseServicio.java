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
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>RegistroAcuseServicio</h1>
 * Clase que ejecuta el servicio para regresar el formato de acuse en base 64
 * ACUSE DE TRAMITE PENSIONES.pdf.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-05
 */
public class RegistroAcuseServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {

    private Context contexto;
    private IServicioRespuesta delegado;
    private Solicitante solicitante;
    private List<Digitalizacion> documentos;
    private Expediente expediente;
    private String firma;
    private String fecha;
    private String numEmpleado;


    public RegistroAcuseServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param solicitante Objeto con valores referentes al solicitante del trámite.
     * @param documentos  Lista de objetos con valores referentes a los documentos tomados.
     * @param expediente  Objeto con valores referentes al trámite.
     * @param firma       Cadena que indica la codificación de la firma del solicitante a base64.
     * @param fecha       Fecha de la tableta al momento de realizar la firma.
     * @param numEmpleado Número del empleado logueado.
     */
    public void ejecutar(Solicitante solicitante,
                         List<Digitalizacion> documentos,
                         Expediente expediente,
                         String firma,
                         String fecha,
                         String numEmpleado) {

        this.solicitante = solicitante;
        this.documentos = documentos;
        this.expediente = expediente;
        this.firma = firma;
        this.fecha = fecha;
        this.numEmpleado = numEmpleado;

        JSONObject parametros = obtenerParametros(solicitante, documentos, expediente, firma, fecha, numEmpleado);
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.ACUSE,
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
    public JSONObject obtenerParametros(Solicitante solicitante,
                                        List<Digitalizacion> documentos,
                                        Expediente expediente,
                                        String firma,
                                        String fecha,
                                        String numEmpleado) {
        JSONObject objetoSolicitante = new JSONObject();
        try {
            objetoSolicitante.put("folio", expediente.getFolioMit());
            objetoSolicitante.put("id_poliza", solicitante.getIdPoliza());
            objetoSolicitante.put("numero_renta_vitalicia", expediente.getPoliza());
            objetoSolicitante.put("nombre_titular", (expediente.getNombreTitular() != null) ? expediente.getNombreTitular() : "");
            objetoSolicitante.put("apellido_paterno_titular", (expediente.getApPaternoTitular() != null ? expediente.getApPaternoTitular() : ""));
            objetoSolicitante.put("apellido_materno_titular", (expediente.getApMaternoTitular() != null) ? expediente.getApMaternoTitular() : "");
            objetoSolicitante.put("nss_titular", expediente.getNss());
            objetoSolicitante.put("id_grupo_pago", expediente.getGrupoPago());

            objetoSolicitante.put("tipo_tramite", expediente.getSubtramite());
            objetoSolicitante.put("desc_parentesco", expediente.getParentescoSolicitante());
            objetoSolicitante.put("nombre_solicitante", solicitante.getNombre());
            objetoSolicitante.put("apellido_paterno_solicitante", solicitante.getApPaterno());
            objetoSolicitante.put("apellido_materno_solicitante", solicitante.getApMaterno());
            objetoSolicitante.put("id_excepcion", expediente.getIdExcepcion());
            objetoSolicitante.put("descripcion_excepcion", expediente.getExcepcion());

            String comentarios = expediente.getObservaciones();
            if (expediente.getExcepcion() != null) {
                comentarios = comentarios.concat(" " + expediente.getExcepcion());
            }
            objetoSolicitante.put("observaciones", comentarios);

            JSONArray telefonos = new JSONArray();
            for (Telefono telefonoForm : solicitante.getTelefonos()) {
                telefonos.put(Utilidades.obtenerObjetoTelefono(telefonoForm));
            }
            objetoSolicitante.put("Telefonos", telefonos);

            JSONArray digitalizaciones = new JSONArray();
            for (Digitalizacion documentoForm : documentos) {
                JSONObject documento = new JSONObject();
                documento.put("id_documento", documentoForm.getIdParamEnvioProceso());
                documento.put("descripcion_documento", documentoForm.getDescripcion());
                documento.put("entregado", true);
                digitalizaciones.put(documento);
            }
            objetoSolicitante.put("Documentos", digitalizaciones);
            objetoSolicitante.put("firma", firma);
            objetoSolicitante.put("fecha_formato", fecha);
            objetoSolicitante.put("numero_empleado", numEmpleado);

            return objetoSolicitante;
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.ACUSE,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200
     * regresará el base 64 del pdf acuse.
     */
    @Override
    public void onResponse(JSONObject jsonObjeto) {
        String mensajeError = jsonObjeto.optString("rootErrorMessage");
        String tituloMensaje = contexto.getString(R.string.acuse_error_carga);

        if (mensajeError != null && !mensajeError.equals("")) {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.ACUSE, tituloMensaje, mensajeError);
        } else {
            delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.ACUSE, jsonObjeto);

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
        ErrorServicio errorServicio = Utilidades.obtenerErrorVolley(contexto, error, contexto.getString(R.string.acuse_error_carga));
        if (errorServicio.getMensaje().equals(Constantes.HTTPError.INVALID_TOKEN)) {
            new OauthServicio(contexto, this).ejecutar();
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.ACUSE, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(solicitante, documentos, expediente, firma, fecha, numEmpleado);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.ACUSE, error.getTitulo(), error.getMensaje());
        }
    }
}