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
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Referencia;
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
 * <h1>RegistroTramiteServicio</h1>
 * Clase que ejecuta el servicio para Registrar un trámite
 * con los datos recabados en el formulario y retorna el base 64
 * del archivo SOLICITUD IDENTIFICACION PENSIONADO.pdf .
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-05
 */
public class RegistroTramiteServicio implements Response.Listener<JSONObject>, Response.ErrorListener, OauthServicio.IOauthServicio {

    private Context contexto;
    private IServicioRespuesta delegado;
    private Solicitante solicitante;
    private List<Digitalizacion> documentos;
    private Expediente expediente;
    private String firma;

    public RegistroTramiteServicio(Context context, IServicioRespuesta listener) {
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
     */
    public void ejecutar(Solicitante solicitante,
                         List<Digitalizacion> documentos,
                         Expediente expediente,
                         String firma) {
        this.solicitante = solicitante;
        this.documentos = documentos;
        this.expediente = expediente;
        this.firma = firma;

        JSONObject parametros = obtenerParametros(solicitante, documentos, expediente, firma);
        VolleySingleton pila = VolleySingleton.getInstancia(contexto);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                BuildConfig.PROFUTURO_SERVER + Constantes.Url.REGISTRO,
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
                                        String firma) {
        JSONObject objetoSolicitante = new JSONObject();
        try {
            objetoSolicitante.put("folio", expediente.getFolioMit());
            objetoSolicitante.put("id_peticion", expediente.getIdPeticion());
            objetoSolicitante.put("id_tramite", expediente.getIdTramite());
            objetoSolicitante.put("descripcion_tramite", expediente.getTramite());
            objetoSolicitante.put("id_subtramite", expediente.getIdSubtramite());
            objetoSolicitante.put("descripcion_subtramite", expediente.getSubtramite());

            objetoSolicitante.put("nombre_solicitante", solicitante.getNombre());
            objetoSolicitante.put("apellido_paterno_solicitante", solicitante.getApPaterno());
            objetoSolicitante.put("apellido_materno_solicitante", solicitante.getApMaterno());
            objetoSolicitante.put("rfc_solicitante", solicitante.getRfc());
            objetoSolicitante.put("curp_solicitante", solicitante.getCurp());
            objetoSolicitante.put("efirma_solicitante", solicitante.geteFirma());
            objetoSolicitante.put("fecha_nacimiento_solicitante", solicitante.getFechaNacimiento());
            objetoSolicitante.put("nss_solicitante", expediente.getNss());
            objetoSolicitante.put("correo_electronico", solicitante.getCorreo());

            Domicilio domicilio = solicitante.getDomicilio();
            objetoSolicitante.put("id_pais", domicilio.getIdPais());
            objetoSolicitante.put("descripcion_pais", domicilio.getPais());
            objetoSolicitante.put("id_nacionalidad", solicitante.getIdNacionalidad());
            objetoSolicitante.put("nacionalidad", solicitante.getNacionalidad());
            objetoSolicitante.put("id_ocupacion", solicitante.getIdOcupacion());
            objetoSolicitante.put("descripcion_ocupacion", solicitante.getOcupacion());
            objetoSolicitante.put("id_titular_cobro", solicitante.getIdTitularCobro());
            objetoSolicitante.put("id_oferta", solicitante.getIdOferta());
            objetoSolicitante.put("id_poliza", solicitante.getIdPoliza());
            objetoSolicitante.put("id_grupo_pago", solicitante.getIdGrpPago());
            objetoSolicitante.put("folio_oferta", solicitante.getFolioOferta());
            objetoSolicitante.put("regimen", expediente.getRegimen());
            objetoSolicitante.put("id_excepcion", expediente.getIdExcepcion());
            objetoSolicitante.put("descripcion_excepcion", expediente.getExcepcion());
            objetoSolicitante.put("id_grupo_pago_pol", solicitante.getIdGrpPago());
            objetoSolicitante.put("id_beneficiario_pol", expediente.getIdBeneficiarioPoliza());

            // En caso de ser seleccionada una excepción está ira concatenada con
            // las observaciones asociadas a la captura de los documentos.
            String comentarios = expediente.getObservaciones();
            if (expediente.getExcepcion() != null) {
                comentarios = comentarios.concat(" " + expediente.getExcepcion());
            }
            objetoSolicitante.put("observaciones", comentarios);
            objetoSolicitante.put("id_sexo", expediente.getIdSexo());
            objetoSolicitante.put("desc_sexo", expediente.getSexo());
            objetoSolicitante.put("poliza", expediente.getPoliza());
            objetoSolicitante.put("id_param_envio", expediente.getIdParamEnvio());

            JSONArray domicilios = new JSONArray();
            JSONObject objetoDomicilio = new JSONObject();
            objetoDomicilio.put("calle", domicilio.getCalle());
            objetoDomicilio.put("numero_exterior", domicilio.getNumExt());
            objetoDomicilio.put("numero_interior", domicilio.getNumInt());
            objetoDomicilio.put("codigo_postal", domicilio.getCp());
            objetoDomicilio.put("id_colonia", domicilio.getIdColonia());
            objetoDomicilio.put("colonia", domicilio.getAsentamiento());
            objetoDomicilio.put("id_municipio", domicilio.getIdMunicipio());
            objetoDomicilio.put("alcaldia", domicilio.getMunicipio());
            objetoDomicilio.put("id_ciudad", domicilio.getIdCiudad());
            objetoDomicilio.put("ciudad", domicilio.getCiudad());
            objetoDomicilio.put("id_entidad", domicilio.getIdEstado());
            objetoDomicilio.put("entidad", domicilio.getEstado());
            domicilios.put(objetoDomicilio);
            objetoSolicitante.put("Domicilio", domicilios);

            JSONArray telefonos = new JSONArray();
            for (Telefono telefonoForm : solicitante.getTelefonos()) {
                telefonos.put(Utilidades.obtenerObjetoTelefono(telefonoForm));
            }
            objetoSolicitante.put("Telefonos", telefonos);

            JSONArray referencias = new JSONArray();
            for (Referencia referenciaForm : solicitante.getReferencias()) {
                JSONObject referencia = new JSONObject();
                referencia.put("id_parentesco", referenciaForm.getIdParentesco());
                referencia.put("descripcion_parentesco", referenciaForm.getDescParentesco());
                referencia.put("nombre", referenciaForm.getNombre());
                referencia.put("apellido_paterno", referenciaForm.getApPaterno());
                referencia.put("apellido_materno", referenciaForm.getApMaterno());
                referencias.put(referencia);
                JSONArray telefonosReferencia = new JSONArray();

                if (referenciaForm.getTelefonoCasa() != null && !referenciaForm.getTelefonoCasa().isEmpty()) {
                    Telefono telefono = new Telefono();
                    telefono.setNumero(referenciaForm.getTelefonoCasa());
                    telefono.setIdTipo(DBHelperRealm.obtenerIdTelefono(Constantes.TipoTelefono.CASA.name()));
                    telefono.setTipo(Constantes.TipoTelefono.CASA.name());
                    telefonosReferencia.put(Utilidades.obtenerObjetoTelefono(telefono));
                }
                if (referenciaForm.getCelular() != null && !referenciaForm.getCelular().isEmpty()) {
                    Telefono telefono = new Telefono();
                    telefono.setNumero(referenciaForm.getCelular());
                    telefono.setIdTipo(DBHelperRealm.obtenerIdTelefono(Constantes.TipoTelefono.MOVIL.name()));
                    telefono.setTipo(Constantes.TipoTelefono.MOVIL.name());
                    telefonosReferencia.put(Utilidades.obtenerObjetoTelefono(telefono));
                }
                referencia.put("Telefonos", telefonosReferencia);
            }
            objetoSolicitante.put("Referencias", referencias);

            JSONArray bancos = new JSONArray();
            JSONObject objetoBancarios = new JSONObject();
            objetoBancarios.put("id_banco", solicitante.getBanco().getId());
            objetoBancarios.put("descripcion_banco", solicitante.getBanco().getDescripcion());
            objetoBancarios.put("tipo_cuenta", solicitante.getBanco().getTipoCuenta());
            objetoBancarios.put("clabe", solicitante.getBanco().getClabe());
            bancos.put(objetoBancarios);
            objetoSolicitante.put("DatosBancarios", bancos);

            JSONArray digitalizaciones = new JSONArray();
            for (Digitalizacion documentoForm : documentos) {
                JSONObject documento = new JSONObject();
                documento.put("id_documento", documentoForm.getIdParamEnvioProceso());
                documento.put("descripcion_documento", documentoForm.getDescripcion());
                documento.put("numero_documentos", documentoForm.getTomarCaptura());
                digitalizaciones.put(documento);
            }
            objetoSolicitante.put("Documentos", digitalizaciones);
            objetoSolicitante.put("firma", firma);

            return objetoSolicitante;
        } catch (JSONException e) {
            e.printStackTrace();
            delegado.obtenerRespuestaError(Constantes.TipoServicio.REGISTRO,
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
            delegado.obtenerRespuestaError(Constantes.TipoServicio.REGISTRO, tituloMensaje, mensajeError);
        } else {
            delegado.obtenerRespuestaExitosa(Constantes.TipoServicio.REGISTRO, jsonObjeto);
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
            delegado.obtenerRespuestaError(Constantes.TipoServicio.REGISTRO, errorServicio.getTitulo(), errorServicio.getMensaje());
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
            ejecutar(solicitante, documentos, expediente, firma);
        } else {
            delegado.obtenerRespuestaError(Constantes.TipoServicio.REGISTRO, error.getTitulo(), error.getMensaje());
        }
    }
}
