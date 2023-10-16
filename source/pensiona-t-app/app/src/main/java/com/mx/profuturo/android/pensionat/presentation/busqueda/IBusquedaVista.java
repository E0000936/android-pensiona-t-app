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
package com.mx.profuturo.android.pensionat.presentation.busqueda;

import com.mx.profuturo.android.pensionat.domain.model.Poliza;

import java.util.List;

public interface IBusquedaVista {
    void habilitarBotonBuscar(boolean b);

    void iniciarBusquedaError(String titulo, String mensaje);

    void iniciarBusquedaExitoso(List<Poliza> polizaList, boolean esCasoCerrado);

    void mostrarErrorValidacion();

    void validacionExitosaCampo(String valorBusqueda);

    void mostrarDialogoDatosInsuficientes(Boolean valor);
}
