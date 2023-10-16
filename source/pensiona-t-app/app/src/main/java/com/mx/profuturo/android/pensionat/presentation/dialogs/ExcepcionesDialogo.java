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
package com.mx.profuturo.android.pensionat.presentation.dialogs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.presentation.busqueda.GenericoAdaptador;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.SpinnerCaja;

import java.util.ArrayList;

/**
 * <h1>ExcepcionesDialogo</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-24
 */
public class ExcepcionesDialogo extends DialogFragment {
    private SpinnerCaja spExcepciones;
    private Button btnGuardar;
    private IExcepcionesDialogo delegado;

    public static ExcepcionesDialogo getInstancia() {
        return new ExcepcionesDialogo();
    }

    public void setDelegado(IExcepcionesDialogo delegado) {
        this.delegado = delegado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.dialogo_excepcion_firma, container, false);
        setCancelable(false);
        btnGuardar = vista.findViewById(R.id.btn_excepciones_guardar);
        Button btnCancelar = vista.findViewById(R.id.btn_excepciones_cancelar);
        btnCancelar.setOnClickListener((View v) -> {
            dismiss();
        });
        btnGuardar.setOnClickListener((View v) -> {
            Categoria categoria = (Categoria) spExcepciones.getSelectedItem();
            if (categoria.getId() == 0) {
                limpiarExcepcion();
            } else {
                guardarExcepcion();
            }
            dismiss();
        });

        habilitarBotonContinuar(false);
        spExcepciones = vista.findViewById(R.id.spinner_excepcion_opciones);
        ArrayList<Categoria> excepciones = DBHelperRealm.obtenerCatalogo(Constantes.Catalogos.EXCEPCION);
        GenericoAdaptador adaptador = new GenericoAdaptador(getContext(), excepciones);
        spExcepciones.setAdapter(adaptador);
        spExcepciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adaptador.setPosicionSeleccionada(i);
                habilitarBotonContinuar(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                throw new UnsupportedOperationException();
            }
        });
        spExcepciones.setSpinnerEventsListener(new SpinnerCaja.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened(Spinner spinner) {
                if (getActivity() != null) {
                    spinner.setBackground(getActivity().getDrawable(R.drawable.fondo_spinner_borde_abierto));
                }
            }

            @Override
            public void onSpinnerClosed(Spinner spinner) {
                if (getActivity() != null) {
                    spinner.setBackground(getActivity().getDrawable(R.drawable.fondo_spinner_borde));
                }
            }
        });

        Expediente expediente = DBHelperRealm.obtenerExpediente();
        if (expediente.getIdExcepcion() != null && expediente.getIdExcepcion() != 0) {
            for (int x = 0; x < excepciones.size(); x++) {
                Categoria categoria = excepciones.get(x);
                if (categoria.getId().equals(expediente.getIdExcepcion())) {
                    spExcepciones.setSelection(x);
                    break;
                }
            }
        } else {
            spExcepciones.setSelection(0);
        }

        return vista;
    }

    private void limpiarExcepcion() {
        DBHelperRealm.actualizarExcepcion("", 0);
        delegado.guardar(false, "");
    }

    private void guardarExcepcion() {
        Categoria categoria = (Categoria) spExcepciones.getSelectedItem();
        DBHelperRealm.actualizarExcepcion(categoria.getDescripcion(), categoria.getId());
        delegado.guardar(true, categoria.getDescripcion());
    }

    private void habilitarBotonContinuar(boolean habilitar) {
        btnGuardar.setEnabled(habilitar);
        if (getActivity() != null) {
            Drawable fondoBoton = habilitar ? getActivity().getDrawable(R.drawable.fondo_boton_azul) : getActivity().getDrawable(R.drawable.fondo_boton_gris);
            btnGuardar.setBackground(fondoBoton);
        }
    }

    @FunctionalInterface
    public interface IExcepcionesDialogo {
        void guardar(boolean esExcepcion, String excepcion);
    }
}
