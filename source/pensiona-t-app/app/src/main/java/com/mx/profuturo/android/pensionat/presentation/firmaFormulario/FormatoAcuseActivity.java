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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.busqueda.BusquedaActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.Utilidades;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <h1>FormatoAcuseActivity</h1>
 * Clase utilizada para la captura de la firma del documento
 * Acuse formato pensionado.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-31
 */
public class FormatoAcuseActivity extends MainActivity {
    private static final String TAG = FormatoAcuseActivity.class.getSimpleName();
    private Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto = this;
        setContentView(R.layout.actividad_formulario_acuse);
        ConstraintLayout btnFirma = findViewById(R.id.btn_acuse_firma);
        btnFirma.setOnClickListener((View v) -> mostrarVistaFirma());
        TextView txtEmpleado = findViewById(R.id.txt_tramite_numero_empleado);
        TextView txtEmpleadoFirma = findViewById(R.id.txt_acuse_numero_empleado);
        TextView txtFecha = findViewById(R.id.txt_acuse_fecha);
        TextView txtTitular = findViewById(R.id.txt_acuse_nombre);
        Button btnCancelarTramite = findViewById(R.id.btn_acuse_cancelar);
        btnCancelarTramite.setOnClickListener((View view) -> mostrarDialogoCancelar());
        txtEmpleado.setText(String.format("Empleado %s", Sesion.getInstancia(getApplication()).getNumeroEmpleado()));
        txtEmpleadoFirma.setText(Sesion.getInstancia(getApplication()).getNumeroEmpleado());
        txtTitular.setText(DBHelperRealm.obtenerTitularPoliza());
        ImageButton imgAtras = findViewById(R.id.img_btn_acuse_atras);
        imgAtras.setOnClickListener((View v) -> finish());
        txtFecha.setText(obtenerFechaActual());
        llenarDatosSolicitante();
        llenarDatosExpediente();
        llenarFormatoDocumentos();
    }

    private void llenarDatosExpediente() {
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        EditText txtComentarios = findViewById(R.id.txt_acuse_comentarios);
        TextView txtFolio = findViewById(R.id.txt_acuse_folio);
        TextView txtNSS = findViewById(R.id.txt_acuse_nss);
        TextView txtPoliza = findViewById(R.id.txt_acuse_poliza);
        TextView txtGrupoPago = findViewById(R.id.txt_acuse_grupo_pago);
        TextView txtTipoTramiteSolicitado = findViewById(R.id.txt_acuse_tramite);
        TextView txtParentesco = findViewById(R.id.txt_acuse_parentesco);
        txtComentarios.setFocusable(false);
        txtFolio.setText(expediente.getFolioMit());
        txtTipoTramiteSolicitado.setText(expediente.getTramite());

        if (expediente.getParentescoSolicitante() != null) {
            txtParentesco.setText(expediente.getParentescoSolicitante());
        } else {
            txtParentesco.setText(Constantes.Nombres.DESC_PARENTESCO);
        }
        if (expediente.getPoliza() != null) {
            txtPoliza.setText(expediente.getPoliza());
        }
        if (expediente.getNss() != null) {
            txtNSS.setText(expediente.getNss());
        }
        if (expediente.getGrupoPago() != null) {
            txtGrupoPago.setText(String.valueOf(expediente.getGrupoPago()));
        }
        String comentarios = expediente.getObservaciones();
        if (expediente.getExcepcion() != null) {
            comentarios = comentarios.concat(" " + expediente.getExcepcion());
        }
        txtComentarios.setText(comentarios);
    }

    private void llenarFormatoDocumentos() {
        List<Digitalizacion> documentos = DBHelperRealm.obtenerDocumentosEntregados();
        DocumentosAcuseAdapter adapter = new DocumentosAcuseAdapter(documentos);
        RecyclerView recyclerDocumentos = findViewById(R.id.recycler_acuse_documentos);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerDocumentos.setLayoutManager(llm);
        recyclerDocumentos.setAdapter(adapter);
    }

    private void llenarDatosSolicitante() {
        Solicitante solicitante = DBHelperRealm.obtenerSolicitante();
        TextView txtSolicitante = findViewById(R.id.txt_acuse_solicitante);
        TextView txtNombreSolicitante = findViewById(R.id.txt_acuse_nombre_solicitante);
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
            apMaterno = solicitante.getApMaterno();
        }

        String nombreCompleto = nombre.concat(" " + apPaterno + " " + apMaterno);
        txtSolicitante.setText(nombreCompleto);
        txtNombreSolicitante.setText(nombreCompleto);

        for (int x = 0; x < solicitante.getTelefonos().size(); x++) {
            Telefono telefono = solicitante.getTelefonos().get(x);
            if (telefono != null) {
                llenarFormatoTelefono(telefono, x);
            }
        }
    }

    private void llenarFormatoTelefono(Telefono telefono, int posicion) {
        String horario = "";
        if (telefono.getDisponibleDesde() != null) {
            horario = telefono.getDisponibleDesde();
        }
        if (telefono.getDisponibleHasta() != null) {
            horario = horario.concat(" " + telefono.getDisponibleHasta());
        }

        if (posicion == 0) {
            TextView txtTelTipo1 = findViewById(R.id.txt_acuse_tipo_telefono_uno);
            TextView txtTel1 = findViewById(R.id.txt_acuse_numero_uno);
            TextView txtTel1Hor = findViewById(R.id.txt_acuse_localizacion_uno);

            txtTelTipo1.setText(telefono.getTipo());
            txtTel1.setText(telefono.getNumero());
            txtTel1Hor.setText(horario);
        } else if (posicion == 1) {
            TextView txtTelTipo2 = findViewById(R.id.txt_acuse_tipo_telefono_dos);
            TextView txtTel2 = findViewById(R.id.txt_acuse_numero_dos);
            TextView txtTel2Hor = findViewById(R.id.txt_acuse_localizacion_dos);

            txtTelTipo2.setText(telefono.getTipo());
            txtTel2.setText(telefono.getNumero());
            txtTel2Hor.setText(horario);
        } else {
            TextView txtTelTipo3 = findViewById(R.id.txt_acuse_tipo_telefono_tres);
            TextView txtTel3 = findViewById(R.id.txt_acuse_numero_tres);
            TextView txtTel3Hor = findViewById(R.id.txt_acuse_localizacion_tres);

            txtTelTipo3.setText(telefono.getTipo());
            txtTel3.setText(telefono.getNumero());
            txtTel3Hor.setText(horario);
        }
    }

    private void mostrarVistaFirma() {
        Intent intent = new Intent(this, FirmaDosActivity.class);
        startActivity(intent);
    }

    private String obtenerFechaActual() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.forLanguageTag("es-ES"));
        return df.format(c);
    }

    private void mostrarDialogoCancelar() {
        AlertaDialogo cancelarDialogo = Utilidades.crearDialogoCancelar(contexto);
        cancelarDialogo.show(getSupportFragmentManager(), TAG);
        cancelarDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            Intent intent = new Intent(this, BusquedaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}
