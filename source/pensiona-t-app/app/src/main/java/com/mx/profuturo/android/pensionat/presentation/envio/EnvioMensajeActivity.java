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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.busqueda.BusquedaActivity;
import com.mx.profuturo.android.pensionat.presentation.busqueda.PrincipalActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.ConfirmacionDialogo;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.login.LoginActivity;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;

import java.io.File;

/**
 * <h1>EnvioMensajeActivity</h1>
 *
 * @author Jose Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-03-21
 */
public class EnvioMensajeActivity extends MainActivity implements IEnvioMensajeVista {
    private Button btnOtroTramite;
    private CardView cardViewCorreo, cardViewSms;
    private TextView tvSMS;
    private ImageView imgCorreo;
    private EnvioMensajePresenter envioMensajePresenter;
    private TextView txtSolicitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_envio_mensaje);
        TextView txtEmpleado = findViewById(R.id.tv_firma_numero_empleado);
        txtEmpleado.setText(String.format("Empleado %s", Sesion.getInstancia(getApplication()).getNumeroEmpleado()));
        envioMensajePresenter = new EnvioMensajePresenter(getApplicationContext(), this);
        txtSolicitante = findViewById(R.id.tv_nombre_solicitante);
        mostrarNombreSolicitante();
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        TextView txtTramite = findViewById(R.id.tv_folio_tramite);
        txtTramite.setText(getString(R.string.finalizar_folio, expediente.getFolioMit()));
        TextView txtNumCapturas = findViewById(R.id.tv_capturas);
        txtNumCapturas.setText(getString(R.string.finalizar_numero_capturas, DBHelperRealm.obtenerNumeroCapturas()));
        ImageButton btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        btnCerrarSesion.setOnClickListener((View v) -> mostrarDialogoCerrarSesion());
        Button btnFinalizar = findViewById(R.id.btn_finalizar);
        btnFinalizar.setOnClickListener((View v) -> mostrarBusquedaCliente());
        btnOtroTramite = findViewById(R.id.btn_envio_mensaje_otrotramite);
        btnOtroTramite.setOnClickListener((View v) -> irASeleccionComponente());
        CardView cardViewIdentificacion = findViewById(R.id.cardView_identificacion_cliente);
        CardView cardVieTramitePensiones = findViewById(R.id.cardView_tramite_pensiones);
        cardViewCorreo = findViewById(R.id.cardView_correo);
        cardViewCorreo.setOnClickListener((View v) -> {
            cardViewCorreo.setEnabled(false);
            Progress.mostrar(this);
            envioMensajePresenter.enviarCorreoTramite();
        });
        cardViewSms = findViewById(R.id.cardView_sms);
        cardViewSms.setOnClickListener((View v) -> {
            cardViewSms.setEnabled(false);
            Progress.mostrar(this);
            envioMensajePresenter.enviarSms();
        });
        tvSMS = findViewById(R.id.tv_sms);
        imgCorreo = findViewById(R.id.img_correo);

        cardViewIdentificacion.setOnClickListener((View v) -> abrirArchivo(Constantes.Nombres.ARCHIVO_TRAMITE));
        cardVieTramitePensiones.setOnClickListener((View v) -> abrirArchivo(Constantes.Nombres.ARCHIVO_ACUSE));
        if (VariablesGlobales.getInstance().folio) {
            habilitarBotonOtroTramite(false);
        } else {
            habilitarBotonOtroTramite(true);
        }
        habilitarCardView(validarCorreo());
    }

    private void mostrarNombreSolicitante() {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        String nombre = (solicitante.getNombre() != null) ? solicitante.getNombre() : "";
        String apPaterno = (solicitante.getApPaterno() != null) ? solicitante.getApPaterno() : "";
        String apMaterno = (solicitante.getApMaterno() != null) ? solicitante.getApMaterno() : "";
        txtSolicitante.setText(nombre.concat(" " + apPaterno + " " + apMaterno));
    }

    private void irASeleccionComponente() {
        Utilidades.limpiarDatosBusqueda(getApplicationContext());
        Intent intent = new Intent(EnvioMensajeActivity.this, PrincipalActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean validarCorreo() {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        return (solicitante.getCorreo() != null && !solicitante.getCorreo().isEmpty());
    }

    private void abrirArchivo(String nombreArchivo) {
        File file = new File(Sesion.getInstancia(getApplicationContext()).getPathPdf().concat(nombreArchivo));
        if (file.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            Uri pdfPath = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
            target.setDataAndType(pdfPath, "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intent = Intent.createChooser(target, "Open File");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }
        }
    }

    public void mostrarBusquedaCliente() {
        Utilidades.limpiarDatosTramite(getApplicationContext());
        Intent next = new Intent(this, BusquedaActivity.class);
        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(next);
        finish();
    }

    private void mostrarDialogoCerrarSesion() {
        AlertaDialogo cerrarSesionDialogo = new AlertaDialogo();
        cerrarSesionDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(getApplicationContext());
            Sesion.getInstancia(getApplicationContext()).limpiarSesion();
            Intent next = new Intent(this, LoginActivity.class);
            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(next);
            finish();
        });
        cerrarSesionDialogo.show(getSupportFragmentManager(), "");
    }

    public void habilitarBotonOtroTramite(boolean habilitar) {
        btnOtroTramite.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.fondo_boton_borde_azul) : getDrawable(R.drawable.fondo_boton_borde_gris);
        btnOtroTramite.setTextColor(habilitar ? ContextCompat.getColor(getApplicationContext(), R.color.colorPrimario) : ContextCompat.getColor(getApplicationContext(), R.color.colorTextoSecondario));
        btnOtroTramite.setBackground(fondoBoton);
    }

    public void habilitarCardView(boolean habilitar) {
        cardViewCorreo.setEnabled(habilitar);
        Drawable fondoImagen = habilitar ? getDrawable(R.drawable.ic_icn_enviar_msj) : getDrawable(R.drawable.ic_correo_disabled);
        tvSMS.setTextColor(habilitar ? ContextCompat.getColor(getApplicationContext(), R.color.colorGrisMedio) : ContextCompat.getColor(getApplicationContext(), R.color.colorTextoSecondario));
        imgCorreo.setImageDrawable(fondoImagen);
        cardViewCorreo.setCardElevation(habilitar ? 15 : 0);
    }

    @Override
    public void envioSmsExitoso(String telefono) {
        Progress.ocultar();
        cardViewSms.setEnabled(true);
        ConfirmacionDialogo confirmacionDialogo = ConfirmacionDialogo.getInstancia(telefono, "");
        confirmacionDialogo.show(getSupportFragmentManager(), "");
    }

    @Override
    public void envioCorreoExitoso(String correo) {
        Progress.ocultar();
        cardViewCorreo.setEnabled(true);
        ConfirmacionDialogo confirmacionDialogo = ConfirmacionDialogo.getInstancia("", correo);
        confirmacionDialogo.show(getSupportFragmentManager(), "");
    }

    @Override
    public void errorEnvio(String titulo, String mensaje) {
        Progress.ocultar();
        if (mensaje != null) {
            DialogoPresenter dialogoPresenter = new DialogoPresenter();
            dialogoPresenter.mensajeAlerta(this, titulo, mensaje);
        }
        habilitarCardView(validarCorreo());
        cardViewSms.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // Cuando presiona el botón atrás en el dispositivo,
        // se llama a esta función y lo lleva a la actividad anterior realizada
        // llamando a la función de superclase, es decir, super.onBackPressed ().
        // Si no se llama a esta función, no ocurrirá nada, por lo tanto,
        // si desea deshabilitar la contrapresión, anule el onBackPressed ()
        // y comente la llamada a la superclase.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utilidades.limpiarDatosTramite(getApplicationContext());
    }
}
