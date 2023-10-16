package com.mx.profuturo.android.pensionat.presentation.busqueda;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class TipoBusquedaAdaptadorTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";
    @Mock
    Context context;

    private ArrayList<String> tipoBusqueda;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tipoBusqueda = new ArrayList<>();
        tipoBusqueda.add("poliza");
        tipoBusqueda.add("folioOferta");
    }

    @Test
    public void testPosicionSeleccionada() {
        TipoBusquedaAdaptador adapter = new TipoBusquedaAdaptador(context, tipoBusqueda);
        adapter.posicionActual = 1;
        Assert.assertEquals(MENSAJE, 1, adapter.posicionActual);
    }

    @Test
    public void testGetEnableFalse() {
        TipoBusquedaAdaptador adapter = new TipoBusquedaAdaptador(context, tipoBusqueda);
        Assert.assertFalse(MENSAJE_EXITO, adapter.isEnabled(0));
    }

    @Test
    public void testGetEnableTrue() {
        TipoBusquedaAdaptador adapter = new TipoBusquedaAdaptador(context, tipoBusqueda);
        Assert.assertTrue(MENSAJE_EXITO, adapter.isEnabled(1));
    }
}
