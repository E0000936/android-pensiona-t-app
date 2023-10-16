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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.busqueda.BusquedaActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.envio.EnvioMensajeActivity;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.DibujarFirmaVista;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * <h1>FirmaDosActivity</h1>
 * Clase utilizada para la captura de la firma del documento
 * Acuse de trámite de pensiones.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-06-15
 */
public class FirmaDosActivity extends MainActivity implements IFirmaVista, DibujarFirmaVista.IDibujarFirmaVista {
    private Button btnSiguiente;
    private ImageButton btnEliminar;
    private DibujarFirmaVista firmaVista;
    private Context contexto;
    private FirmaPresenter firmaPresenter;
    private TextView txtExcepcionTitulo;
    private TextView txtExcepcion;
    private ImageView imgFirmaGuardada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_firma);
        contexto = this;
        imgFirmaGuardada = findViewById(R.id.img_firma_guardada);
        txtExcepcionTitulo = findViewById(R.id.txt_excepcion_firma);
        txtExcepcion = findViewById(R.id.txt_excepcion_firma_causa);
        firmaPresenter = new FirmaPresenter(this, this);
        Button btnExcepciones = findViewById(R.id.btn_firma_excepcion);
        LinearLayout layoutContentenedor = findViewById(R.id.view_firma);
        firmaVista = new DibujarFirmaVista(this, null);
        firmaVista.setBackgroundColor(ContextCompat.getColor(contexto, R.color.colorTransparente));
        firmaVista.setDibujarFirmaDelegado(this);
        layoutContentenedor.addView(firmaVista, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnSiguiente = findViewById(R.id.btn_firma_continuar);
        btnEliminar = findViewById(R.id.btn_firma_eliminar);
        habilitarBotonContinuar(false);
        btnEliminar.setEnabled(false);
        ImageButton btnAtras = findViewById(R.id.btn_firma_atras);
        TextView nombreSolicitante = findViewById(R.id.txt_firma_solicitante);
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        String nombre = (solicitante.getNombre() != null) ? solicitante.getNombre() : "";
        String apPaterno = (solicitante.getApPaterno() != null) ? solicitante.getApPaterno() : "";
        String apMaterno = (solicitante.getApMaterno() != null) ? solicitante.getApMaterno() : "";
        nombreSolicitante.setText(nombre.concat(" " + apPaterno + " " + apMaterno));
        Button btnCancelarTramite = findViewById(R.id.btn_diobujarFirma_cancelar);
        btnCancelarTramite.setOnClickListener((View v) -> mostrarDialogoCancelar());
        btnSiguiente.setOnClickListener((View v) -> {
            btnEliminar.setEnabled(false);
            Progress.mostrar(this);
            guardarTramite();
            validarProgresoRegistro();
        });
        btnEliminar.setOnClickListener((View v) -> eliminarArchivosFirma());
        btnAtras.setOnClickListener((View v) -> {
            if (btnEliminar.isEnabled()) {
                guardarTramite();
            }

            finish();
        });
        recuperarFirmaGuardada();
        btnExcepciones.setEnabled(false);
        btnExcepciones.setBackground(getDrawable(R.drawable.fondo_boton_borde_gris));
        btnExcepciones.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorTextoSecondario));
    }

    private void recuperarFirmaGuardada() {
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.FIRMA_ACUSE_AZUL);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgFirmaGuardada.setImageBitmap(myBitmap);
            dibujarFirma(true);
            btnEliminar.setEnabled(true);
            imgFirmaGuardada.setVisibility(View.VISIBLE);
            habilitarBotonContinuar(true);
        } else {
            imgFirmaGuardada.setVisibility(View.GONE);
            habilitarBotonContinuar(false);
        }

        Expediente expediente = DBHelperRealm.obtenerExpediente();
        if (expediente.getExcepcion() != null && !expediente.getExcepcion().equals("")) {
            txtExcepcionTitulo.setVisibility(View.VISIBLE);
            txtExcepcion.setVisibility(View.VISIBLE);
            txtExcepcion.setText(expediente.getExcepcion());
            habilitarBotonContinuar(true);
        }
    }

    private void validarProgresoRegistro() {
        if (!VariablesGlobales.getInstance().registroExitoso) {
            firmaPresenter.registrarTramite(obtenerBase64Tramite(Constantes.Nombres.NOMBRE_FIRMA_FORM));
        } else if (!VariablesGlobales.getInstance().acuseExitoso) { //ejecutar acuse
            firmaPresenter.registrarAcuse(obtenerBase64Tramite(Constantes.Nombres.FIRMA_ACUSE));
        } else { //Carga de documento
            firmaPresenter.guadarDocumentos();
        }
    }

    private void eliminarArchivosFirma() {
        imgFirmaGuardada.setVisibility(View.GONE);
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.FIRMA_ACUSE_AZUL);
        File imgFile2 = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.FIRMA_ACUSE);
        if (imgFile.exists() && imgFile2.exists()) {
            imgFile.delete();
            imgFile2.delete();
        }
        firmaVista.limpiar();
    }

    private void guardarFirma(String nombre) {
        Bitmap signature = firmaVista.obtenerFirma();
        String rutaDosCompleta = Sesion.getInstancia(contexto).getPathPdf().concat(nombre);
        Bitmap newBitmapDos = Bitmap.createBitmap(signature.getWidth(), signature.getHeight(), signature.getConfig());
        Canvas canvasDos = new Canvas(newBitmapDos);
        if (nombre.contains("Azul")) {
            canvasDos.drawColor(ContextCompat.getColor(contexto, R.color.colorGrisPalido));
        } else {
            canvasDos.drawColor(Color.WHITE);
        }
        canvasDos.drawBitmap(signature, 0, 0, null);
        ManejoArchivosHelper.guardarArchivoFirma(rutaDosCompleta, newBitmapDos);
    }

    private void guardarTramite() {
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.FIRMA_ACUSE_AZUL);
        if (!imgFile.exists()) {
            guardarFirma(Constantes.Nombres.FIRMA_ACUSE_AZUL);
            guardarFirma(Constantes.Nombres.FIRMA_ACUSE);
        }
    }

    private String obtenerBase64Tramite(String nombre) {
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + nombre);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return "";
        }
    }

    private void habilitarBotonEliminar(boolean habilitar) {
        btnEliminar.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.ic_eliminar_azul) : getDrawable(R.drawable.ic_eliminar_gris);
        btnEliminar.setBackground(fondoBoton);
    }

    private void habilitarBotonContinuar(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.fondo_boton_azul) : getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    @Override
    public void registroTramiteExitoso() {
        firmaPresenter.registrarAcuse(obtenerBase64Tramite(Constantes.Nombres.FIRMA_ACUSE));
    }

    @Override
    public void registroAcuseExitoso() {
        firmaPresenter.guadarDocumentos();
    }

    @Override
    public void mostrarMensaje(String titulo, String mensaje) {
        Progress.ocultar();
        recuperarFirmaGuardada();
        if (mensaje != null) {
            mostrarErrorDialogo(mensaje);
        }
    }

    private void mostrarErrorDialogo(String mensaje) {
        String titulo = getString(R.string.acuse_error_carga);
        String confirmacion = getString(R.string.acuse_error_confirmacion);
        AlertaDialogo errorDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        errorDialogo.setDelegado(() -> {
            errorDialogo.dismiss();
            Progress.mostrar(this);
            validarProgresoRegistro();
        });
        errorDialogo.show(getSupportFragmentManager(), "");
    }

    @Override
    public void mostrarCargaCompleta() {
        Progress.ocultar();
        Intent next = new Intent(contexto, EnvioMensajeActivity.class);
        startActivity(next);
        finish();
    }

    @Override
    public void dibujarFirma(boolean existe) {
        habilitarBotonEliminar(existe);

        if (txtExcepcion.getVisibility() == View.VISIBLE) {
            habilitarBotonContinuar(true);
        } else {
            habilitarBotonContinuar(existe);
        }
    }

    public void mostrarDialogoCancelar() {
        AlertaDialogo cancelarDialogo = Utilidades.crearDialogoCancelar(contexto);
        cancelarDialogo.show(getSupportFragmentManager(), "");
        cancelarDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            Intent intent = new Intent(this, BusquedaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}