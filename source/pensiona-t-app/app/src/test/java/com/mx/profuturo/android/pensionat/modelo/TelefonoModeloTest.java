package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Telefono;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TelefonoModeloTest {
    private static final String NUMERO = "4521360250";
    private static final String TIPO = "CASA";
    private static final Integer IDTIPO = 159;
    private static final String DISPONIBLE_DESDE = "10:00 AM";
    private static final String DISPONIBLE_HASTA = "1:00 PM";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Telefono telefono;

    @Before
    public void setUp() {
        telefono = new Telefono();
        telefono.setNumero(NUMERO);
        telefono.setTipo(TIPO);
        telefono.setIdTipo(IDTIPO);
        telefono.setDisponibleDesde(DISPONIBLE_DESDE);
        telefono.setDisponibleHasta(DISPONIBLE_HASTA);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, NUMERO, telefono.getNumero());
        Assert.assertEquals(MENSAJE, TIPO, telefono.getTipo());
        Assert.assertEquals(MENSAJE, IDTIPO, telefono.getIdTipo());
        Assert.assertEquals(MENSAJE, DISPONIBLE_DESDE, telefono.getDisponibleDesde());
        Assert.assertEquals(MENSAJE, DISPONIBLE_HASTA, telefono.getDisponibleHasta());
    }
}