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
import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>CargarDocumentosServicio</h1>
 * Clase que se encarga de enviar la captura de los documento
 * tomados por medio del motor de imágenes, en forma de base 64.
 * Método POST
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-10
 */
public class CargarDocumentosServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {
    private Context contexto;
    private IServicioRespuesta delegado;
    private Captura captura;
    private String nombre;
    private String base64;
    private int numero;
    private int total;
    private String checksum;
    private Expediente expediente;

    public CargarDocumentosServicio(Context context, IServicioRespuesta listener) {
        this.contexto = context;
        this.delegado = listener;
    }

    /**
     * Ejecuta el llamado al servicio.
     *
     * @param expediente Objeto con valores referentes al trámite.
     * @param captura    Objeto con valores referentes a la captura tomada.
     * @param nombre     Nombre del documento a cargar junto con su extensión.
     * @param base64     Cadena que indica la codificación de un documento a base64.
     * @param checksum   Cadena que indica el valor checksum MD5.
     * @param numero     Valor que hace referencia a la posición actual
     *                   del documento dentro de la secuencia de archivos a enviar.
     *                   *ejemplo:1*.
     * @param total      Valor que hace referencia al total de
     *                   documentos a enviar.*ejemplo:10*.
     */
    public void ejecutar(Expediente expediente,
                         Captura captura,
                         String nombre,
                         String base64,
                         String checksum,
                         int numero,
                         int total) {
        this.captura = captura;
        this.nombre = nombre;
        this.base64 = base64;
        this.checksum = checksum;
        this.expediente = expediente;
        this.numero = numero;
        this.total = total;

        JSONObject parametros = obtenerParametros(expediente, captura, nombre, base64, checksum, numero, total);
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.CARGAR,
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
     * - folio: folio mit valor retornado del servicio "FolioTramiteServicio".
     * - id_peticion: id_peticion del servicio "TramitesServicio".
     * - id_tramite: id_tipo_tramite del servicio "TramitesServicio".
     * - id_subtramite: id_tramite_sucursal del servicio "TramitesServicio".
     * - id_documento: id_param_envio_proceso del servicio "TramitesServicio".
     * - desc_documento: descripcion del servicio "TramitesServicio".
     * - documento: Cadena que indica la codificación de un documento a base64.
     * - nombre: Nombre del documento a cargar junto con su extensión.
     * - consecutivo: Cada documento tiene un numero de capturas permitidas este
     * valor indica el número de captura actual con respecto al valor indicado en
     * no_documentos del servicio "TramitesServicio".
     * - numero: Valor que hace referencia a la posición actual del documento
     * dentro de la secuencia del total de los archivos a enviar.
     * - checksum: Cadena que indica el valor checksum MD5.
     * - total: Valor que hace referencia al total de documentos a enviar.
     * - id_tipo_doc: id_tipo_doc del servicio "TramitesServicio".
     * - id_param_envio: id_param_envio del servicio "TramitesServicio", en caso de
     * ser un pdf su valor será 0.
     *
     * @return json de la petición.
     */
    public JSONObject obtenerParametros(Expediente expediente,
                                        Captura captura,
                                        String nombre,
                                        String imgBase64,
                                        String checksum,
                                        int numero,
                                        int total) {
        JSONObject objetoDocumento = new JSONObject();
        try {
            objetoDocumento.put("folio", expediente.getFolioMit());
            objetoDocumento.put("id_peticion", expediente.getIdPeticion());
            objetoDocumento.put("id_tramite", expediente.getIdTramite());
            objetoDocumento.put("id_subtramite", expediente.getIdSubtramite());
            objetoDocumento.put("id_documento", captura.getIdParamEnvioProceso());
            objetoDocumento.put("desc_documento", captura.getDescripcion());
            objetoDocumento.put("documento", imgBase64);
            objetoDocumento.put("nombre", nombre);
            objetoDocumento.put("consecutivo", captura.getNumero());
            objetoDocumento.put("numero", numero);
            objetoDocumento.put("checksum", checksum);
            objetoDocumento.put("total", total);
            objetoDocumento.put("id_tipo_doc", captura.getIdTipoDoc());

            int idParamEnvio = expediente.getIdParamEnvio();
            if (nombre.equals(Constantes.Nombres.ARCHIVO_ACUSE) || nombre.equals(Constantes.Nombres.ARCHIVO_TRAMITE)) {
                idParamEnvio = 0;
            }
            objetoDocumento.put("id_param_envio", idParamEnvio);
            return objetoDocumento;
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CARGAR,
                    contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
            return null;
        }
    }

    /**
     * En caso de ser exitosa la petición http 200.
     */
    @Override
    public void onResponse(JSONObject response) {
        delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.CARGAR, response);
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
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CARGAR, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(expediente, captura, nombre, base64, checksum, numero, total);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.CARGAR, error.getTitulo(), error.getMensaje());
        }
    }
}