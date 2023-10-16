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

import com.mx.profuturo.android.pensionat.domain.model.Poliza;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>VariablesGlobales</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-07
 */
public class VariablesGlobales {

    private static VariablesGlobales mInstance = null;

    public List<Poliza> polizas = new ArrayList<>();
    public Solicitante solicitante = new Solicitante();
    public boolean progressCompleto = false;
    public boolean folio = false;
    public boolean registroExitoso = false;
    public boolean acuseExitoso = false;
    public String token = "";

    protected VariablesGlobales() {
    }

    public static synchronized VariablesGlobales getInstance() {
        if (null == mInstance) {
            mInstance = new VariablesGlobales();
        }
        return mInstance;
    }

    public void limpiar() {
        polizas = new ArrayList<>();
        solicitante = new Solicitante();
        progressCompleto = false;
        registroExitoso = false;
        acuseExitoso = false;
    }
}
