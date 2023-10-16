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
package com.mx.profuturo.android.pensionat.presentation.tramite;

import android.content.Context;

import com.mx.profuturo.android.pensionat.data.external.web.volley.FolioTramiteServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Documento;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.TraceLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmList;

/**
 * <h1>TramitePresenter</h1>
 * Clase que contiene la lógica de selección de trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-25
 */
public class TramitePresenter implements IServicioRespuesta {

    private static final Pattern numEmpleadoRegex = Pattern.compile("^0+(?!$)");
    private Context contexto;
    private ITramiteVista iTramiteVista;

    TramitePresenter(Context contexto, ITramiteVista iTramiteVista) {
        this.contexto = contexto;
        this.iTramiteVista = iTramiteVista;
    }

    void validarExpedienteExistente() {
        Expediente expedienteRealm = DBHelperRealm.obtenerExpediente();
        if (expedienteRealm != null && expedienteRealm.getIdPeticion() != null) {
            int idPeticion = expedienteRealm.getIdPeticion();
            int idTramite = expedienteRealm.getIdTramite();
            int idSubtramite = expedienteRealm.getIdSubtramite();
            int posicionTramite = obtenerPosicionTramiteSeleccionado(idPeticion, idTramite);
            int posicionSubtramite = obtenerPosicionSubtramiteSeleccionado(idTramite, idSubtramite);
            iTramiteVista.precargarTramite(idPeticion, posicionTramite, posicionSubtramite);
        } else {
            iTramiteVista.precargarTramite(1, 0, 0);
        }
    }

    private int obtenerPosicionTramiteSeleccionado(int idPeticion, int idTramite) {
        ArrayList<Categoria> listaTramites = DBHelperRealm.obtenerTramites(idPeticion);
        for (int x = 0; x < listaTramites.size(); x++) {
            Categoria categoria = listaTramites.get(x);
            if (categoria.getId().equals(idTramite)) {
                return x;
            }
        }
        return 0;
    }

    private int obtenerPosicionSubtramiteSeleccionado(int idTramite, int idSubtramite) {
        ArrayList<Categoria> listaSubtramites = DBHelperRealm.obtenerSubtramites(idTramite);
        for (int x = 0; x < listaSubtramites.size(); x++) {
            Categoria categoria = listaSubtramites.get(x);
            if (categoria.getId().equals(idSubtramite)) {
                return x;
            }
        }
        return 0;
    }

    void guardarExpediente(String peticion, Categoria tramite, Categoria subtramite, List<Documento> documentos) {
        Expediente expediente = new Expediente();
        expediente.setIdTramite(tramite.getId());
        expediente.setTramite(tramite.getDescripcion());
        expediente.setIdSubtramite(subtramite.getId());
        expediente.setSubtramite(subtramite.getDescripcion());
        expediente.setIdParamEnvio(subtramite.getClave());
        expediente.setObservaciones("");

        expediente.setPeticion(peticion);
        switch (peticion) {
            case "TRÁMITE":
                expediente.setIdPeticion(1);
                break;
            case "QUEJA":
                expediente.setIdPeticion(2);
                break;
            case "INVESTIGACIÓN":
                expediente.setIdPeticion(3);
                break;
            default:
        }

        List<Digitalizacion> digitalizaciones = new ArrayList<>();
        for (Documento documento : documentos) {
            Digitalizacion digitalizacion = new Digitalizacion();
            digitalizacion.setDescripcion(documento.getDescripcion());
            digitalizacion.setAyuda(documento.getAyuda());
            digitalizacion.setEsObligatorio(documento.getEsObligatorio());
            digitalizacion.setIdParamEnvioProceso(documento.getIdParamEnvioProceso());
            digitalizacion.setTomarCaptura(0);
            digitalizacion.setCapturas(documento.getNumeroDocumentos());
            RealmList<Captura> capturas = new RealmList<>();
            for (int x = 0; x < documento.getNumeroDocumentos(); x++) {
                Captura captura = new Captura();
                captura.setNumero(x + 1);
                captura.setEstatus(0);
                captura.setIdParamEnvioProceso(documento.getIdParamEnvioProceso());
                captura.setIdTipoDoc(documento.getIdTipoDoc());
                captura.setDescripcion(documento.getDescripcion());
                captura.setCargaExitosa(0);
                capturas.add(captura);
            }
            digitalizacion.setCapturasArchivo(capturas);
            digitalizaciones.add(digitalizacion);
        }

        Digitalizacion observaciones = new Digitalizacion();
        observaciones.setDescripcion("OBSERVACIONES");
        observaciones.setAyuda("Comentarios de apoyo al trámite");
        observaciones.setCapturas(0);
        observaciones.setTomarCaptura(0);
        if (peticion.equals("QUEJA") || peticion.equals("INVESTIGACIÓN")) {
            observaciones.setEsObligatorio(1);
        } else {
            observaciones.setEsObligatorio(0);
        }
        digitalizaciones.add(observaciones);

        DBHelperRealm.insertarActualizarExpendiente(expediente);
        DBHelperRealm.insertarDigitalizaciones(digitalizaciones);
        ManejoArchivosHelper.limpiarArchivosDirectorio(Sesion.getInstancia(contexto).getPathMotor());
        iTramiteVista.iniciarDigitalizacion();
    }

    void obtenerFolioMIT(int idEnvio, int idTramite, int idTramiteSucursal) {
        Expediente expedienteRealm = DBHelperRealm.obtenerExpediente();
        boolean actualizarExpediente = false;

        if (expedienteRealm != null && expedienteRealm.getIdTramite() != null) {
            int idTramiteRealm = expedienteRealm.getIdTramite();
            int idSubtramiteRealm = expedienteRealm.getIdSubtramite();
            if (idTramiteRealm != idTramite || idSubtramiteRealm != idTramiteSucursal) {
                actualizarExpediente = true;
            }
        } else {
            actualizarExpediente = true;
        }

        if (actualizarExpediente) {
            Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
            String numeroEmpleado = Sesion.getInstancia(contexto).getNumeroEmpleado();
            Matcher matcher = numEmpleadoRegex.matcher(numeroEmpleado);
            numeroEmpleado = matcher.replaceFirst("");
            new FolioTramiteServicio(contexto, this).ejecutar(expedienteRealm,
                    solicitante,
                    numeroEmpleado,
                    idEnvio,
                    idTramiteSucursal);
        } else {
            iTramiteVista.iniciarDigitalizacion();
        }
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iTramiteVista.mostrarMensaje(titulo, mensaje);
    }

    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        JSONObject respuesta = jsonObjeto.optJSONObject("response");
        String folioTramite = respuesta.optString("folio_tramite");
        folioTramite = folioTramite.trim();

        /** Se agrega customKey para Crashalytics FOLIO **/
        TraceLog.getInstance().setIdProcess(folioTramite);

        DBHelperRealm.actualizarFolioMIT(folioTramite);
        iTramiteVista.obtenerFolioMitExitoso();
    }
}
