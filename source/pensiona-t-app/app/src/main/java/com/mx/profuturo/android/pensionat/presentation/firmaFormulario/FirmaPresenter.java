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
package com.mx.profuturo.android.pensionat.presentation.firmaFormulario;

import android.content.Context;
import android.util.Base64;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.CargarDocumentosServicio;
import com.mx.profuturo.android.pensionat.data.external.web.volley.RegistroAcuseServicio;
import com.mx.profuturo.android.pensionat.data.external.web.volley.RegistroTramiteServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Checksum;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <h1>FirmaPresenter</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-26
 */
public class FirmaPresenter implements IServicioRespuesta {
    private static final Integer ID_ARCHIVO_TRAMITE = 1481;
    private static final Integer ID_ARCHIVO_ACUSE = 1480;
    private IFirmaVista iFirmaVista;
    private Context contexto;
    private String nombreDocumento;
    private int idParamEnvioProceso;

    FirmaPresenter(Context contexto, IFirmaVista iFirmaVista) {
        this.iFirmaVista = iFirmaVista;
        this.contexto = contexto;
    }

    void registrarTramite(String firma) {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        List<Digitalizacion> documentos = DBHelperRealm.obtenerDocumentosEntregados();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        new RegistroTramiteServicio(contexto, this).ejecutar(solicitante, documentos, expediente, firma);
    }

    void registrarAcuse(String firma) {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        List<Digitalizacion> documentos = DBHelperRealm.obtenerDocumentosEntregados();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        new RegistroAcuseServicio(contexto, this).ejecutar(solicitante,
                documentos,
                expediente,
                firma,
                obtenerFechaActual(),
                Sesion.getInstancia(contexto).getNumeroEmpleado());
    }

    private String obtenerFechaActual() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.forLanguageTag("es-ES"));
        return df.format(c);
    }

    void guadarDocumentos() {
        nombreDocumento = Constantes.Nombres.ARCHIVO_TRAMITE;
        enviarPDF(nombreDocumento);
    }

    private void obtenerDocumento() {
        Captura captura = DBHelperRealm.obtenerCaptura();
        if (captura != null) {
            nombreDocumento = captura.getRuta();
            idParamEnvioProceso = captura.getIdParamEnvioProceso();
            String nombre = captura.getNombre();
            String imagen = ManejoArchivosHelper.obtenerBase64(nombreDocumento);
            String md5 = Checksum.obtenerMD5(nombreDocumento);
            int totalCapturas = (int) DBHelperRealm.obtenerNumeroCapturas();
            int capturasTomadas = totalCapturas + 3;
            ejecutarCargarArchivo(captura, nombre, md5, imagen, capturasTomadas);
        } else {
            iFirmaVista.mostrarCargaCompleta();
        }
    }

    private void enviarPDF(String nombre) {
        String nombreArchivo = Sesion.getInstancia(contexto).getPathPdf() + nombre;
        String archivo = ManejoArchivosHelper.obtenerBase64(nombreArchivo);
        String md5 = Checksum.obtenerMD5(nombreArchivo);
        Captura captura = new Captura();
        int numero = 1;
        if (nombre.equals(Constantes.Nombres.ARCHIVO_TRAMITE)) {
            captura.setIdParamEnvioProceso(ID_ARCHIVO_TRAMITE);
            captura.setIdTipoDoc(ID_ARCHIVO_TRAMITE);
            captura.setNumero(0);
        } else {
            captura.setIdParamEnvioProceso(ID_ARCHIVO_ACUSE);
            captura.setIdTipoDoc(ID_ARCHIVO_ACUSE);
            captura.setNumero(1);
            numero = 2;
        }
        captura.setDescripcion(nombre);
        ejecutarCargarArchivo(captura, nombre, md5, archivo, numero);
    }

    private void ejecutarCargarArchivo(Captura captura, String nombre, String checksum, String base64, int numero) {
        int totalCapturas = (int) DBHelperRealm.obtenerTotalCapturas();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        new CargarDocumentosServicio(contexto, this).ejecutar(expediente, captura, nombre, base64, checksum, numero, totalCapturas);
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iFirmaVista.mostrarMensaje(titulo, mensaje);
    }

    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        switch (tipoServicio) {
            case REGISTRO:
                VariablesGlobales.getInstance().registroExitoso = true;
                String tramiteBase64 = jsonObjeto.optString("acuse_tramite");
                guardarPDF(tramiteBase64, Constantes.Nombres.ARCHIVO_TRAMITE);
                iFirmaVista.registroTramiteExitoso();
                break;
            case ACUSE:
                VariablesGlobales.getInstance().acuseExitoso = true;
                String acuseBase64 = jsonObjeto.optString("acuse_tramite");
                guardarPDF(acuseBase64, Constantes.Nombres.ARCHIVO_ACUSE);
                iFirmaVista.registroAcuseExitoso();
                break;
            case CARGAR:
                if (nombreDocumento.equals(Constantes.Nombres.ARCHIVO_TRAMITE)) {
                    nombreDocumento = Constantes.Nombres.ARCHIVO_ACUSE;
                    enviarPDF(nombreDocumento);
                } else {
                    DBHelperRealm.actualizarCargaExitosaCaptura(nombreDocumento, idParamEnvioProceso);
                    obtenerDocumento();
                }
                break;
            default:
                iFirmaVista.mostrarMensaje(contexto.getString(R.string.error_conexion_titulo),
                        contexto.getString(R.string.error_conexion));
                break;
        }
    }

    private void guardarPDF(String pdf, String nombreDocumento) {
        File directorio = new File(Sesion.getInstancia(contexto).getPathPdf());
        if (directorio.exists()) {
            File archivo = new File(directorio, nombreDocumento);
            try (FileOutputStream fileOutputStream = new FileOutputStream(archivo)) {
                byte[] pdfAsBytes = Base64.decode(pdf, 0);
                fileOutputStream.write(pdfAsBytes);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                iFirmaVista.mostrarMensaje(contexto.getString(R.string.error),
                        contexto.getString(R.string.error_guardar_pdf));
            }
        }
    }
}
