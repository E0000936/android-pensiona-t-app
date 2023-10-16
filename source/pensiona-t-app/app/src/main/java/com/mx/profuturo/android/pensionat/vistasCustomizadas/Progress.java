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
package com.mx.profuturo.android.pensionat.vistasCustomizadas;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.mx.profuturo.android.pensionat.R;

/**
 * <h1>Progress</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-07
 */
public class Progress {
    private static Dialog dialogo = null;

    private Progress() {
    }

    private static void instanciar(Context contexto) {
        dialogo = new Dialog(contexto, R.style.CustomDialogTheme);
        View v = View.inflate(contexto, R.layout.dialogo_progreso, null);
        dialogo.setContentView(v);
        dialogo.setCancelable(false);
    }

    public static void mostrar(Context contexto) {
        if (dialogo == null) {
            instanciar(contexto);
        }

        dialogo.show();
    }

    public static void ocultar() {
        if (dialogo != null) {
            dialogo.hide();
            dialogo = null;
        }
    }
}
