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
package com.mx.profuturo.android.pensionat.data.external.web;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * <h1>VolleySingleton</h1>
 * Clase que configura una única instancia de RequestQueue que durará toda la vida útil de la aplicación.
 * encapsulando RequestQueue y otras funciones de Volley.
 * Un concepto clave es que RequestQueue debe crear una instancia con el contexto de la aplicación,
 * no con un contexto de actividad.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-11
 */
public final class VolleySingleton {
    private static VolleySingleton instancia;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context) {
        requestQueue = getRequestQueue(context);
    }

    public static synchronized VolleySingleton getInstancia(Context context) {
        if (instancia == null) {
            instancia = new VolleySingleton(context);
        }
        return instancia;
    }

    private RequestQueue getRequestQueue(Context contexto) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestqueue(Request<T> request) {
        requestQueue.add(request);
    }

}
