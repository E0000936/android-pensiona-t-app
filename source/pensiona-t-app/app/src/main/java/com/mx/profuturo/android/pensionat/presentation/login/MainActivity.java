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
package com.mx.profuturo.android.pensionat.presentation.login;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.mx.profuturo.android.pensionat.utils.TimerCierreSesion;

/**
 * <h1>MainActivity</h1>
 * Clase principal de la cual heredan las demás actividades
 * para controlar el manejo de la sesión
 * El cierre de sesión solo se activa mientras la aplicación
 * se encuentre en segundo plano.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-23
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        TimerCierreSesion.getInstancia(this);
        if (!TimerCierreSesion.isMauRunning())
            TimerCierreSesion.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TimerCierreSesion.stop();
    }

    /**
     * Una vez finalizado el tiempo de espera, se regresa al login
     * sin mostrar ningun mensaje de confirmación.
     */
    public void cerrarSesion() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
