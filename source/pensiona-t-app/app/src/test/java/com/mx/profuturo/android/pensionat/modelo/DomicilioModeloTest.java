package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Domicilio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DomicilioModeloTest {
    private static final String CALLE = "Adolfo López Mateos";
    private static final Integer ID_PAIS = 1;
    private static final String PAIS = "México";
    private static final String ASENTAMIENTO = "Ampliacion Los Alpes";
    private static final String ESTADO = "México";
    private static final String MUNICIPIO = "Adolfo López Mateos";
    private static final String NUM_EXT = "2009";
    private static final String NUM_INT = "";
    private static final String CP = "01010";
    private static final String CIUDAD = "México";
    private static final Integer ID_COLONIA = 1;
    private static final Integer ID_MUNICIPIO = 2;
    private static final Integer ID_CIUDAD = 3;
    private static final Integer ID_ESTADO = 4;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Domicilio domicilio;

    @Before
    public void setUp() {
        domicilio = new Domicilio();
        domicilio.setCalle(CALLE);
        domicilio.setIdPais(ID_PAIS);
        domicilio.setPais(PAIS);
        domicilio.setAsentamiento(ASENTAMIENTO);
        domicilio.setEstado(ESTADO);
        domicilio.setMunicipio(MUNICIPIO);
        domicilio.setNumExt(NUM_EXT);
        domicilio.setNumInt(NUM_INT);
        domicilio.setCp(CP);
        domicilio.setCiudad(CIUDAD);
        domicilio.setIdColonia(ID_COLONIA);
        domicilio.setIdMunicipio(ID_MUNICIPIO);
        domicilio.setIdCiudad(ID_CIUDAD);
        domicilio.setIdEstado(ID_ESTADO);
    }

    @Test
    public void testCatalogoDetalles() {
        Assert.assertEquals(MENSAJE, CALLE, domicilio.getCalle());
        Assert.assertEquals(MENSAJE, ID_PAIS, domicilio.getIdPais());
        Assert.assertEquals(MENSAJE, PAIS, domicilio.getPais());
        Assert.assertEquals(MENSAJE, ASENTAMIENTO, domicilio.getAsentamiento());
        Assert.assertEquals(MENSAJE, ESTADO, domicilio.getEstado());
        Assert.assertEquals(MENSAJE, MUNICIPIO, domicilio.getMunicipio());
        Assert.assertEquals(MENSAJE, NUM_EXT, domicilio.getNumExt());
        Assert.assertEquals(MENSAJE, NUM_INT, domicilio.getNumInt());
        Assert.assertEquals(MENSAJE, CP, domicilio.getCp());
        Assert.assertEquals(MENSAJE, CIUDAD, domicilio.getCiudad());
        Assert.assertEquals(MENSAJE, ID_COLONIA, domicilio.getIdColonia());
        Assert.assertEquals(MENSAJE, ID_MUNICIPIO, domicilio.getIdMunicipio());
        Assert.assertEquals(MENSAJE, ID_CIUDAD, domicilio.getIdCiudad());
        Assert.assertEquals(MENSAJE, ID_ESTADO, domicilio.getIdEstado());
    }
}