package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Componente;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponenteModeloTest {
    private static final String ID_OFERTA = "145948";
    private static final Integer ID_POLIZA = 145948;
    private static final Integer CONSECUTIVO = 1;
    private static final String NOMBRE = "RUIZ ACUÑA LUIS MANUEL";
    private static final Integer ID_PARENTESCO = 586;
    private static final String PARENTESCO = "HIJO (A)";
    private static final Integer ID_SEXO = 652;
    private static final String SEXO = "M";
    private static final String NACIMIENTO = "31/03/2001";
    private static final Integer ID_ESTATUS = 190;
    private static final String ESTATUS = "ACTIVO";
    private static final Integer ID_GRUPO = 609391;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Componente componente;

    @Before
    public void setUp() {
        componente = new Componente();
        componente.setIdBeneficiarioOferta(ID_OFERTA);
        componente.setIdBeneficiarioPoliza(ID_POLIZA);
        componente.setConsecutivoBeneficiario(CONSECUTIVO);
        componente.setNombreCompleto(NOMBRE);
        componente.setIdParentesco(ID_PARENTESCO);
        componente.setParentesco(PARENTESCO);
        componente.setIdSexo(ID_SEXO);
        componente.setSexo(SEXO);
        componente.setFechaNacimiento(NACIMIENTO);
        componente.setIdEstatus(ID_ESTATUS);
        componente.setEstatusBeneficiario(ESTATUS);
        componente.setIdGrupoPago(ID_GRUPO);
    }

    @Test
    public void testComponenteDetalles() {
        Assert.assertEquals(MENSAJE, ID_OFERTA, componente.getIdBeneficiarioOferta());
        Assert.assertEquals(MENSAJE, ID_POLIZA, componente.getIdBeneficiarioPoliza());
        Assert.assertEquals(MENSAJE, CONSECUTIVO, componente.getConsecutivoBeneficiario());
        Assert.assertEquals(MENSAJE, NOMBRE, componente.getNombreCompleto());
        Assert.assertEquals(MENSAJE, ID_PARENTESCO, componente.getIdParentesco());
        Assert.assertEquals(MENSAJE, PARENTESCO, componente.getParentesco());
        Assert.assertEquals(MENSAJE, ID_SEXO, componente.getIdSexo());
        Assert.assertEquals(MENSAJE, SEXO, componente.getSexo());
        Assert.assertEquals(MENSAJE, NACIMIENTO, componente.getFechaNacimiento());
        Assert.assertEquals(MENSAJE, ID_ESTATUS, componente.getIdEstatus());
        Assert.assertEquals(MENSAJE, ESTATUS, componente.getEstatusBeneficiario());
        Assert.assertEquals(MENSAJE, ID_GRUPO, componente.getIdGrupoPago());
    }
}