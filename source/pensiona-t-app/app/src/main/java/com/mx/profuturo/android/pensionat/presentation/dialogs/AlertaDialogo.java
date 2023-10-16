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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mx.profuturo.android.pensionat.R;

/**
 * <h1>AlertaDialogo</h1>
 * Clase genérica para la visualización de los mensajes de error.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-16
 */
public class AlertaDialogo extends DialogFragment {
    private IAlertaDialogo delegado;

    public static AlertaDialogo getInstancia(String titulo, String mensaje, String confirmacion) {
        Bundle bundle = new Bundle();
        bundle.putString("titulo", titulo);
        bundle.putString("mensaje", mensaje);
        bundle.putString("confirmacion", confirmacion);
        AlertaDialogo fragment = new AlertaDialogo();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setDelegado(IAlertaDialogo delegado) {
        this.delegado = delegado;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.dialogo_cerrar_sesion, container, false);
        setCancelable(false);
        ImageView imgAlerta = vista.findViewById(R.id.img_cerrar_sesion_alerta);
        TextView txtTitulo = vista.findViewById(R.id.txt_cerrar_sesion_titulo);
        TextView txtMensaje = vista.findViewById(R.id.txt_cerrar_sesion_aviso);
        TextView txtConfirmacion = vista.findViewById(R.id.txt_cerrar_sesion_confirmacion);
        Button btnSi = vista.findViewById(R.id.btn_cerrar_sesion_si);
        Button btnNo = vista.findViewById(R.id.btn_cerrar_sesion_no);
        btnNo.setOnClickListener((View v) -> dismiss());
        btnSi.setOnClickListener((View v) -> {
            delegado.confirmar();
        });

        // Para el mesaje de error mostrado en la sección de carga en la clase "FirmaPresenter"
        // Se realiza un ajuste en el diseño general de la alerta
        if (getArguments() != null) {
            String titulo = getArguments().getString("titulo");
            txtTitulo.setText(titulo);
            txtMensaje.setText(getArguments().getString("mensaje"));
            txtConfirmacion.setText(getArguments().getString("confirmacion"));
            if (titulo != null && titulo.equals(getString(R.string.acuse_error_carga)) && getActivity() != null) {
                Drawable fondoImagen = getActivity().getDrawable(R.drawable.ic_icons_error);
                imgAlerta.setBackground(fondoImagen);
            }
        }

        return vista;
    }

    @FunctionalInterface
    public interface IAlertaDialogo {
        void confirmar();
    }
}
