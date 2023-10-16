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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.CodigoPostal;
import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.Colonia;
import com.mx.profuturo.android.pensionat.presentation.busqueda.GenericoAdaptador;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.DbHelperEstados;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.ValidadorFormulario;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * <h1>DatosPersonalesFragment</h1>
 * Clase utilizada para la presentación y captura del formulario
 * de datos personales.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class DatosPersonalesFragment extends Fragment
        implements View.OnClickListener,
        SpinnerCaja.OnSpinnerEventsListener,
        AdapterView.OnItemSelectedListener {

    private static final Integer ID_MEXICO = 141;
    public FormularioGeneralesInterfaz delegado;
    private TextInputEditText edtNombre;
    private TextInputEditText edtApPaterno;
    private TextInputEditText edtApMaterno;
    private TextInputEditText edtFechaNacimiento;
    private SpinnerCaja spNacionalidad;
    private SpinnerCaja spPais;
    private SpinnerCaja spProfesion;
    private TextInputEditText edtCalle;
    private TextInputEditText edtNumero;
    private TextInputEditText edtNumeroInt;
    private TextInputLayout layoutCp;
    private TextInputEditText edtCp;
    private TextInputEditText edtMunicipio;
    private TextInputEditText edtCiudad;
    private TextInputEditText edtEstado;
    private SpinnerCaja spColonia;
    private TextInputLayout layoutCurp;
    private TextInputEditText edtCurp;
    private TextInputLayout layoutRfc;
    private TextInputEditText edtRfc;
    private TextInputEditText edtEFirma;
    private TextInputLayout layoutCasa;
    private TextInputEditText edtTelCasa;
    private TextInputEditText edtTelDesde;
    private TextInputEditText edtTelHasta;
    private TextInputLayout layoutCelular;
    private TextInputEditText edtCelular;
    private TextInputEditText edtCelularDesde;
    private TextInputEditText edtCelularHasta;
    private TextInputLayout layoutOtro;
    private TextInputEditText edtOtro;
    private TextInputEditText edtOtroDesde;
    private TextInputEditText edtOtroHasta;
    private TextInputLayout layoutCorreo;
    private TextInputEditText edtCorreo;
    private Button btnSiguiente;
    private boolean limpiarErrores = false;
    private ArrayList<Categoria> listaPaises;
    private ArrayList<Categoria> listaProfesiones;
    private ArrayList<Categoria> listaNacionalidad;
    private ArrayList<Categoria> listaColoniasLaboral;
    private Context contexto;
    private CodigoPostal objCodigoPostal;
    private View vistaPrincipal;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contexto = container.getContext();
        listaPaises = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.PAIS);
        listaProfesiones = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.PROFESION);
        listaNacionalidad = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.NACIONALIDAD);
        return inflater.inflate(R.layout.fragmento_datos_generales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        edtNombre = view.findViewById(R.id.edt_formulario_nombre);
        edtNombre.addTextChangedListener(new validarCamposObligatorios());
        edtApPaterno = view.findViewById(R.id.edt_formulario_appaterno);
        edtApPaterno.addTextChangedListener(new validarCamposObligatorios());
        edtApMaterno = view.findViewById(R.id.edt_formulario_apmaterno);
        edtFechaNacimiento = view.findViewById(R.id.edt_formulario_nacimiento);
        edtFechaNacimiento.setOnClickListener(this);
        edtFechaNacimiento.addTextChangedListener(new validarCamposObligatorios());
        spNacionalidad = view.findViewById(R.id.spinner_formulario_nacionalidad);
        spNacionalidad.setSpinnerEventsListener(this);
        spPais = view.findViewById(R.id.spinner_formulario_pais);
        spPais.setSpinnerEventsListener(this);
        spProfesion = view.findViewById(R.id.spinner_formulario_profesion);
        spProfesion.setSpinnerEventsListener(this);

        edtCalle = view.findViewById(R.id.edt_formulario_calle);
        edtCalle.addTextChangedListener(new validarCamposObligatorios());
        edtNumero = view.findViewById(R.id.edt_formulario_numero);
        edtNumero.addTextChangedListener(new validarCamposObligatorios());
        edtNumeroInt = view.findViewById(R.id.edt_formulario_numero_interior);
        layoutCp = view.findViewById(R.id.layout_formulario_cp);
        edtCp = view.findViewById(R.id.edt_formulario_cp);
        edtCp.addTextChangedListener(new validarCamposObligatorios());
        edtCp.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (edtCp.getText() != null) {
                leerDireccion(edtCp.getText().toString());
            }
        });
        edtMunicipio = view.findViewById(R.id.edt_formulario_municipio);
        edtMunicipio.addTextChangedListener(new validarCamposObligatorios());
        edtCiudad = view.findViewById(R.id.edt_formulario_ciudad);
        edtCiudad.addTextChangedListener(new validarCamposObligatorios());
        edtEstado = view.findViewById(R.id.edt_formulario_estado);
        edtEstado.addTextChangedListener(new validarCamposObligatorios());
        spColonia = view.findViewById(R.id.spinner_formulario_colonia);
        spColonia.setSpinnerEventsListener(this);

        layoutCurp = view.findViewById(R.id.layout_formulario_curp);
        edtCurp = view.findViewById(R.id.edt_formulario_curp);
        edtCurp.addTextChangedListener(new validarCamposObligatorios());
        layoutRfc = view.findViewById(R.id.layout_formulario_rfc);
        edtRfc = view.findViewById(R.id.edt_formulario_rfc);
        edtRfc.addTextChangedListener(new validarCamposObligatorios());
        edtEFirma = view.findViewById(R.id.edt_formulario_efirma);

        layoutCasa = view.findViewById(R.id.layout_formulario_telefono);
        edtTelCasa = view.findViewById(R.id.edt_formulario_telefono);
        edtTelCasa.addTextChangedListener(new validarCamposObligatorios());
        edtTelDesde = view.findViewById(R.id.edt_formulario_ddtelefono);
        edtTelDesde.setOnClickListener(this);
        edtTelHasta = view.findViewById(R.id.edt_formulario_dhtelefono);
        edtTelHasta.setOnClickListener(this);

        layoutCelular = view.findViewById(R.id.layout_formulario_celular);
        edtCelular = view.findViewById(R.id.edt_formulario_celular);
        edtCelular.addTextChangedListener(new validarCamposObligatorios());
        edtCelularDesde = view.findViewById(R.id.edt_formulario_ddcelular);
        edtCelularDesde.setOnClickListener(this);
        edtCelularHasta = view.findViewById(R.id.edt_formulario_dhcelular);
        edtCelularHasta.setOnClickListener(this);

        layoutOtro = view.findViewById(R.id.layout_formulario_otro);
        edtOtro = view.findViewById(R.id.edt_formulario_otro);
        edtOtro.addTextChangedListener(new validarCamposObligatorios());
        edtOtroDesde = view.findViewById(R.id.edt_formulario_ddotro);
        edtOtroDesde.setOnClickListener(this);
        edtOtroHasta = view.findViewById(R.id.edt_formulario_dhotro);
        edtOtroHasta.setOnClickListener(this);

        layoutCorreo = view.findViewById(R.id.layout_formulario_correo);
        edtCorreo = view.findViewById(R.id.edt_formulario_correo);
        edtCorreo.addTextChangedListener(new validarCamposObligatorios());
        btnSiguiente = view.findViewById(R.id.btn_formulario_siguiente);
        Button btnCancelar = view.findViewById(R.id.btn_formulario_cancelar);

        vistaPrincipal = view.findViewById(R.id.view_formulario);
        vistaPrincipal.setOnClickListener((View v) -> esconderTeclado(vistaPrincipal));

        spPais.setAdapter(new GenericoAdaptador(getActivity(), listaPaises));
        spPais.setSelection(0);
        spPais.setOnItemSelectedListener(this);

        spProfesion.setAdapter(new GenericoAdaptador(getActivity(), listaProfesiones));
        spProfesion.setSelection(0);
        spProfesion.setOnItemSelectedListener(this);

        spNacionalidad.setAdapter(new GenericoAdaptador(getActivity(), listaNacionalidad));
        spNacionalidad.setSelection(1);
        spNacionalidad.setOnItemSelectedListener(this);

        btnSiguiente.setOnClickListener((View v) -> aplicarValidaciones());
        btnCancelar.setOnClickListener((View vista) -> delegado.cancelarTramite());
        rellenarFormulario(VariablesGlobales.getInstance().solicitante);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            validarHabiliarBotonSiguiente();
            obtenerValidaciones();
        }
    }

    public void esconderTeclado(View vista) {
        if (getActivity() != null) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    private void rellenarFormulario(Solicitante solicitante) {
        if (edtNombre != null) {

            datosSolicitante(solicitante);
            datosSolicitanteDos(solicitante);
            solicitanteNacionalidad(solicitante);
            solicitanteOcupacion(solicitante);
            listaPais();

            if (solicitante.getDomicilio() != null) {
                Domicilio domicilio = solicitante.getDomicilio();
                domicilioPais(domicilio);
                domicilioDos(domicilio);
                domicilioTres(domicilio);
                domicilioAcentamiento(domicilio);
            }

            if (solicitante.getTelefonos() != null) {
                List<Telefono> telefonos = solicitante.getTelefonos();
                llenarTelefonos(telefonos);
            }

            obtenerValidaciones();
            validarHabiliarBotonSiguiente();
        }
    }

    private void llenarTelefonos(List<Telefono> telefonos) {
        for (Telefono telefono : telefonos) {
            if (telefono.getTipo() != null) {
                switch (telefono.getTipo()) {
                    case "CASA":
                        edtTelCasa.setText(telefono.getNumero());
                        telCasa(telefono);
                        break;
                    case "MOVIL":
                        edtCelular.setText(telefono.getNumero());
                        telCelular(telefono);
                        break;
                    default:
                        otroTelefono(telefono);
                        break;
                }
            }
        }
    }

    private void otroTelefono(Telefono telefono) {
        edtOtro.setText(telefono.getNumero());
        if (telefono.getDisponibleDesde() != null) {
            edtOtroDesde.setText(telefono.getDisponibleDesde());
        }
        if (telefono.getDisponibleHasta() != null) {
            edtOtroHasta.setText(telefono.getDisponibleHasta());
        }
    }

    private void telCelular(Telefono telefono) {
        if (telefono.getDisponibleDesde() != null) {
            edtCelularDesde.setText(telefono.getDisponibleDesde());
        }
        if (telefono.getDisponibleHasta() != null) {
            edtCelularHasta.setText(telefono.getDisponibleHasta());
        }
    }

    private void telCasa(Telefono telefono) {
        if (telefono.getDisponibleDesde() != null) {
            edtTelDesde.setText(telefono.getDisponibleDesde());
        }
        if (telefono.getDisponibleHasta() != null) {
            edtTelHasta.setText(telefono.getDisponibleHasta());
        }
    }

    private void listaPais() {
        for (int x = 0; x < listaPaises.size(); x++) {
            Categoria categoria = listaPaises.get(x);
            if (categoria.getId().equals(ID_MEXICO)) {
                spPais.setSelection(x);
                break;
            }
        }
    }

    private void domicilioAcentamiento(Domicilio domicilio) {
        if (domicilio.getAsentamiento() != null && listaColoniasLaboral != null) {
            for (int x = 0; x < listaColoniasLaboral.size(); x++) {
                Categoria categoria = listaColoniasLaboral.get(x);
                if (categoria.getDescripcion().contains(domicilio.getAsentamiento())) {
                    spColonia.setSelection(x);
                    break;
                }
            }
        }
    }

    private void domicilioTres(Domicilio domicilio) {
        if (domicilio.getEstado() != null) {
            edtEstado.setText(domicilio.getEstado());
        }
        if (domicilio.getCp() != null) {
            edtCp.setText(domicilio.getCp());
            leerDireccion(domicilio.getCp());
        }

        if (domicilio.getCiudad() != null && edtCiudad.getText().toString().isEmpty()) {
            edtCiudad.setText(domicilio.getCiudad());
        }
    }

    private void domicilioDos(Domicilio domicilio) {

        if (domicilio.getCalle() != null) {
            edtCalle.setText(domicilio.getCalle());
        }
        if (domicilio.getNumExt() != null) {
            edtNumero.setText(domicilio.getNumExt());
        }
        if (domicilio.getNumInt() != null) {
            edtNumeroInt.setText(domicilio.getNumInt());
        }
        if (domicilio.getMunicipio() != null) {
            edtMunicipio.setText(domicilio.getMunicipio());
        }
    }

    private void domicilioPais(Domicilio domicilio) {
        if (domicilio.getIdPais() != null) {
            for (int x = 0; x < listaPaises.size(); x++) {
                Categoria categoria = listaPaises.get(x);
                if (categoria.getId() != null && categoria.getId().equals(domicilio.getIdPais())) {
                    spPais.setSelection(x);
                    break;
                }
            }
        }
    }

    private void datosSolicitanteDos(Solicitante solicitante) {

        if (solicitante.geteFirma() != null) {
            edtEFirma.setText(solicitante.geteFirma());
        }
        if (solicitante.getCorreo() != null) {
            edtCorreo.setText(solicitante.getCorreo());
        }
        if (solicitante.getCurp() != null) {
            edtCurp.setText(solicitante.getCurp());
        }
        if (solicitante.getRfc() != null) {
            edtRfc.setText(solicitante.getRfc());
        }
    }

    private void solicitanteOcupacion(Solicitante solicitante) {
        if (solicitante.getOcupacion() != null) {
            for (int x = 0; x < listaProfesiones.size(); x++) {
                Categoria categoria = listaProfesiones.get(x);
                if (solicitante.getOcupacion().equals(categoria.getDescripcion())) {
                    spProfesion.setSelection(x);
                    break;
                }
            }
        }
    }

    private void solicitanteNacionalidad(Solicitante solicitante) {
        if (solicitante.getIdNacionalidad() != null) {
            for (int x = 0; x < listaNacionalidad.size(); x++) {
                Categoria categoria = listaNacionalidad.get(x);
                if (solicitante.getIdNacionalidad().equals(categoria.getClave())) {
                    spNacionalidad.setSelection(x);
                    break;
                }
            }
        }
    }

    private void datosSolicitante(Solicitante solicitante) {

        if (solicitante.getNombre() != null) {
            edtNombre.setText(solicitante.getNombre());
        }
        if (solicitante.getApPaterno() != null) {
            edtApPaterno.setText(solicitante.getApPaterno());
        }
        if (solicitante.getApMaterno() != null) {
            edtApMaterno.setText(solicitante.getApMaterno());
        }
        if (solicitante.getFechaNacimiento() != null) {
            edtFechaNacimiento.setText(solicitante.getFechaNacimiento());
        }
    }

    @Override
    public void onClick(View vista) {
        esconderTeclado(vistaPrincipal);
        switch (vista.getId()) { // falta la cláusula por defecto porque no se ocupa
            case R.id.edt_formulario_nacimiento:
                mostrarDialogoFecha();
                break;
            case R.id.edt_formulario_ddtelefono:
                mostrarDialogoTiempo(R.id.edt_formulario_ddtelefono);
                break;
            case R.id.edt_formulario_dhtelefono:
                mostrarDialogoTiempo(R.id.edt_formulario_dhtelefono);
                break;
            case R.id.edt_formulario_ddcelular:
                mostrarDialogoTiempo(R.id.edt_formulario_ddcelular);
                break;
            case R.id.edt_formulario_dhcelular:
                mostrarDialogoTiempo(R.id.edt_formulario_dhcelular);
                break;
            case R.id.edt_formulario_ddotro:
                mostrarDialogoTiempo(R.id.edt_formulario_ddotro);
                break;
            case R.id.edt_formulario_dhotro:
                mostrarDialogoTiempo(R.id.edt_formulario_dhotro);
                break;
            default:
        }
    }

    private void aplicarValidaciones() {
        boolean esError = false;

        Drawable img = contexto.getResources().getDrawable(R.drawable.ic_alerta_roja);
        esError = datosPersonalesCurp(esError, img);
        esError = datosPersonalesTelefono(esError, img);

        if (!edtCorreo.getText().toString().trim().isEmpty()) {
            if (!ValidadorFormulario.validarCorreo(edtCorreo.getText().toString())) {
                esError = true;
                layoutCorreo.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutCorreo.setErrorEnabled(true);
                edtCorreo.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            }
        }

        if (!ValidadorFormulario.validarRFC(edtRfc.getText().toString())) {
            esError = true;
            layoutRfc.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutRfc.setErrorEnabled(true);
            edtRfc.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        limpiarErrores = true;
        if (!esError) {
            guardarSolicitante();
        }
    }

    private boolean datosPersonalesTelefono(boolean esError, Drawable img) {
        if (!edtTelCasa.getText().toString().trim().isEmpty()) {
            if (!ValidadorFormulario.validarTelefono(edtTelCasa.getText().toString())) {
                esError = true;
                layoutCasa.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutCasa.setErrorEnabled(true);
                edtTelCasa.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            }
        }

        if (edtOtro.getText().toString().trim().length() > 0) {
            if (!ValidadorFormulario.validarTelefono(edtOtro.getText().toString())) {
                esError = true;
                layoutOtro.setError(contexto.getString(R.string.formulario_general_formato_invalido));
                layoutOtro.setErrorEnabled(true);
                edtOtro.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            }
        }
        return esError;
    }

    private boolean datosPersonalesCurp(boolean esError, Drawable img) {
        if (edtCp.getText().toString().length() != Constantes.TamanoCampos.CP) {
            esError = true;
            layoutCp.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutCp.setErrorEnabled(true);
            edtCp.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        if (!ValidadorFormulario.validarCurp(edtCurp.getText().toString())) {
            esError = true;
            layoutCurp.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutCurp.setErrorEnabled(true);
            edtCurp.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        if (!ValidadorFormulario.validarTelefono(edtCelular.getText().toString())) {
            esError = true;
            layoutCelular.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutCelular.setErrorEnabled(true);
            edtCelular.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }
        return esError;
    }

    private void guardarSolicitante() {
        Categoria nacionalidad = listaNacionalidad.get(spNacionalidad.getSelectedItemPosition());
        Categoria pais = listaPaises.get(spPais.getSelectedItemPosition());
        Categoria profesion = listaProfesiones.get(spProfesion.getSelectedItemPosition());
        Categoria colonia = listaColoniasLaboral.get(spColonia.getSelectedItemPosition());

        Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
        solicitante.setNombre(edtNombre.getText().toString());
        solicitante.setApPaterno(edtApPaterno.getText().toString());
        solicitante.setApMaterno(edtApMaterno.getText().toString());
        solicitante.setIdNacionalidad(nacionalidad.getClave());
        solicitante.setNacionalidad(nacionalidad.getDescripcion());
        solicitante.setFechaNacimiento(edtFechaNacimiento.getText().toString());
        solicitante.setIdOcupacion(profesion.getId());
        solicitante.setOcupacion(profesion.getDescripcion());
        solicitante.setCurp(edtCurp.getText().toString());
        solicitante.setRfc(edtRfc.getText().toString());
        solicitante.seteFirma(edtEFirma.getText().toString());
        solicitante.setCorreo(edtCorreo.getText().toString());

        Domicilio domicilio = new Domicilio();
        domicilio.setIdPais(pais.getId());
        domicilio.setPais(pais.getDescripcion());
        domicilio.setCalle(edtCalle.getText().toString());
        domicilio.setNumExt(edtNumero.getText().toString());
        domicilio.setNumInt(edtNumeroInt.getText().toString());
        domicilio.setCp(edtCp.getText().toString());
        domicilio.setIdColonia(colonia.getId());
        domicilio.setAsentamiento(colonia.getDescripcion());
        domicilio.setMunicipio(edtMunicipio.getText().toString());
        domicilio.setCiudad(edtCiudad.getText().toString());
        domicilio.setEstado(edtEstado.getText().toString());
        if (objCodigoPostal != null) {
            domicilio.setIdMunicipio(objCodigoPostal.getIdMunicipio());
            domicilio.setIdCiudad(objCodigoPostal.getIdCiudad());
            domicilio.setIdEstado(objCodigoPostal.getIdEntidadFederativa());
        }
        solicitante.setDomicilio(domicilio);

        RealmList<Telefono> telefonos = new RealmList<>();
        telefonosRealm(telefonos);
        celularSolicitante(telefonos);
        telefonoAlterno(telefonos);
        solicitante.setTelefonos(telefonos);

        VariablesGlobales.getInstance().solicitante = solicitante;
        DBHelperRealm.insertarActualizarSolicitante(solicitante);
        delegado.mostrarReferencias();
    }

    private void telefonoAlterno(RealmList<Telefono> telefonos) {
        if (!edtOtro.getText().toString().trim().isEmpty()) {
            Telefono telefono = new Telefono();
            telefono.setNumero(edtOtro.getText().toString());
            telefono.setTipo(Constantes.TipoTelefono.RECADOS.name());
            telefono.setIdTipo(DBHelperRealm.obtenerIdTelefono(Constantes.TipoTelefono.RECADOS.name()));
            if (!edtOtroDesde.getText().toString().isEmpty()) {
                telefono.setDisponibleDesde(edtOtroDesde.getText().toString());
            }

            if (!edtOtroHasta.getText().toString().isEmpty()) {
                telefono.setDisponibleHasta(edtOtroHasta.getText().toString());
            }
            telefonos.add(telefono);
        }
    }

    private void celularSolicitante(RealmList<Telefono> telefonos) {
        if (!edtCelular.getText().toString().trim().isEmpty()) {
            Telefono telefono = new Telefono();
            telefono.setNumero(edtCelular.getText().toString());
            telefono.setTipo(Constantes.TipoTelefono.MOVIL.name());
            telefono.setIdTipo(DBHelperRealm.obtenerIdTelefono(Constantes.TipoTelefono.MOVIL.name()));
            if (!edtCelularDesde.getText().toString().isEmpty()) {
                telefono.setDisponibleDesde(edtCelularDesde.getText().toString());
            }

            if (!edtCelularHasta.getText().toString().isEmpty()) {
                telefono.setDisponibleHasta(edtCelularHasta.getText().toString());
            }
            telefonos.add(telefono);
        }
    }

    private void telefonosRealm(RealmList<Telefono> telefonos) {
        if (!edtTelCasa.getText().toString().trim().isEmpty()) {
            Telefono telefono = new Telefono();
            telefono.setNumero(edtTelCasa.getText().toString());
            telefono.setTipo(Constantes.TipoTelefono.CASA.name());
            telefono.setIdTipo(DBHelperRealm.obtenerIdTelefono(Constantes.TipoTelefono.CASA.name()));
            if (!edtTelDesde.getText().toString().isEmpty()) {
                telefono.setDisponibleDesde(edtTelDesde.getText().toString());
            }

            if (!edtTelHasta.getText().toString().isEmpty()) {
                telefono.setDisponibleHasta(edtTelHasta.getText().toString());
            }
            telefonos.add(telefono);
        }
    }

    private void limpiarError() {
        edtCp.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCp.setError("");
        layoutRfc.setError("");
        edtRfc.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCurp.setError("");
        edtCurp.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCelular.setError("");
        edtCelular.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCasa.setError("");
        edtTelCasa.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutOtro.setError("");
        edtOtro.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        layoutCorreo.setError("");
        edtCorreo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    private void mostrarDialogoFecha() {
        String fecha = "";
        if (edtFechaNacimiento.getText() != null) {
            fecha = edtFechaNacimiento.getText().toString();
        }
        DatePickerFragment fechaPicker = DatePickerFragment.nuevaInstancia(fecha,
                (DatePicker datePicker, int year, int month, int day) ->
                        edtFechaNacimiento.setText(Utilidades.obtenerFechaConFormato(year, month, day)));
        if (getActivity() != null) {
            fechaPicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    }

    private void mostrarDialogoTiempo(int id) {
        TimePickerFragment tiempoPicker = TimePickerFragment.nuevaInstancia(
                (TimePicker timePicker, int horas, int minutos) -> {
                    final String tiempo = Utilidades.obtenerTiempoConFormato(horas, minutos);

                    switch (id) {// falta la cláusula por defecto porque no se ocupa
                        case R.id.edt_formulario_ddtelefono:
                            edtTelDesde.setText(tiempo);
                            break;
                        case R.id.edt_formulario_dhtelefono:
                            edtTelHasta.setText(tiempo);
                            break;
                        case R.id.edt_formulario_ddcelular:
                            edtCelularDesde.setText(tiempo);
                            break;
                        case R.id.edt_formulario_dhcelular:
                            edtCelularHasta.setText(tiempo);
                            break;
                        case R.id.edt_formulario_ddotro:
                            edtOtroDesde.setText(tiempo);
                            break;
                        case R.id.edt_formulario_dhotro:
                            edtOtroHasta.setText(tiempo);
                            break;
                        default:
                    }
                });
        if (getActivity() != null) {
            tiempoPicker.show(getActivity().getSupportFragmentManager(), "datePicker");
        }
    }

    @Override
    public void onSpinnerOpened(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde_abierto));
    }

    @Override
    public void onSpinnerClosed(Spinner spinner) {
        spinner.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
        obtenerValidaciones();
    }

    private void leerDireccion(String codigoPostal) {
        DbHelperEstados db = new DbHelperEstados(getActivity());
        objCodigoPostal = db.obtenerDireccion(codigoPostal);

        if (objCodigoPostal != null) {
            edtMunicipio.setText(objCodigoPostal.getMunicipio());
            edtCiudad.setText(objCodigoPostal.getCiudad());
            edtEstado.setText(objCodigoPostal.getEntidadFederativa());
            listaColoniasLaboral = new ArrayList<>();
            Categoria categoriaVacia = new Categoria(0, 0, "Seleccionar", "Seleccionar");
            listaColoniasLaboral.add(categoriaVacia);

            for (Colonia colonia : objCodigoPostal.getListaColonias()) {
                Categoria categoria = new Categoria(colonia.getIdColonia(), 0, colonia.getNombre(), colonia.getNombre());
                listaColoniasLaboral.add(categoria);
            }

            GenericoAdaptador adaptador = new GenericoAdaptador(getActivity(), listaColoniasLaboral);
            spColonia.setAdapter(adaptador);
            spColonia.setSelection(0);
            spColonia.setOnItemSelectedListener(this);
        } else if (edtCp.getText() != null && !edtCp.getText().toString().isEmpty()) {
            DialogoPresenter dialogoPresenter = new DialogoPresenter();
            dialogoPresenter.mensajeAlerta(contexto, getString(R.string.error), getString(R.string.error_motor_cp));
        } else {
            //Validación no utilizada
        }
    }

    public void habilitarBotonSiguiente(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    public void obtenerValidaciones() {
        if (edtNombre != null) {
            int progreso = 0;
            progreso = validacionUno(progreso);
            progreso = validacionDos(progreso);
            progreso = validacionTres(progreso);
            progreso = validacionCuatro(progreso);

            if (spColonia.getSelectedItem() != null) {
                if (spColonia.getSelectedItemPosition() != 0) {
                    progreso += 1;
                }
            }
            delegado.enviarProgreso(progreso, 0);
        }
    }

    private int validacionCuatro(int progreso) {

        if (spNacionalidad.getSelectedItemPosition() != 0) {
            progreso += 1;
        }
        if (spPais.getSelectedItemPosition() != 0) {
            progreso += 1;
        }
        if (spProfesion.getSelectedItemPosition() != 0) {
            progreso += 1;
        }
        return progreso;
    }

    private int validacionTres(int progreso) {
        if (!edtEstado.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtCurp.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtRfc.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtCelular.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }
        return progreso;
    }

    private int validacionDos(int progreso) {
        if (!edtNumero.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtCp.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtMunicipio.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtCiudad.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }
        return progreso;
    }

    private int validacionUno(int progreso) {
        if (!edtNombre.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtApPaterno.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtFechaNacimiento.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }

        if (!edtCalle.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }
        return progreso;
    }

    private void validarHabiliarBotonSiguiente() {
        if (edtNombre != null) {
            boolean validacionUno = validarCamposUno() && validarCamposDos();
            boolean validacionDos = validarCamposTres() && validarCamposCuatro();
            if (validacionUno && validacionDos) {
                habilitarBotonSiguiente(true);
            } else {
                habilitarBotonSiguiente(false);
            }
        }
    }

    private boolean validarCamposCuatro() {
        return spNacionalidad.getSelectedItemPosition() != 0 && spPais.getSelectedItemPosition() != 0
                && spProfesion.getSelectedItemPosition() != 0 && spColonia.getSelectedItemPosition() != 0;
    }

    private boolean validarCamposTres() {
        return !edtEstado.getText().toString().trim().isEmpty() && !edtCurp.getText().toString().trim().isEmpty() &&
                !edtRfc.getText().toString().trim().isEmpty() && !edtCelular.getText().toString().trim().isEmpty();
    }

    private boolean validarCamposDos() {
        return !edtNumero.getText().toString().trim().isEmpty() && !edtCp.getText().toString().trim().isEmpty() &&
                !edtMunicipio.getText().toString().trim().isEmpty() && !edtCiudad.getText().toString().trim().isEmpty();
    }

    public boolean validarCamposUno() {
        return !edtNombre.getText().toString().trim().isEmpty() && !edtApPaterno.getText().toString().trim().isEmpty() &&
                !edtFechaNacimiento.getText().toString().trim().isEmpty() && !edtCalle.getText().toString().trim().isEmpty();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((GenericoAdaptador) parent.getAdapter()).setPosicionSeleccionada(position);
        validarHabiliarBotonSiguiente();
        obtenerValidaciones();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        throw new UnsupportedOperationException();
    }

    public interface FormularioGeneralesInterfaz {
        void mostrarReferencias();

        void enviarProgreso(int progreso, int seccion);

        void cancelarTramite();
    }

    private class validarCamposObligatorios implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //este metodo no se ocupa porque la validaciones las hacemos despues de que escribio el usuario
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (limpiarErrores) {
                limpiarErrores = false;
                limpiarError();
            }
            validarHabiliarBotonSiguiente();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            obtenerValidaciones();
        }
    }
}
