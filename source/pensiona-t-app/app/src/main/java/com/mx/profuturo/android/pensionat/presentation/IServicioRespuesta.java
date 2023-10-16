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
package com.mx.profuturo.android.pensionat.presentation;

import com.mx.profuturo.android.pensionat.utils.Constantes;

import org.json.JSONObject;

public interface IServicioRespuesta {
    void obtenerRespuestaError(Constantes.TipoServicio tipoServicio,
                               String titulo,
                               String mensaje);

    void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio,
                                 JSONObject jsonObjeto);
}
