package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Banco;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BancoModeloTest {
    private static final Integer ID_BANCO = 0;
    private static final String DESCRIPCION = "BBVA BANCOMER";
    private static final String TIPO_CUENTA = "DEPOSITO EN CUENTA BANCARIA";
    private static final String CLABE = "012060012243510292";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Banco banco;

    @Before
    public void setUp() {
        banco = new Banco();
        banco.setId(ID_BANCO);
        banco.setDescripcion(DESCRIPCION);
        banco.setTipoCuenta(TIPO_CUENTA);
        banco.setClabe(CLABE);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID_BANCO, banco.getId());
        Assert.assertEquals(MENSAJE, DESCRIPCION, banco.getDescripcion());
        Assert.assertEquals(MENSAJE, TIPO_CUENTA, banco.getTipoCuenta());
        Assert.assertEquals(MENSAJE, CLABE, banco.getClabe());
    }
}
