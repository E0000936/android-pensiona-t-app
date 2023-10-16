package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorServicioTest {
    private static final String TITULO = "Error de conexión";
    private static final String MENSAJE_ERROR = "Ocurrió un error al realizar la petición";
    private static final int ERROR_CODE = 401;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private ErrorServicio errorServicio;

    @Before
    public void setUp() {
        errorServicio = new ErrorServicio(ERROR_CODE, TITULO, MENSAJE_ERROR);
    }

    @Test
    public void testErrorServicio() {
        Assert.assertEquals(MENSAJE, ERROR_CODE, errorServicio.getEstatus());
        Assert.assertEquals(MENSAJE, TITULO, errorServicio.getTitulo());
        Assert.assertEquals(MENSAJE, MENSAJE_ERROR, errorServicio.getMensaje());
    }
}
