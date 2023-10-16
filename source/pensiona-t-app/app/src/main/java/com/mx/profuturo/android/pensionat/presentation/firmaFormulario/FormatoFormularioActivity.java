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
import android.widget.ImageButton;
import android.widget.TextView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Referencia;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.busqueda.BusquedaActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.formulario.ReferenciasFragment;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.TraceLog;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

import java.util.List;

/**
 * <h1>FormatoFormularioActivity</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-22
 */
public class FormatoFormularioActivity extends MainActivity {
    private static final String TAG = FormatoFormularioActivity.class.getSimpleName();
    public ReferenciasFragment.ReferenciasInterfaz delegado;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto = this;
        setContentView(R.layout.actividad_firma_formulario);
        Button btnExcepciones = findViewById(R.id.btnFirma);
        btnExcepciones.setOnClickListener((View v) -> mostrarActividad());
        TextView txtEmpleado = findViewById(R.id.tv_firma_numero_empleado);
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(this).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);
        Button btnCancelarTramite = findViewById(R.id.btn_firma_cancelar);
        btnCancelarTramite.setOnClickListener((View v) -> cancelarTramite());
        ImageButton imgAtras = findViewById(R.id.img_btn_firma_atras);
        imgAtras.setOnClickListener((View v) -> finish());
        rellenarFormulario(VariablesGlobales.getInstance().solicitante);
    }

    private void mostrarActividad() {
        Intent intent = new Intent(this, FirmaActivity.class);
        startActivity(intent);
    }

    private void rellenarFormulario(Solicitante solicitante) {
        String folioOferta = (solicitante.getFolioOferta() != null) ? solicitante.getFolioOferta() : "";
        actualizarRegimenFormato(folioOferta);
        datosSolicitante(solicitante);
        datosCurpSolicitante(solicitante);
        Domicilio domicilio = solicitante.getDomicilio();
        if (domicilio != null) {
            llenarDomicilioSolicitante(domicilio);
            llenarDomicilioDosSolicitante(domicilio);
        }
        llenarOcupacionNacionalidadSolicitante(solicitante);
        if (solicitante.getTelefonos() != null) {
            List<Telefono> telefonos = solicitante.getTelefonos();
            for (Telefono telefono : telefonos) {
                if (telefono.getTipo() != null) {
                    llenarDatosTelefono(telefono);
                }
            }
        }
        llenarDatosReferencias(solicitante);
    }

    private void llenarDatosReferencias(Solicitante solicitante) {
        if (solicitante.getReferencias() != null) {
            List<Referencia> referencias = solicitante.getReferencias();
            actualizarReferencias(referencias);
        }
    }

    private void actualizarReferencias(List<Referencia> referencias) {
        for (int x = 0; x < referencias.size(); x++) {
            Referencia referencia = referencias.get(x);
            if (referencia != null) {
                llenarNombreReferencia(referencia, x);
                llenarTelefonosReferencia(referencia, x);
            }
        }
    }

    private void llenarTelefonosReferencia(Referencia referencia, int posicion) {
        String celular = "";
        String casa = "";
        String parentesco = "";

        if (referencia.getCelular() != null) {
            celular = referencia.getCelular();
        }
        if (referencia.getTelefonoCasa() != null) {
            casa = referencia.getTelefonoCasa();
        }
        if (referencia.getDescParentesco() != null) {
            parentesco = referencia.getDescParentesco();
        }

        if (posicion == 0) {
            TextView tvReferenciaParentesco = findViewById(R.id.tvParentescoRealm);
            TextView tvReferenciaCelular = findViewById(R.id.tvTelefonoCelularReferenciaRealm);
            TextView tvReferenciaTelCasa = findViewById(R.id.tvTelefonoReferenciaRealm);
            tvReferenciaTelCasa.setText(casa);
            tvReferenciaCelular.setText(celular);
            tvReferenciaParentesco.setText(parentesco);
        } else {
            TextView tvReferenciaParentesco2 = findViewById(R.id.tvParentesco2Realm);
            TextView tvReferenciaTelCasa2 = findViewById(R.id.tvTelefonoReferencia2Realm);
            TextView tvReferenciaCelular2 = findViewById(R.id.tvTelefonoCasaReferencia2Realm);
            tvReferenciaTelCasa2.setText(casa);
            tvReferenciaCelular2.setText(celular);
            tvReferenciaParentesco2.setText(parentesco);
        }
    }

    private void llenarNombreReferencia(Referencia referencia, int posicion) {
        String nombre = "";
        String apPaterno = "";
        String apMaterno = "";

        if (referencia.getNombre() != null) {
            nombre = referencia.getNombre();
        }
        if (referencia.getApPaterno() != null) {
            apPaterno = referencia.getApPaterno();
        }
        if (referencia.getApMaterno() != null) {
            apMaterno = referencia.getApMaterno();
        }

        if (posicion == 0) {
            TextView tvReferenciaNombre = findViewById(R.id.tvNombreRealm);
            TextView tvReferenciaApPaterno = findViewById(R.id.tvApellidoRealm);
            TextView tvReferenciaApMaterno = findViewById(R.id.tvApellidoMaternoRealm);

            tvReferenciaNombre.setText(nombre);
            tvReferenciaApPaterno.setText(apPaterno);
            tvReferenciaApMaterno.setText(apMaterno);
        } else {
            TextView tvReferenciaNombre2 = findViewById(R.id.tvNombre2Realm);
            TextView tvReferenciaApPaterno2 = findViewById(R.id.tvApellido2Realm);
            TextView tvReferenciaApMaterno2 = findViewById(R.id.tvApellidoMaterno2Realm);
            tvReferenciaNombre2.setText(nombre);
            tvReferenciaApPaterno2.setText(apPaterno);
            tvReferenciaApMaterno2.setText(apMaterno);
        }

    }

    private void llenarDatosTelefono(Telefono telefono) {
        String disponibleDesde = "";
        String disponibleHasta = "";
        if (telefono.getDisponibleDesde() != null) {
            disponibleDesde = telefono.getDisponibleDesde();
        }
        if (telefono.getDisponibleHasta() != null) {
            disponibleHasta = telefono.getDisponibleHasta();
        }

        switch (telefono.getTipo()) {
            case "CASA":
                TextView tvTelCasa = findViewById(R.id.tvTelefonoRealm);
                TextView tvTelDesde = findViewById(R.id.tvDisponibleRealm);
                TextView tvTelHasta = findViewById(R.id.tvAhrRealm);
                tvTelCasa.setText(telefono.getNumero());
                tvTelDesde.setText(disponibleDesde);
                tvTelHasta.setText(disponibleHasta);
                break;
            case "MOVIL":
                TextView tvCelular = findViewById(R.id.tvTelefonoCelularRealm);
                TextView tvCelularDesde = findViewById(R.id.tvDisponible2Realm);
                TextView tvCelularHasta = findViewById(R.id.tvARealm);
                tvCelular.setText(telefono.getNumero());
                tvCelularDesde.setText(disponibleDesde);
                tvCelularHasta.setText(disponibleHasta);
                break;
            default:
                TextView tvOtro = findViewById(R.id.tvTelefonoOtroRealm);
                TextView tvOtroDesde = findViewById(R.id.tvDisponible3Realm);
                TextView tvOtroHasta = findViewById(R.id.tvA3Realm);
                tvOtro.setText(telefono.getNumero());
                tvOtroDesde.setText(disponibleDesde);
                tvOtroHasta.setText(disponibleHasta);
                break;
        }
    }

    private void llenarOcupacionNacionalidadSolicitante(Solicitante solicitante) {
        TextView tvNacionalidad = findViewById(R.id.tvNacionalidadRealm);
        TextView tvProfesion = findViewById(R.id.tvOcupacionRealm);
        if (solicitante.getOcupacion() != null) {
            tvProfesion.setText(solicitante.getOcupacion());
        }

        if (solicitante.getNacionalidad() != null) {
            tvNacionalidad.setText(solicitante.getNacionalidad());
        }
    }

    private void llenarDomicilioSolicitante(Domicilio domicilio) {
        TextView tvCiudad = findViewById(R.id.tvCiudadRealm);
        TextView tvCalle = findViewById(R.id.tvCalleRealm);
        TextView tvCp = findViewById(R.id.tvCpRealm);
        TextView tvNumero = findViewById(R.id.tvNoRealm);
        if (domicilio.getCalle() != null) {
            tvCalle.setText(domicilio.getCalle());
        }
        if (domicilio.getCp() != null) {
            tvCp.setText(domicilio.getCp());
        }
        if (domicilio.getCiudad() != null) {
            tvCiudad.setText(domicilio.getCiudad());
        }
        if (domicilio.getNumExt() != null) {
            tvNumero.setText(domicilio.getNumExt());
        }
    }

    private void llenarDomicilioDosSolicitante(Domicilio domicilio) {
        TextView tvEstado = findViewById(R.id.tvEntidadRealm);
        TextView tvMunicipio = findViewById(R.id.tvDelegacionRealm);
        TextView tvColonia = findViewById(R.id.tvColoniaRealm);
        TextView tvPais = findViewById(R.id.tvPaisRealm);
        if (domicilio.getMunicipio() != null) {
            tvMunicipio.setText(domicilio.getMunicipio());
        }
        if (domicilio.getPais() != null) {
            tvPais.setText(domicilio.getPais());
        }
        if (domicilio.getEstado() != null) {
            tvEstado.setText(domicilio.getEstado());
        }
        if (domicilio.getAsentamiento() != null) {
            tvColonia.setText(domicilio.getAsentamiento());
        }
    }

    private void datosCurpSolicitante(Solicitante solicitante) {
        TextView tvCorreo = findViewById(R.id.tvCorreoRealm);
        TextView tvEFirma = findViewById(R.id.tvEfirmaRealm);
        TextView tvCurp = findViewById(R.id.tvCurpRealm);
        TextView tvRfc = findViewById(R.id.tvRFCRealm);
        if (solicitante.getCorreo() != null) {
            tvCorreo.setText(solicitante.getCorreo());
        }
        if (solicitante.geteFirma() != null) {
            tvEFirma.setText(solicitante.geteFirma());
        }
        if (solicitante.getCurp() != null) {
            tvCurp.setText(solicitante.getCurp());
            TraceLog.getInstance().setCurpProcess(solicitante.getCurp());
        }
        if (solicitante.getRfc() != null) {
            tvRfc.setText(solicitante.getRfc());
        }
    }

    private void datosSolicitante(Solicitante solicitante) {
        TextView tvNombre = findViewById(R.id.tvNombres);
        TextView tvApPaterno = findViewById(R.id.tvApePaterno);
        TextView tvApMaterno = findViewById(R.id.tvApellidoMaterno);
        TextView tvFechaNacimiento = findViewById(R.id.tvFechaRealm);
        if (solicitante.getNombre() != null) {
            tvNombre.setText(solicitante.getNombre());
        }
        if (solicitante.getApPaterno() != null) {
            tvApPaterno.setText(solicitante.getApPaterno());
        }
        if (solicitante.getApMaterno() != null) {
            tvApMaterno.setText(solicitante.getApMaterno());
        }
        if (solicitante.getFechaNacimiento() != null) {
            tvFechaNacimiento.setText(solicitante.getFechaNacimiento());
        }
    }

    private void actualizarRegimenFormato(String folioOferta) {
        Expediente expediente = DBHelperRealm.obtenerExpediente();
        String nss = "";
        if (expediente.getNss() != null) {
            nss = expediente.getNss();
        }
        if (expediente.getRegimen() != null) {
            switch (expediente.getRegimen()) {
                case "IMSS":
                    TextView tvIdTipoRegimen = findViewById(R.id.id_tipo_regimen);
                    TextView tvFirmaNss = findViewById(R.id.tvfirma_nss);
                    TextView tvFirmaFolio = findViewById(R.id.tvfirma_folio);
                    tvIdTipoRegimen.setText(Constantes.Nombres.VALOR_SI);
                    tvFirmaNss.setText(nss);
                    tvFirmaFolio.setText(folioOferta);
                    break;
                case "ISSSTE":
                    TextView tvIdTipoRegimen2 = findViewById(R.id.id_tipo_regimen2);
                    TextView tvFirmaNss2 = findViewById(R.id.tvfirma_nss2);
                    TextView tvFirmaFolio2 = findViewById(R.id.tvfirma_folio2);
                    tvIdTipoRegimen2.setText(Constantes.Nombres.VALOR_SI);
                    tvFirmaNss2.setText(nss);
                    tvFirmaFolio2.setText(folioOferta);
                    break;
                default:
                    break;
            }
        }
    }

    private void cancelarTramite() {
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
}




