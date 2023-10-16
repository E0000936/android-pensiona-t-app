package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.CustomProgressModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomProgressModelTest {
    private static final int SECCION = 2;
    private static final String NOMBRE = "DATOS BANCARIOS";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private CustomProgressModel customProgressModel;

    @Before
    public void setUp() {
        customProgressModel = new CustomProgressModel(SECCION, NOMBRE);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, SECCION, customProgressModel.getSectionCount());
        Assert.assertEquals(MENSAJE, NOMBRE, customProgressModel.getLabelName());
    }
}
