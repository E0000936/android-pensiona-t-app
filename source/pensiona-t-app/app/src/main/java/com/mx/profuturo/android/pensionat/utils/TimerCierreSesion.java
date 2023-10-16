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
package com.mx.profuturo.android.pensionat.utils;

import android.util.Log;

import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>TimerCierreSesion</h1>
 * Clase que configura una única instancia de un Timer
 * que durará toda la vida útil de la aplicación.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-07
 */
public class TimerCierreSesion {
    private static final long TIEMPO_CIERRE = 1000L * 60 * 20;
    private volatile static TimerCierreSesion timerSingleton;
    private volatile static boolean isRunning;
    private volatile static TimerTaskJob task;
    private MainActivity delegado;
    private volatile TimerTaskJob timerTask;
    private volatile Timer timer;

    private volatile static boolean isMauRunning = false;

    private TimerCierreSesion(MainActivity delegado) {
        this.delegado = delegado;
    }

    /**
     * Funciona como un singleton con bloqueo sincronizado.
     *
     * @return devuelve la instancia de singleton
     */

    public static synchronized TimerCierreSesion getInstancia(MainActivity delegado) {
        Log.i("Main", "getIntance");
        if (timerSingleton == null) {
            timerSingleton = new TimerCierreSesion(delegado);
            task = timerSingleton.new TimerTaskJob();
            isRunning = false;
        }
        return timerSingleton;
    }

    public static void start() {
        if (!isRunning) {
            isRunning = true;
            task.start();
        }
    }

    public static void stop() {
        if (isRunning) {
            isRunning = false;
            task.stop();
        }
    }

    public static void setIsMauRunning(boolean isMauRunning) {
        TimerCierreSesion.isMauRunning = isMauRunning;
    }

    public static boolean isMauRunning() {
        return isMauRunning;
    }

    public static long getTimForCloseSession() {
        return TimerCierreSesion.TIEMPO_CIERRE;
    }

    /*
     * Clase interna privada que permite la extensión no estática de TimerTask.
     * Esto es necesario para usar dentro del singleton.
     *
     * Solo realice cambios en DoTask () para llamar a la tarea a realizar.
     */
    private class TimerTaskJob extends TimerTask {
        @Override
        public void run() {
            doTask();
        }

        void start() {
            Log.i("Main", "start");
            timerTask = new TimerTaskJob();
            timer = new Timer(true);
            timer.schedule(timerTask, TIEMPO_CIERRE);
        }

        void stop() {
            Log.i("Main", "stop");
            timerTask.cancel();
            timer.cancel();
        }

        private void doTask() {
            Log.i("Main", "session expired");
            delegado.cerrarSesion();
            stop();
        }
    }

}