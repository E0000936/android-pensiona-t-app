package com.mx.profuturo.android.pensionat.presentation.formulario;

import org.junit.Assert;
import org.junit.Test;

public class DatosBancariosFragmentTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";

    @Test
    public void validarConversionCadenaLongitud() {
        DatosBancariosFragment datosBancariosFragment = new DatosBancariosFragment();
        Assert.assertEquals(MENSAJE, 14, datosBancariosFragment.convertirCadenaArrayEnteros("26335163513517").length);
    }

    @Test
    public void validarConversionCadenaCorrecto() {
        DatosBancariosFragment datosBancariosFragment = new DatosBancariosFragment();
        Assert.assertNotNull(MENSAJE_EXITO, datosBancariosFragment.convertirCadenaArrayEnteros("26335163513d"));
    }

    @Test
    public void validarConversionCadenaVacia() {
        DatosBancariosFragment datosBancariosFragment = new DatosBancariosFragment();
        Assert.assertEquals(MENSAJE, 0, datosBancariosFragment.convertirCadenaArrayEnteros("").length);
    }
}
