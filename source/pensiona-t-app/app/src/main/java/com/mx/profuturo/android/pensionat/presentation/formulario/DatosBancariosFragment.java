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
import android.text.InputFilter;
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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.Banco;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.busqueda.GenericoAdaptador;
import com.mx.profuturo.android.pensionat.presentation.busqueda.TipoBusquedaAdaptador;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ValidadorFormulario;
import com.mx.profuturo.android.pensionat.utils.ValidarCLABE;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>DatosBancariosFragment</h1>
 * Clase utilizada para la presentación y captura del formulario
 * de datos bancarios.
 *
 * @author Jose Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-03-25
 */
public class DatosBancariosFragment extends Fragment
        implements SpinnerCaja.OnSpinnerEventsListener,
        AdapterView.OnItemSelectedListener {

    private static final String CUENTA_CLABE = "CLABE";
    public DatosBancariosInterfaz delegado;
    public Context contexto;
    private TextInputEditText edtbanco;
    private TextView txtBancos;
    private SpinnerCaja spBancos;
    private SpinnerCaja spTipoCuenta;
    private Button btnSiguiente;
    private TextInputLayout layoutClabe;
    private TipoBusquedaAdaptador adaptadorArray;
    private String clabe;
    private int digitoValidador;
    private ArrayList<Categoria> listaBancos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_datos_bancarios, parent, false);
        contexto = parent.getContext();
        listaBancos = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.BANCO);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        spBancos = view.findViewById(R.id.spinner_banco);
        spBancos.setAdapter(new GenericoAdaptador(getActivity(), listaBancos));
        spBancos.setSelection(0);
        spBancos.setSpinnerEventsListener(this);
        spBancos.setOnItemSelectedListener(this);
        spBancos.setFocusable(true);
        spBancos.setFocusableInTouchMode(true);
        spBancos.requestFocus();
        spTipoCuenta = view.findViewById(R.id.spinner_tipo_cuenta);
        spTipoCuenta.setSpinnerEventsListener(this);
        spTipoCuenta.setEnabled(false);
        layoutClabe = view.findViewById(R.id.layout_cuenta_clabe);
        edtbanco = view.findViewById(R.id.edt_cuenta_clabe);
        edtbanco.addTextChangedListener(new validarCamposObligatorios());
        edtbanco.setEnabled(false);
        btnSiguiente = view.findViewById(R.id.btn_bancario_siguiente);
        txtBancos = view.findViewById(R.id.textView_banco);
        habilitarBotonSiguiente(false);
        btnSiguiente.setOnClickListener((View v) -> {
            validarDatosBancarios(spTipoCuenta.getSelectedItemPosition());
            clabe = edtbanco.getText().toString();
            if (clabe.length() == 18) {
                String str = clabe.substring(0, clabe.length() - 1);
                int[] clabeArray = convertirCadenaArrayEnteros(str);
                digitoValidador = ValidarCLABE.validarCuentaBancaria(clabeArray);
                Log.wtf("CONDICION", "SI ENTRA EN LA CONDICION");
                obtenerDigitoVerificador(digitoValidador);
            }
            edtbanco.setEnabled(true);
        });
        View viewPrincipal = view.findViewById(R.id.view_datos_bancarios);
        viewPrincipal.setOnClickListener((View v) -> esconderTeclado(viewPrincipal));
        Button btnCancelar = view.findViewById(R.id.btn_bancario_cancelar);
        btnCancelar.setOnClickListener((View v) -> delegado.cancelarTramite());
        cargarTipoCuenta();
        if (VariablesGlobales.getInstance().solicitante.getBanco() != null) {
            rellenarFormulario(VariablesGlobales.getInstance().solicitante.getBanco());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            aplicarValidaciones();
            obtenerValidaciones();
        }
    }

    public boolean validarBanco() {
        Categoria categoria = listaBancos.get(spBancos.getSelectedItemPosition());
        String bancos = categoria.getBancoDigitos();
        String bancoClabe = clabe;
        String prueba = bancoClabe.substring(0, 3);
        return bancos.equals(prueba);
    }

    private void rellenarFormulario(Banco banco) {
        if (banco.getId() != null) {
            for (int x = 0; x < listaBancos.size(); x++) {
                Categoria categoria = listaBancos.get(x);
                if (banco.getId().equals(categoria.getClave())) {
                    spBancos.setSelection(x);
                    break;
                }
            }
        }

        if (banco.getClabe() != null) {
            edtbanco.setText(banco.getClabe());
        }
    }

    public void obtenerDigitoVerificador(int digitoValidador) {
        int[] cuenta = convertirCadenaArrayEnteros(clabe);
        if (!validarBanco() || cuenta[17] != digitoValidador) {
            layoutClabe.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutClabe.setErrorEnabled(true);
            edtbanco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            edtbanco.setEnabled(true);
        } else {
            if ((spBancos.getSelectedItemPosition() != 0 && !edtbanco.toString().trim().isEmpty())) {
                guardarSolicitante();
            }
        }
    }

    private void guardarSolicitante() {
        Solicitante solicitante = VariablesGlobales.getInstance().solicitante;
        Banco banco = new Banco();
        banco.setClabe(edtbanco.getText().toString());
        Categoria categoria = listaBancos.get(spBancos.getSelectedItemPosition());
        banco.setDescripcion(categoria.getDescripcion());
        banco.setId(categoria.getClave());
        banco.setTipoCuenta(CUENTA_CLABE);
        solicitante.setBanco(banco);

        VariablesGlobales.getInstance().solicitante = solicitante;
        DBHelperRealm.insertarActualizarSolicitante(solicitante);
        delegado.mostrarSeleccionTramite();
    }

    public int[] convertirCadenaArrayEnteros(String cadena) {
       /* String[] cadenaArray = cadena.split("");
        int[] cadenaInt = new int[cadena.length()];
        String[] newCadenaArray = Arrays.copyOfRange(cadenaArray, 0, cadenaArray.length);
        for (int i = 0; i < newCadenaArray.length; i++) {
            try {
                cadenaInt[i] = Integer.valueOf(newCadenaArray[i]);
            } catch (NumberFormatException e) {
                cadenaInt[i] = -1;
            }
        }

        return cadenaInt;*/
        int[] cadenaInt = new int[cadena.length()];
        for(int i = 0; i<cadena.length(); i++){
            cadenaInt[i] = Integer.parseInt(String.valueOf(cadena.charAt(i)));
        }

        return cadenaInt;
    }

    private void aplicarValidaciones() {
        if (spBancos.getSelectedItemPosition() != 0) {
            edtbanco.setEnabled(true);
        } else {
            edtbanco.setEnabled(false);
        }

        if (!edtbanco.getText().toString().trim().isEmpty() && spBancos.getSelectedItemPosition() != 0) {
            habilitarBotonSiguiente(true);
        } else {
            habilitarBotonSiguiente(false);
        }
    }

    private void validarDatosBancarios(int posicion) {
        switch (posicion) {//no se tiene default son casos especificos
            case 0:
                break;
            case 1:
                validarClabeBanco(edtbanco.getText().toString());
                break;
            case 2:
                validarTelefonoBanco(edtbanco.getText().toString());
                break;
            case 3:
                validarNumeroTarjeta(edtbanco.getText().toString());
                break;
            default:
        }
    }

    public void habilitarBotonSiguiente(boolean habilitar) {
        btnSiguiente.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? contexto.getDrawable(R.drawable.fondo_boton_azul) : contexto.getDrawable(R.drawable.fondo_boton_gris);
        btnSiguiente.setBackground(fondoBoton);
    }

    private void cargarTipoCuenta() {
        String[] myResArray = getResources().getStringArray(R.array.tipo_cuenta_spinner);
        List<String> myResArrayList = Arrays.asList(myResArray);
        ArrayList<String> lista = new ArrayList<>(myResArrayList);
        adaptadorArray = new TipoBusquedaAdaptador(contexto, lista);
        spTipoCuenta.setAdapter(adaptadorArray);
        spTipoCuenta.setSelection(1);
        adaptadorArray.setPosicionActual(0);
        spTipoCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adaptadorArray.setPosicionActual(position);
                Drawable img = contexto.getResources().getDrawable(R.drawable.ic_alerta_roja);
                switch (position) { //no se tiene default son casos especificos
                    case 0:
                        layoutClabe.setHint(contexto.getString(R.string.formulario_tipo_cuenta_hint));
                        break;
                    case 1:
                        edtbanco.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.CLABE)});
                        break;
                    case 2:
                        edtbanco.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.TELEFONO)});
                        break;
                    case 3:
                        edtbanco.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Constantes.TamanoCampos.NUMERODETARJETA)});
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void validarClabeBanco(String clabe) {
        if (!ValidadorFormulario.validarCLABE(clabe)) {
            habilitarBotonSiguiente(true);
            layoutClabe.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutClabe.setErrorEnabled(true);
            edtbanco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            edtbanco.setEnabled(true);
        }
    }

    private void validarTelefonoBanco(String telefono) {
        if (!ValidadorFormulario.validarTelefono(telefono)) {
            habilitarBotonSiguiente(true);
            layoutClabe.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutClabe.setErrorEnabled(true);
            edtbanco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            edtbanco.setEnabled(true);
        }
    }

    private void validarNumeroTarjeta(String numTarjeta) {
        if (!ValidadorFormulario.validarNumeroTarjeta(numTarjeta)) {
            habilitarBotonSiguiente(true);
            layoutClabe.setError(contexto.getString(R.string.formulario_general_formato_invalido));
            layoutClabe.setErrorEnabled(true);
            edtbanco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    private void limpiarError() {
        layoutClabe.setError("");
        edtbanco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        spBancos.setBackground(contexto.getDrawable(R.drawable.fondo_spinner_borde));
        txtBancos.setTextColor(ContextCompat.getColor(contexto, R.color.colorTextoElemento));
    }

    public void esconderTeclado(View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    public void obtenerValidaciones() {
        int progreso = 0;

        if (spBancos.getSelectedItemPosition() != 0) {
            progreso += 1;
        }

        if (spTipoCuenta.getSelectedItemPosition() != 0) {
            progreso += 1;
        }
        if (!edtbanco.getText().toString().trim().isEmpty()) {
            progreso += 1;
        }
        delegado.enviarProgreso(progreso, 2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((GenericoAdaptador) parent.getAdapter()).setPosicionSeleccionada(position);
        limpiarError();
        aplicarValidaciones();
        obtenerValidaciones();
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
        obtenerValidaciones();
    }

    public interface DatosBancariosInterfaz {
        void mostrarSeleccionTramite();

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
            limpiarError();
            aplicarValidaciones();
            obtenerValidaciones();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //este metodo no se ocupa porque la validaciones las hacemos despues de que escribio el usuario

        }
    }
}

