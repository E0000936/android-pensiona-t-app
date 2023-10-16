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
package com.mx.profuturo.android.pensionat.presentation.envio;

import android.content.Context;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.CorreoServicio;
import com.mx.profuturo.android.pensionat.data.external.web.volley.SmsServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;

import org.json.JSONObject;

import java.util.List;

/**
 * <h1>EnvioMensajePresenter</h1>
 * Clase que contiene la lógica correspondiente
 * al envió de mensajes de confirmación del trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-15
 */
public class EnvioMensajePresenter implements IServicioRespuesta {
    private Context contexto;
    private IEnvioMensajeVista iEnvioMensajeVista;

    EnvioMensajePresenter(Context contexto, IEnvioMensajeVista iEnvioMensajeVista) {
        this.contexto = contexto;
        this.iEnvioMensajeVista = iEnvioMensajeVista;
    }

    void enviarSms() {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        List<Telefono> telefonos = solicitante.getTelefonos();
        for (Telefono telefono : telefonos) {
            if (telefono.getTipo().equals(Constantes.TipoTelefono.MOVIL.name())) {
                new SmsServicio(contexto, this).ejecutar(telefono.getNumero(),
                        contexto.getString(R.string.finalizar_sms_mensaje, expediente.getFolioMit()));
                break;
            }
        }
    }

    void enviarCorreoTramite() {
        String nombreArchivo = Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.ARCHIVO_TRAMITE;
        String archivoContenido = ManejoArchivosHelper.obtenerBase64(nombreArchivo);
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        new CorreoServicio(contexto, this, Constantes.Nombres.ARCHIVO_TRAMITE).ejecutar(solicitante.getCorreo(), Constantes.Nombres.ARCHIVO_TRAMITE, archivoContenido, 1, expediente.getFolioMit());

    }

    private void enviarCorreoAcuse() {
        String nombreArchivo = Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.ARCHIVO_ACUSE;
        String archivoContenido = ManejoArchivosHelper.obtenerBase64(nombreArchivo);
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        new CorreoServicio(contexto, this, Constantes.Nombres.ARCHIVO_ACUSE).ejecutar(solicitante.getCorreo(), Constantes.Nombres.ARCHIVO_ACUSE, archivoContenido, 1, expediente.getFolioMit());
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iEnvioMensajeVista.errorEnvio(titulo, mensaje);
    }

    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        switch (tipoServicio) {
            case SMS:
                iEnvioMensajeVista.envioSmsExitoso(jsonObjeto.optString("telefono"));
                break;
            case CORREO:
                String nombreDoc = jsonObjeto.optString("nombreDoc");
                if (nombreDoc.equals(Constantes.Nombres.ARCHIVO_TRAMITE)) {
                    enviarCorreoAcuse();
                } else {
                    iEnvioMensajeVista.envioCorreoExitoso(jsonObjeto.optString("correo"));
                }
                break;
            default:
                iEnvioMensajeVista.errorEnvio(contexto.getString(R.string.error_conexion_titulo),
                        contexto.getString(R.string.error_conexion));
        }
    }
}
