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
import com.mx.profuturo.android.pensionat.presentation.dialogs.ExcepcionesDialogo;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.DibujarFirmaVista;

import java.io.File;

/**
 * <h1>FirmaActivity</h1>
 * Clase utilizada para la captura de la firma del documento
 * Solicitud identificación pensionado.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-06-03
 */
public class FirmaActivity extends MainActivity implements DibujarFirmaVista.IDibujarFirmaVista {

    private Button btnSiguiente;
    private ImageButton btnEliminar;
    private DibujarFirmaVista firmaVista;
    private Context contexto;
    private Button btnExcepciones;
    private TextView txtUnoExcepcionTitulo;
    private TextView txtUnoExcepcion;
    private ImageView imgUnoFirmaGuardada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_firma);
        contexto = this;
        imgUnoFirmaGuardada = findViewById(R.id.img_firma_guardada);
        txtUnoExcepcionTitulo = findViewById(R.id.txt_excepcion_firma);
        txtUnoExcepcion = findViewById(R.id.txt_excepcion_firma_causa);
        btnExcepciones = findViewById(R.id.btn_firma_excepcion);
        btnExcepciones.setOnClickListener((View v) -> mostrarModalExcepciones());
        LinearLayout layoutContentenedor = findViewById(R.id.view_firma);
        firmaVista = new DibujarFirmaVista(this, null);
        firmaVista.setBackgroundColor(ContextCompat.getColor(contexto, R.color.colorTransparente));
        firmaVista.setDibujarFirmaDelegado(this);
        layoutContentenedor.addView(firmaVista, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btnSiguiente = findViewById(R.id.btn_firma_continuar);
        btnEliminar = findViewById(R.id.btn_firma_eliminar);
        btnEliminar.setEnabled(false);
        ImageButton btnAtras = findViewById(R.id.btn_firma_atras);
        Button btnCancelarTramite = findViewById(R.id.btn_diobujarFirma_cancelar);
        btnCancelarTramite.setOnClickListener((View v) -> mostrarDialogoCancelar());
        btnSiguiente.setOnClickListener((View v) -> {
            guardarTramite();
            irAFormatoAcuse();
            btnEliminar.setEnabled(false);
        });
        btnEliminar.setOnClickListener((View v) -> eliminarArchivosFirma());
        btnAtras.setOnClickListener((View v) -> {
            guardarTramite();
            finish();
        });
        llenarNomreSolicitante();
        recuperarFirmaGuardada();
    }

    private void llenarNomreSolicitante() {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        TextView nombreSolicitante = findViewById(R.id.txt_firma_solicitante);
        String nombre = "";
        String apPaterno = "";
        String apMaterno = "";
        if (solicitante.getNombre() != null) {
            nombre = solicitante.getNombre();
        }
        if (solicitante.getApPaterno() != null) {
            apPaterno = solicitante.getApPaterno();
        }
        if (solicitante.getApMaterno() != null) {
            apPaterno = solicitante.getApMaterno();
        }
        nombreSolicitante.setText(nombre.concat(" " + apPaterno + " " + apMaterno));
    }

    private void recuperarFirmaGuardada() {
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.NOMBRE_FIRMA_FORM_AZUL);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgUnoFirmaGuardada.setImageBitmap(myBitmap);
            dibujarFirma(true);
            btnEliminar.setEnabled(true);
            imgUnoFirmaGuardada.setVisibility(View.VISIBLE);
            habilitarBotonContinuar(true);
        } else {
            imgUnoFirmaGuardada.setVisibility(View.GONE);
            habilitarBotonContinuar(false);
        }

        if (expediente.getExcepcion() != null && !expediente.getExcepcion().equals("")) {
            mostrarExcepcion(true, expediente.getExcepcion());
            habilitarBotonContinuar(true);
        }
    }

    private void eliminarArchivosFirma() {
        imgUnoFirmaGuardada.setVisibility(View.GONE);
        File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.NOMBRE_FIRMA_FORM_AZUL);
        File imgFile2 = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.NOMBRE_FIRMA_FORM);
        if (imgFile.exists() && imgFile2.exists()) {
            imgFile.delete();
            imgFile2.delete();
        }

        firmaVista.limpiar();
    }

    private void guardarFirma(String nombre) {
        Bitmap signature = firmaVista.obtenerFirma();
        String rutaCompleta = Sesion.getInstancia(contexto).getPathPdf().concat(nombre);
        Bitmap newBitmap = Bitmap.createBitmap(signature.getWidth(), signature.getHeight(), signature.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        if (nombre.contains("Azul")) {
            canvas.drawColor(ContextCompat.getColor(contexto, R.color.colorGrisPalido));
        } else {
            canvas.drawColor(Color.WHITE);
        }
        canvas.drawBitmap(signature, 0, 0, null);
        ManejoArchivosHelper.guardarArchivoFirma(rutaCompleta, newBitmap);
    }

    private void mostrarModalExcepciones() {
        ExcepcionesDialogo excepcionesDialogo = ExcepcionesDialogo.getInstancia();
        excepcionesDialogo.setDelegado((esExcepcion, excepcion) -> {
            if (esExcepcion) {
                habilitarBotonContinuar(true);
                mostrarExcepcion(true, excepcion);
            } else {
                if (!btnEliminar.isEnabled()) {
                    habilitarBotonContinuar(false);
                }
                mostrarExcepcion(false, "");
            }
        });
        excepcionesDialogo.show(getSupportFragmentManager(), "");
    }

    private void guardarTramite() {
        if (btnEliminar.isEnabled()) {
            File imgFile = new File(Sesion.getInstancia(contexto).getPathPdf() + Constantes.Nombres.NOMBRE_FIRMA_FORM_AZUL);
            if (!imgFile.exists()) {
                guardarFirma(Constantes.Nombres.NOMBRE_FIRMA_FORM_AZUL);
                guardarFirma(Constantes.Nombres.NOMBRE_FIRMA_FORM);
            }
        }
    }

    private void irAFormatoAcuse() {
        Intent intent = new Intent(this, FormatoAcuseActivity.class);
        startActivity(intent);
    }

    private void mostrarExcepcion(boolean mostrar, String excepcion) {
        txtUnoExcepcionTitulo.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        txtUnoExcepcion.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        txtUnoExcepcion.setText(excepcion);
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

    private void habilitarBotonExcepcion(boolean habilitar) {
        btnExcepciones.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.fondo_boton_borde_azul) : getDrawable(R.drawable.fondo_boton_borde_gris);
        btnExcepciones.setTextColor(habilitar ? ContextCompat.getColor(getApplicationContext(), R.color.colorPrimario) : ContextCompat.getColor(getApplicationContext(), R.color.colorTextoSecondario));
        btnExcepciones.setBackground(fondoBoton);
    }

    public void mostrarDialogoCancelar() {
        AlertaDialogo cancelarDialogoFirma = Utilidades.crearDialogoCancelar(contexto);
        cancelarDialogoFirma.show(getSupportFragmentManager(), "");
        cancelarDialogoFirma.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            Intent busquedaIntent = new Intent(this, BusquedaActivity.class);
            busquedaIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(busquedaIntent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarFirmaGuardada();
    }

    @Override
    public void dibujarFirma(boolean existe) {
        habilitarBotonEliminar(existe);
        if (txtUnoExcepcion.getVisibility() == View.GONE) {
            habilitarBotonExcepcion(!existe);
            habilitarBotonContinuar(existe);
        }
    }
}