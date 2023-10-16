package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.Colonia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColoniaModeloTest {
    private static final int ID_COLONIA = 145;
    private static final String NOMBRE = "SAN ISIDRO LA PAZ";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Colonia colonia;

    @Before
    public void setUp() {
        colonia = new Colonia();
        colonia.setIdColonia(ID_COLONIA);
        colonia.setNombre(NOMBRE);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID_COLONIA, colonia.getIdColonia());
        Assert.assertEquals(MENSAJE, NOMBRE, colonia.getNombre());
    }
}
