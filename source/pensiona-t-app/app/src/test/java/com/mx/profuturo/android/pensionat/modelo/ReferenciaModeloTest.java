package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Referencia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReferenciaModeloTest {
    private static final String ID_PARENTESCO = "1";
    private static final String PARENTESCO = "HERMANO";
    private static final String NOMBRE = "MIGUEL";
    private static final String APPATERNO = "GOMEZ";
    private static final String APMATERNO = "TORRES";
    private static final String TELEFONO = "53277833928";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Referencia referencia;

    @Before
    public void setUp() {
        referencia = new Referencia();
        referencia.setNombre(NOMBRE);
        referencia.setApPaterno(APPATERNO);
        referencia.setApMaterno(APMATERNO);
        referencia.setDescParentesco(PARENTESCO);
        referencia.setIdParentesco(ID_PARENTESCO);
        referencia.setCelular(TELEFONO);
        referencia.setTelefonoCasa(TELEFONO);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, NOMBRE, referencia.getNombre());
        Assert.assertEquals(MENSAJE, APPATERNO, referencia.getApPaterno());
        Assert.assertEquals(MENSAJE, APMATERNO, referencia.getApMaterno());
        Assert.assertEquals(MENSAJE, PARENTESCO, referencia.getDescParentesco());
        Assert.assertEquals(MENSAJE, ID_PARENTESCO, referencia.getIdParentesco());
        Assert.assertEquals(MENSAJE, TELEFONO, referencia.getCelular());
        Assert.assertEquals(MENSAJE, TELEFONO, referencia.getTelefonoCasa());
    }
}
