package com.mx.profuturo.android.pensionat.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class ConstantesTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Before
    public void initMocks() {
        //Esto inicializará los mock anotados
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validarEnumTipoTelefono() {
        Assert.assertEquals(MENSAJE, "CASA", Constantes.TipoTelefono.CASA.name());
        Assert.assertEquals(MENSAJE, "MOVIL", Constantes.TipoTelefono.MOVIL.name());
        Assert.assertEquals(MENSAJE, "RECADOS", Constantes.TipoTelefono.RECADOS.name());
    }

    @Test
    public void validarClaseTamanoCampos() {
        Assert.assertEquals(MENSAJE, 18, Constantes.TamanoCampos.CURP);
        Assert.assertEquals(MENSAJE, 13, Constantes.TamanoCampos.POLIZA);
        Assert.assertEquals(MENSAJE, 11, Constantes.TamanoCampos.NSS);
        Assert.assertEquals(MENSAJE, 10, Constantes.TamanoCampos.TELEFONO);
        Assert.assertEquals(MENSAJE, 5, Constantes.TamanoCampos.CP);
        Assert.assertEquals(MENSAJE, 16, Constantes.TamanoCampos.NUMERODETARJETA);
        Assert.assertEquals(MENSAJE, 18, Constantes.TamanoCampos.CLABE);
    }

    @Test
    public void validarClaseConfiguraciones() {
        Assert.assertEquals(MENSAJE, 50000, Constantes.Configuraciones.MILISEGUNDOS_REINTENTO);
        Assert.assertEquals(MENSAJE, 1, Constantes.Configuraciones.REINTENTOS);
    }

    @Test
    public void validarClaseCatalogos() {
        Assert.assertEquals(MENSAJE, 1000, Constantes.Catalogos.BANCO);
        Assert.assertEquals(MENSAJE, 2000, Constantes.Catalogos.NACIONALIDAD);
        Assert.assertEquals(MENSAJE, 3000, Constantes.Catalogos.PARENTESCO);
        Assert.assertEquals(MENSAJE, 6000, Constantes.Catalogos.PAIS);
        Assert.assertEquals(MENSAJE, 8000, Constantes.Catalogos.PROFESION);
        Assert.assertEquals(MENSAJE, 10000, Constantes.Catalogos.EXCEPCION);
    }
}
