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
package com.mx.profuturo.android.pensionat.data.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <h1>Sesion</h1>
 * Clase Singleton para el acceso a SharedPreferences,
 * se debe inicializar una solo vez al principio con cualquier componente de la aplicación
 * permite persistir los valores correspondientes al usuario logueado.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-21
 */
public class Sesion {
    private static final String SESION_USUARIO = "SESION_USUARIO";
    private static final String NUMERO_EMPLEADO = "numeroEmpleado";
    private static final String PATH_PDF = "pathArchivos";
    private static final String PATH_MOTOR = "pathMotor";
    private static Sesion instancia;
    private static Context contexto;
    private SharedPreferences preferencias;

    public Sesion(Context context) {
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(SESION_USUARIO, Context.MODE_PRIVATE);
    }

    /**
     * Si la clase no ha sido inicializada creará una instancia de la clase
     * Inicializar la clase usando el contexto de la aplicación
     *
     * @param contexto contexto de la aplicación
     * @return unica instancia de la clase Sesión
     */
    public static synchronized Sesion getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new Sesion(contexto);
        }
        return instancia;
    }

    public SharedPreferences getPreferencias() {
        return preferencias;
    }

    public void limpiarSesion() {
        SharedPreferences.Editor editor = getPreferencias().edit();
        editor.clear();
        editor.apply();
    }

    public String getNumeroEmpleado() {
        return getPreferencias().getString(NUMERO_EMPLEADO, "");
    }

    public void setNumeroEmpleado(String valor) {
        SharedPreferences.Editor editor = getPreferencias().edit();
        editor.putString(NUMERO_EMPLEADO, valor);
        editor.apply();
    }

    /**
     * Ruta donde se almacenarán los archivos pdf dentro del dispositivo.
     */
    public String getPathPdf() {
        return getPreferencias().getString(PATH_PDF, "");
    }

    public void setPathPdf(String valor) {
        SharedPreferences.Editor editor = getPreferencias().edit();
        editor.putString(PATH_PDF, valor);
        editor.apply();
    }

    /**
     * Ruta retornada por el motor de imágenes donde se
     * almacenarán los archivos capturados de los documentos.
     */
    public String getPathMotor() {
        return getPreferencias().getString(PATH_MOTOR, "");
    }

    public void setPathMotor(String valor) {
        SharedPreferences.Editor editor = getPreferencias().edit();
        editor.putString(PATH_MOTOR, valor);
        editor.apply();
    }
}
