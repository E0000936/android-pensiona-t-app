package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Categoria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoriaModeloTest {
    private static final Integer ID_CATEGORIA = 1;
    private static final Integer CLAVE = 1419;
    private static final String DESCRIPCION = "01 CALCULO FIRMADO POR PENSIONADO PARA CAMBIO DE BA";
    private static final String DESC_COMPLETA = "CALCULO FIRMADO POR PENSIONADO PARA CAMBIO DE BA";
    private static final String DIGITOS_BANCO = "1213";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Categoria categoria;

    @Before
    public void setUp() {
        categoria = new Categoria();
        categoria.setId(ID_CATEGORIA);
        categoria.setClave(CLAVE);
        categoria.setDescripcion(DESCRIPCION);
        categoria.setDescCompleta(DESC_COMPLETA);
        categoria.setBancoDigitos(DIGITOS_BANCO);
    }

    @Test
    public void testCategoriaDetalles() {
        Assert.assertEquals(MENSAJE, ID_CATEGORIA, categoria.getId());
        Assert.assertEquals(MENSAJE, CLAVE, categoria.getClave());
        Assert.assertEquals(MENSAJE, DESCRIPCION, categoria.getDescripcion());
        Assert.assertEquals(MENSAJE, DESC_COMPLETA, categoria.getDescCompleta());
        Assert.assertEquals(MENSAJE, DIGITOS_BANCO, categoria.getBancoDigitos());
    }
}
