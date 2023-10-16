package com.mx.profuturo.android.pensionat.utils;

import com.mx.profuturo.android.pensionat.domain.model.Solicitante;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Creado por Jose Antonio Acevedo Trejo en 21/08/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
public class VariablesGlobalesTest {

    private static final String MENSAJE = "variables globales";


    @Test
    public void getInstance() {
        VariablesGlobales.getInstance().polizas = new ArrayList<>();
        Assert.assertEquals(MENSAJE, VariablesGlobales.getInstance().polizas, new ArrayList<>());

        Solicitante solicitante = new Solicitante();
        VariablesGlobales.getInstance().solicitante = solicitante;
        Assert.assertEquals(MENSAJE, VariablesGlobales.getInstance().solicitante, solicitante);

        VariablesGlobales.getInstance().progressCompleto = false;
        Assert.assertFalse(MENSAJE, VariablesGlobales.getInstance().progressCompleto);

        VariablesGlobales.getInstance().folio = true;
        Assert.assertTrue(MENSAJE, VariablesGlobales.getInstance().folio);

        VariablesGlobales.getInstance().registroExitoso = true;
        Assert.assertTrue(MENSAJE, VariablesGlobales.getInstance().registroExitoso);

        VariablesGlobales.getInstance().acuseExitoso = true;
        Assert.assertTrue(MENSAJE, VariablesGlobales.getInstance().acuseExitoso);
    }

    @Test
    public void limpiar() {
        VariablesGlobales.getInstance().limpiar();
    }
}