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
package com.mx.profuturo.android.pensionat.presentation.formulario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Referencia;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.busqueda.GenericoAdaptador;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.TimerCierreSesion;
import com.mx.profuturo.android.pensionat.utils.ValidadorFormulario;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;
import com.mx.profuturo.grupo.launchermau.core.rules.BusinessLine;
import com.mx.profuturo.grupo.launchermau.core.tags.MDCatalog;
import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;
import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;
import com.mx.profuturo.grupo.launchermau.data.local.ConfLauncher;
import com.mx.profuturo.grupo.launchermau.data.model.response_launcher.ResponseLauncher;
import com.mx.profuturo.grupo.launchermau.main.LauncherMAU;
import com.mx.profuturo.grupo.launchermau.main.builder.MauBuilder;
import com.mx.profuturo.grupo.launchermau.main.callback.OnResponseLauncher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.RealmList;

/**
 * <h1>ReferenciasFragment</h1>
 * Clase utilizada para la presentación y captura del formulario
 * de referencias personales.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class ReferenciasFragment extends Fragment
        implements SpinnerCaja.OnSpinnerEventsListener,
        AdapterView.OnItemSelectedListener {
    public ReferenciasInterfaz delegado;
    private TextInputEditText edtNombre;
    private TextInputLayout layoutNombre;
    private TextInputEditText edtApPaterno;
    private TextInputLayout layoutPaterno;
    private TextInputEditText edtApMaterno;
    private TextInputLayout layoutCasa;
    private TextInputEditText edtTelCasa;
    private TextInputLayout layoutCelular;
    private TextInputEditText edtCelular;
    private SpinnerCaja spParentesco;
    private TextInputEditText edtNombre2;
    private TextInputEditText edtApPaterno2;
    private TextInputEditText edtApMaterno2;
    private TextInputLayout layoutCasa2;
    private TextInputEditText edtTelCasa2;
    private TextInputLayout layoutCelular2;
    private TextInputEditText edtCelular2;
    private TextView txtSpinnerTitulo;
    private SpinnerCaja spParentesco2;
    private ArrayList<Categoria> listaParentescos;
    private Button btnSiguiente;
    private boolean limpiarErrores = false;
    private Context contexto;

    private LauncherMAU launcherMAU ;
    private DialogoPresenter dialogoPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contexto = container.getContext();
        listaParentescos = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.PARENTESCO);
        return inflater.inflate(R.layout.fragmento_referencias_personales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        edtNombre = view.findViewById(R.id.edt_referencias_nombre);
        edtNombre.addTextChangedListener(new validarCamposObligatorios());
        edtApPaterno = view.findViewById(R.id.edt_referencias_appaterno);
        edtApPaterno.addTextChangedListener(new validarCamposObligatorios());
        edtApMaterno = view.findViewById(R.id.edt_referencias_apmaterno);
        spParentesco = view.findViewById(R.id.spinner_referencias_parentesco);
        GenericoAdaptador adaptador = new GenericoAdaptador(getActivity(), listaParentescos);
        spParentesco.setAdapter(adaptador);
        spParentesco.setSelection(0);
        spParentesco.setOnItemSelectedListener(this);
        spParentesco.setSpinnerEventsListener(this);
        layoutCasa = view.findViewById(R.id.layout_referencias_telefono);
        edtTelCasa = view.findViewById(R.id.edt_referencias_telefono);
        edtTelCasa.addTextChangedListener(new validarCamposObligatorios());
        layoutCelular = view.findViewById(R.id.layout_referencias_celular);
        edtCelular = view.findViewById(R.id.edt_referencias_celular);
        edtCelular.addTextChangedListener(new validarCamposObligatorios());
        edtNombre2 = view.findViewById(R.id.edt_referencias_nombre2);
        layoutNombre = view.findViewById(R.id.layout_referencias_nombre2);
        edtNombre2.addTextChangedListener(new validarCamposObligatorios());
        edtApPaterno2 = view.findViewById(R.id.edt_referencias_appaterno2);
        layoutPaterno = view.findViewById(R.id.layout_referencias_appaterno2);
        edtApPaterno2.addTextChangedListener(new validarCamposObligatorios());
        edtApMaterno2 = view.findViewById(R.id.edt_referencias_apmaterno2);
        spParentesco2 = view.findViewById(R.id.spinner_referencias_parentesco2);
        GenericoAdaptador adaptador2 = new GenericoAdaptador(getActivity(), listaParentescos);
        spParentesco2.setAdapter(adaptador2);
        spParentesco2.setSelection(0);
        spParentesco2.setOnItemSelectedListener(this);
        spParentesco2.setSpinnerEventsListener(this);
        layoutCasa2 = view.findViewById(R.id.layout_referencias_telefono2);
        edtTelCasa2 = view.findViewById(R.id.edt_referencias_telefono2);
        edtTelCasa2.addTextChangedListener(new validarCamposObligatorios());
        layoutCelular2 = view.findViewById(R.id.layout_referencias_celular2);
        edtCelular2 = view.findViewById(R.id.edt_referencias_celular2);
        edtCelular2.addTextChangedListener(new validarCamposObligatorios());
        txtSpinnerTitulo = view.findViewById(R.id.textView_referencias_parentesco2);
        btnSiguiente = view.findViewById(R.id.btn_referencias_siguiente);
        btnSiguiente.setOnClickListener((View v) -> aplicarValidaciones());
        Button btnCancelar = view.findViewById(R.id.btn_referencias_cancelar);
        btnCancelar.setOnClickListener((View v) -> delegado.cancelarTramite());
        View viewPrincipal = view.findViewById(R.id.view_referencias);
        viewPrincipal.setOnClickListener((View v) -> esconderTeclado(viewPrincipal));
        rellenarFormulario(VariablesGlobales.getInstance().solicitante);

        //creating instance of launcher
        if (launcherMAU == null) launcherMAU = LauncherMAU.getInstance(requireActivity(), onResponseLauncher);
        if (dialogoPresenter == null) dialogoPresenter = new DialogoPresenter();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            validarHabiliarBotonSiguiente();
            obtenerValidaciones();
        }
    }

    private void rellenarFormulario(Solicitante solicitante) {
        if (solicitante.getReferencias() != null && edtNombre != null) {
            List<Referencia> referencias = solicitante.getReferencias();
            for (int x = 0; x < referencias.size(); x++) {
                Referencia referencia = referencias.get(x);
                if (referencia != null) {
                    llenarDatosNombre(referencia, x);
                    llenarDatosComplentarios(referencia, x);
                }
            }
        }
    }

    private void llenarDatosNombre(Referencia referencia, int x) {
        String nombre = "";
        String apMaterno = "";
        String apPaterno = "";

        if (referencia.getNombre() != null) {
            nombre = referencia.getNombre();
        }

        if (referencia.getApMaterno() != null) {
            apMaterno = referencia.getApMaterno();
        }
        if (referencia.getApPaterno() != null) {
            apPaterno = referencia.getApPaterno();
        }

        if (x == 0) {
            edtNombre.setText(nombre);
            edtApMaterno.setText(apMaterno);
            edtApPaterno.setText(apPaterno);
        } else {
            edtNombre2.setText(nombre);
            edtApMaterno2.setText(apMaterno);
            edtApPaterno2.setText(apPaterno);
        }
    }

    private void llenarDatosComplentarios(Referencia referencia, int x) {
        String telCasa = "";
        String telCelular = "";
        if (referencia.getTelefonoCasa() != null) {
            telCasa = referencia.getTelefonoCasa();
        }
        if (referencia.getCelular() != null) {
            telCelular = referencia.getCelular();
        }

        if (x == 0) {
            edtTelCasa.setText(telCasa);
            edtCelular.setText(telCelular);
        } else {
            edtTelCasa2.setText(telCasa);
            edtCelular2.setText(telCelular);
        }

        if (referencia.getIdParentesco() != null) {
            try {
                int idParentesco = Integer.parseInt(referencia.getIdParentesco());
                llenarParentesco(idParentesco, x);

            } catch (NumberFormatException nfe) {
                Log.d("NumberFormatException", nfe.getMessage());
            }
        }
    }

    private void llenarParentesco(int idParentesco, int x) {
        for (int y = 0; y < listaParentescos.size(); y++) {
            Categoria categoria = listaParentescos.get(y);
            int idCategoria = categoria.getClave();
            if (idCategoria == idParentesco) {
                if (x == 0) {
                    spParentesco.setSelection(y);
                } else {
                    spParentesco2.setSelection(y);
                }
                break;
            }
        }
    }

    private void aplicarValidaciones() {
        boolean esError = false;
        Drawable img = contexto.getResources().getDrawable(R.drawable.ic_alerta_roja);
        esError = validarTelCelular(esError, img);
        esError = validarTelCasa(esError, img);

        if (validarReferenciaPrincipal() && validarSegundaReferencia()) {
            esError = validarDatosReferenciaDos(esError, img);
            esError = validarTelefonoReferenciaDos(esError, img);
        }

        limpiarErrores = true;

        if (!esError) {
            guardarReferencias();
        }
    }

    private boolean validarTelCelular(Boolean esError, Drawable img) {
        if (edtCelular.getText() != null && !ValidadorFormulario.validarTelefono(edtCelular.getText().toString())) {
            esError = true;
            layoutCelular.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutCelular.setErrorEnabled(true);
            edtCelular.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }
        return esError;
    }

    private boolean validarTelCasa(Boolean esError, Drawable img) {
        if (edtTelCasa.getText() != null && !edtTelCasa.getText().toString().trim().isEmpty()) {
            if (!ValidadorFormulario.validarTelefono(edtTelCasa.getText().toString())) {
                esError = true;
                layoutCasa.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutCasa.setErrorEnabled(true);
                edtTelCasa.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            }
        }
        return esError;
    }

    private boolean validarReferenciaPrincipal() {
        if (edtNombre.getText() != null && edtApPaterno.getText() != null && edtCelular.getText() != null) {
            return !edtNombre.getText().toString().trim().isEmpty() &&
                    !edtApPaterno.getText().toString().trim().isEmpty() &&
                    !edtCelular.getText().toString().trim().isEmpty() &&
                    spParentesco.getSelectedItemPosition() != 0;
        }

        return false;
    }

    private boolean validarSegundaReferencia() {
        if (edtNombre2.getText() != null && edtApPaterno2.getText() != null && edtCelular2.getText() != null) {
            return !edtNombre2.getText().toString().trim().isEmpty() ||
                    !edtApPaterno2.getText().toString().trim().isEmpty() ||
                    !edtCelular2.getText().toString().trim().isEmpty() ||
                    spParentesco2.getSelectedItemPosition() != 0;
        }

        return false;
    }

    private boolean validarDatosReferenciaDos(Boolean esError, Drawable img) {
        if (spParentesco2.getSelectedItemPosition() == 0) {
            esError = true;
            spParentesco2.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde_rojo));
            txtSpinnerTitulo.setTextColor(ContextCompat.getColor(contexto, R.color.colorNaranja));
        }

        if (edtNombre2.getText() != null && edtNombre2.getText().toString().trim().isEmpty()) {
            esError = true;
            layoutNombre.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutNombre.setErrorEnabled(true);
            edtNombre2.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        if (edtApPaterno2.getText() != null && edtApPaterno2.getText().toString().trim().isEmpty()) {
            esError = true;
            layoutPaterno.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutPaterno.setErrorEnabled(true);
            edtApPaterno2.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        return esError;
    }

    private boolean validarTelefonoReferenciaDos(Boolean esError, Drawable img) {
        if (edtCelular2.getText() != null && edtCelular.getText() != null) {
            if (!ValidadorFormulario.validarTelefono(edtCelular2.getText().toString()) ||
                    edtCelular2.getText().toString().equals(edtCelular.getText().toString())) {
                esError = true;
                layoutCelular2.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutCelular2.setErrorEnabled(true);
                edtCelular2.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);

            }
        }

        if (edtTelCasa2.getText() != null && !edtTelCasa2.getText().toString().trim().isEmpty()) {
            if (!ValidadorFormulario.validarTelefono(edtTelCasa2.getText().toString())) {
                esError = true;
                layoutCasa2.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutCasa2.setErrorEnabled(true);
                edtTelCasa2.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            }
        }

        return esError;
    }

    private void limpiarError() {
        layoutCelular.setError("");
        edtCelular.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCasa.setError("");
        edtTelCasa.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCelular2.setError("");
        edtCelular2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCasa2.setError("");
        edtTelCasa2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        edtNombre2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutNombre.setError("");
        edtApPaterno2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutPaterno.setError("");
        spParentesco2.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
        txtSpinnerTitulo.setTextColor(ContextCompat.getColor(contexto, R.color.colorTextoElemento));
    }

    public void obtenerValidaciones() {
        int progreso = 0;
        if (edtNombre.getText() != null && !edtNombre.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (edtApPaterno.getText() != null && !edtApPaterno.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (edtCelular.getText() != null && !edtCelular.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (spParentesco.getSelectedItemPosition() != 0) {
            progreso += 1;
        }

        delegado.enviarProgreso(progreso, 1);
    }

    private void habilitarBotonSiguiente(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
        edtNombre2.setEnabled(habilitar);
        edtApPaterno2.setEnabled(habilitar);
        edtApMaterno2.setEnabled(habilitar);
        edtTelCasa2.setEnabled(habilitar);
        edtCelular2.setEnabled(habilitar);
    }

    private void validarHabiliarBotonSiguiente() {
        if (validarReferenciaPrincipal()) {
            habilitarBotonSiguiente(true);
        } else {
            habilitarBotonSiguiente(false);
        }
        if (limpiarErrores) {
            limpiarErrores = false;
            limpiarError();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((GenericoAdaptador) parent.getAdapter()).setPosicionSeleccionada(position);
        validarHabiliarBotonSiguiente();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onSpinnerOpened(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde_abierto));
    }

    @Override
    public void onSpinnerClosed(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
        validarHabiliarBotonSiguiente();
        obtenerValidaciones();
    }

    private void guardarReferencias() {
        Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
        RealmList<Referencia> referencias = new RealmList<Referencia>();
        Categoria categoria = listaParentescos.get(spParentesco.getSelectedItemPosition());
        Referencia referencia1 = new Referencia();
        referencia1.setNombre(edtNombre.getText().toString());
        referencia1.setApPaterno(edtApPaterno.getText().toString());
        referencia1.setApMaterno(edtApMaterno.getText().toString());
        referencia1.setTelefonoCasa(edtTelCasa.getText().toString());
        referencia1.setCelular(edtCelular.getText().toString());
        referencia1.setIdParentesco(categoria.getClave().toString());
        referencia1.setDescParentesco(categoria.getDescripcion());
        referencias.add(referencia1);

        if (validarReferenciaPrincipal() && validarSegundaReferencia()) {
            Categoria categoria2 = listaParentescos.get(spParentesco2.getSelectedItemPosition());
            Referencia referencia2 = new Referencia();
            referencia2.setNombre(edtNombre2.getText().toString());
            referencia2.setApPaterno(edtApPaterno2.getText().toString());
            referencia2.setApMaterno(edtApMaterno2.getText().toString());
            referencia2.setTelefonoCasa(edtTelCasa2.getText().toString());
            referencia2.setCelular(edtCelular2.getText().toString());
            referencia2.setIdParentesco(categoria2.getClave().toString());
            referencia2.setDescParentesco(categoria2.getDescripcion());
            referencias.add(referencia2);
        }
        solicitante.setReferencias(referencias);

        VariablesGlobales.getInstance().solicitante = solicitante;
        DBHelperRealm.insertarActualizarSolicitante(solicitante);
        if (VariablesGlobales.getInstance().folio) delegado.mostrarDatosBancarios();
        else startProcessMAU();
    }

    private void startProcessMAU() {
        Progress.mostrar(requireContext());
        Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
        String poliza = DBHelperRealm.obtenerExpediente().getPoliza();
        MauBuilder mauBuilder = MauBuilder.getInstance(
                solicitante.getCurp().trim().toUpperCase(),
                solicitante.getNombre().trim().toUpperCase(),
                solicitante.getApPaterno().trim().toUpperCase(),
                solicitante.getApMaterno().trim().toUpperCase(),
                poliza,
                MDCatalog.CATALOG_PROCEDURE_BASE,
                MDCatalog.CATALOG_PROCEDURE_BASE,
                TagsBuilder.ORIGIN_PENSIONAT,
                BusinessLine.ASEGURADORA_LINE,
                MDCatalog.TYPE_USER_CLIENT,
                MDCatalog.PROCESS_P_FORMULARIO,
                MDCatalog.SUBPROCESS_P_DATOS_PERSONALES
        );
        String idAdviser = Sesion.getInstancia(getContext()).getNumeroEmpleado();
        mauBuilder.setEmail_client(solicitante.getCorreo());
        mauBuilder.setPhone_client(getCelPhoneNumber(solicitante.getTelefonos()));
        mauBuilder.setId_adviser(idAdviser);
        mauBuilder.setTimeOutSession(TimerCierreSesion.getTimForCloseSession());
        launcherMAU.setMauBuilder(mauBuilder);
        launcherMAU.startMAU();
    }

    private String getCelPhoneNumber(@NonNull List<Telefono> telefonos) {
        for (Telefono telefono : telefonos) {
            if (telefono.getTipo() != null && telefono.getTipo().equals("MOVIL")) {
                return telefono.getNumero();
            }
        }
        return "";
    }

    private final OnResponseLauncher onResponseLauncher = new OnResponseLauncher() {
        @Override
        public void onError(String log, int code) {
            Progress.ocultar();
            TimerCierreSesion.setIsMauRunning(false);
            if (Objects.equals(log, TagResponse.MAU_BUILDER_NOT_FOUND))
                dialogoPresenter.mensajeAlerta(requireContext(), "Motor Autenticacion Universal", "No se ha podido crear la instancia");
            else if (Objects.equals(log, TagResponse.NO_MAU_INSTALLED))
                dialogoPresenter.mensajeAlerta(requireContext(), getString(R.string.error), getString(R.string.error_motor_mau));
            else
                dialogoPresenter.mensajeAlerta(requireContext(), "Motor Autenticacion Universal", "Ha ocurrido un error en la validación de los campos");
        }

        @Override
        public void onSuccess(@Nullable Intent intent) {
            Progress.ocultar();
            if (intent != null) {
                TimerCierreSesion.setIsMauRunning(true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultLauncher.launch(intent);
            }
        }

        @Override
        public void isSessionActive(boolean isSessionActive) { }
    };

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        TimerCierreSesion.setIsMauRunning(false);
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            String resultEngine = result.getData().getStringExtra(TagResponse.MAU_RESPONSE);
            ConfLauncher.setStringByTag(requireContext(), ConfLauncher.TAG_STATUS_ENROLMENT,resultEngine);
            delegado.mostrarDatosBancarios();
        } else if (result.getResultCode() == Activity.RESULT_CANCELED && result.getData() != null) {
            String resultEngine = result.getData().getStringExtra(TagResponse.MAU_RESPONSE);
            ResponseLauncher responseLauncher = new Gson().fromJson(resultEngine, ResponseLauncher.class);
            if (!Objects.isNull(responseLauncher) && responseLauncher.getStatus_engine().equals(TagResponse.SESSION_TIMEOUT)) {
                ((MainActivity) requireActivity()).cerrarSesion();
            }
        }
    });

    private void esconderTeclado(@NonNull View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    public interface ReferenciasInterfaz {
        void enviarProgreso(int progreso, int seccion);

        void cancelarTramite();

        void mostrarDatosBancarios();
    }

    private class validarCamposObligatorios implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //este metodo no se ocupa porque la validaciones las hacemos despues de que escribio el usuario
        }

        @Override
        public void afterTextChanged(Editable s) {
            obtenerValidaciones();
            validarHabiliarBotonSiguiente();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //este metodo no se ocupa porque la validaciones las hacemos despues de que escribio el usuario
        }
    }
}

