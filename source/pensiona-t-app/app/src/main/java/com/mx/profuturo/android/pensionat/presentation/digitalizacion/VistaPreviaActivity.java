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
package com.mx.profuturo.android.pensionat.presentation.digitalizacion;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;

import java.io.File;

/**
 * <h1>VistaPreviaActivity</h1>
 * Clase de preevisulización del documento capturado
 * con la opción de eliminar el recurso.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-16
 */
public class VistaPreviaActivity extends MainActivity {
    private String nombreDocumento;
    private Integer numeroCaptura;
    private String descDocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_vista_previa);
        WebView documento = findViewById(R.id.webview_vista_previa_documento);
        documento.setPadding(0, 0, 0, 0);
        documento.getSettings().setLoadWithOverviewMode(true);
        documento.getSettings().setUseWideViewPort(true);
        documento.getSettings().setBuiltInZoomControls(true);
        documento.getSettings().setJavaScriptEnabled(false);
        documento.getSettings().setAllowFileAccessFromFileURLs(false);
        documento.getSettings().setAllowUniversalAccessFromFileURLs(false);

        ImageButton btnAtras = findViewById(R.id.img_vista_previa_atras);
        btnAtras.setOnClickListener((View v) -> regresarDigitalizacion());
        ImageButton btnEliminar = findViewById(R.id.img_vista_previa_eliminar);
        btnEliminar.setOnClickListener((View v) -> mostrarDialogoEliminar());

        if (getIntent().getExtras() != null) {
            nombreDocumento = getIntent().getExtras().getString("nombreDocumento");
            numeroCaptura = getIntent().getExtras().getInt("numeroCaptura");
            descDocumento = getIntent().getExtras().getString("descDocumento");
            documento.loadUrl(nombreDocumento);
        }
    }

    private void regresarDigitalizacion() {
        super.onBackPressed();
    }

    private void mostrarDialogoEliminar() {
        String titulo = getApplicationContext().getString(R.string.digitalizacion_eliminar_captura_titulo);
        String mensaje = getApplicationContext().getString(R.string.digitalizacion_eliminar_captura_detalle);
        String confirmacion = getApplicationContext().getString(R.string.digitalizacion_eliminar_captura_confirmacion);
        AlertaDialogo eliminarDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        eliminarDialogo.setDelegado(() -> {
            File archivo1 = new File(nombreDocumento);
            boolean exitoso = archivo1.delete();
            DBHelperRealm.eliminarImagenCaptura(descDocumento, numeroCaptura);
            regresarDigitalizacion();
        });
        if (getSupportFragmentManager() != null) {
            eliminarDialogo.show(getSupportFragmentManager(), "");
        }
    }
}