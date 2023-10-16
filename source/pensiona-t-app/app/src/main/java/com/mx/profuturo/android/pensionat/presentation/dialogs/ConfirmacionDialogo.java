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

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mx.profuturo.android.pensionat.R;

/**
 * <h1>ConfirmacionDialogo</h1>
 * Dialogo de confirmación de envio correo y sms.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-06
 */
public class ConfirmacionDialogo extends DialogFragment {

    public static ConfirmacionDialogo getInstancia(String numero, String correo) {
        Bundle bundle = new Bundle();
        bundle.putString("numero", numero);
        bundle.putString("correo", correo);
        ConfirmacionDialogo fragment = new ConfirmacionDialogo();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.dialogo_confirmacion, container, false);
        setCancelable(false);
        TextView txtMensaje = vista.findViewById(R.id.txt_confirmacion_mensaje);
        Button btnAceptar = vista.findViewById(R.id.btn_confirmacion_aceptar);
        btnAceptar.setOnClickListener((View v) -> dismiss());

        if (getArguments() != null) {
            String correo = getArguments().getString("correo");
            String telefono = getArguments().getString("numero");

            if (telefono != null && !telefono.equals("")) {
                String mensaje = getString(R.string.confirmacion_mensaje, telefono);
                SpannableStringBuilder str = new SpannableStringBuilder(mensaje);
                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                        mensaje.length() - telefono.length(),
                        mensaje.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtMensaje.setText(str);
            } else {
                String mensaje = getString(R.string.confirmacion_correo, correo);
                SpannableStringBuilder str = new SpannableStringBuilder(mensaje);
                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                        mensaje.length() - correo.length(),
                        mensaje.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtMensaje.setText(str);
            }
        }

        return vista;
    }
}
