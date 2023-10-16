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

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mx.profuturo.android.pensionat.R;

/**
 * <h1>DialogoPresenter</h1>
 * Clase búsqueda solicitante
 *
 * @author Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-01-25
 */
public class DialogoPresenter {

    public void mensajeAlerta(Context contexto, String titulo, String mensaje) {
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setContentView(R.layout.dialogo_error);
        TextView txtTitulo = dialogo.findViewById(R.id.tvTitulo);
        txtTitulo.setText(titulo);
        TextView contenido = dialogo.findViewById(R.id.tvTexto);
        contenido.setText(mensaje);
        (dialogo.findViewById(R.id.buttonAceptar)).setOnClickListener(((View v) -> dialogo.dismiss()));
        dialogo.show();
    }

    public static class ClassUnderTest {
        Context mContext;

        public ClassUnderTest(Context context) {
            mContext = context;
        }

        public String getHelloWorldString() {
            return mContext.getString(R.string.text_hello_word);
        }
    }
}
