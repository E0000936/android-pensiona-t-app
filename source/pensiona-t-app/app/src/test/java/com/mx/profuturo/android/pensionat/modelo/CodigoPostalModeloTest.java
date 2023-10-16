package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.CodigoPostal;
import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.Colonia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CodigoPostalModeloTest {
    private static final int ID_ENTIDAD = 0;
    private static final String CODIGO_POSTAL = "54477";
    private static final String ENTIDAD = "MÉXICO";
    private static final String CLAVE = "54477";
    private static final int ID_MUNICIPIO = 123;
    private static final String MUNICIPIO = "ATIZAPAN";
    private static final int ID_CIUDAD = 1923;
    private static final String CIUDAD = "ESTADO DE MÉXICO";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private CodigoPostal codigoPostal;

    @Before
    public void setUp() {
        codigoPostal = new CodigoPostal();
        codigoPostal.setIdEntidadFederativa(ID_ENTIDAD);
        codigoPostal.setCodigoPostal(CODIGO_POSTAL);
        codigoPostal.setEntidadFederativa(ENTIDAD);
        codigoPostal.setClaveAlfanumericaEntidad(CLAVE);
        codigoPostal.setIdMunicipio(ID_MUNICIPIO);
        codigoPostal.setMunicipio(MUNICIPIO);
        codigoPostal.setIdCiudad(ID_CIUDAD);
        codigoPostal.setCiudad(CIUDAD);
        codigoPostal.setListaColonias(new ArrayList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID_ENTIDAD, codigoPostal.getIdEntidadFederativa());
        Assert.assertEquals(MENSAJE, CODIGO_POSTAL, codigoPostal.getCodigoPostal());
        Assert.assertEquals(MENSAJE, ENTIDAD, codigoPostal.getEntidadFederativa());
        Assert.assertEquals(MENSAJE, CLAVE, codigoPostal.getClaveAlfanumericaEntidad());
        Assert.assertEquals(MENSAJE, ID_MUNICIPIO, codigoPostal.getIdMunicipio());
        Assert.assertEquals(MENSAJE, MUNICIPIO, codigoPostal.getMunicipio());
        Assert.assertEquals(MENSAJE, ID_CIUDAD, codigoPostal.getIdCiudad());
        Assert.assertEquals(MENSAJE, CIUDAD, codigoPostal.getCiudad());
        Assert.assertEquals(MENSAJE, new ArrayList<Colonia>(), codigoPostal.getListaColonias());
    }
}
