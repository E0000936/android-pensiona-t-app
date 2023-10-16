package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Expediente;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpedienteModeloTest {
    private static final String POLIZA = "20190011245";
    private static final Integer ID_GRUPO_PAGO = 1;
    private static final String ID_BENEFICIARIO_OFERTA = "49194";
    private static final Integer ID_BENEFICIARIO_POLIZA = 1;
    private static final String PARENTESCO = "HERMANO";
    private static final String NOMBRE = "MIGUEL";
    private static final String APPATERNO = "GOMEZ";
    private static final String APMATERNO = "TORRES";
    private static final String NSS = "90876851792";
    private static final String PETICION = "TRAMITE";
    private static final Integer ID_PETICION = 1;
    private static final Integer ID_TRAMITE = 80;
    private static final String TRAMITE = "B ENDOSOS IMS";
    private static final Integer ID_SUBTRAMITE = 4;
    private static final Integer ID_PARAM_ENVIO = 90896;
    private static final String SUBTRAMITE = "BS01 SEGUNDAS NUPCIAS";
    private static final String OBSERVACIONES = "COMENTARIOS DE PRUEBA";
    private static final String REGIMEN = "IMSS";
    private static final Integer ID_REGIMEN = 2980;
    private static final String FOLIO_MIT = "5052019000181";
    private static final String EXCEPCION = "LA PERSONA NO TIENE AMBAS MANOS";
    private static final Integer ID_EXCEPCION = 1;
    private static final Integer ID_SEXO = 1;
    private static final String SEXO = "M";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Expediente expediente;

    @Before
    public void setUp() {
        expediente = new Expediente();
        expediente.setPoliza(POLIZA);
        expediente.setGrupoPago(ID_GRUPO_PAGO);
        expediente.setIdBeneficiarioOferta(ID_BENEFICIARIO_OFERTA);
        expediente.setIdBeneficiarioPoliza(ID_BENEFICIARIO_POLIZA);
        expediente.setParentescoSolicitante(PARENTESCO);
        expediente.setNombreTitular(NOMBRE);
        expediente.setApPaternoTitular(APPATERNO);
        expediente.setApMaternoTitular(APMATERNO);
        expediente.setNss(NSS);
        expediente.setPeticion(PETICION);
        expediente.setIdPeticion(ID_PETICION);
        expediente.setIdTramite(ID_TRAMITE);
        expediente.setTramite(TRAMITE);
        expediente.setIdSubtramite(ID_SUBTRAMITE);
        expediente.setIdParamEnvio(ID_PARAM_ENVIO);
        expediente.setSubtramite(SUBTRAMITE);
        expediente.setObservaciones(OBSERVACIONES);
        expediente.setRegimen(REGIMEN);
        expediente.setIdTipoRegimen(ID_REGIMEN);
        expediente.setFolioMit(FOLIO_MIT);
        expediente.setExcepcion(EXCEPCION);
        expediente.setIdExcepcion(ID_EXCEPCION);
        expediente.setIdSexo(ID_SEXO);
        expediente.setSexo(SEXO);
    }

    @Test
    public void testCatalogoDetalles() {
        Assert.assertEquals(MENSAJE, POLIZA, expediente.getPoliza());
        Assert.assertEquals(MENSAJE, ID_GRUPO_PAGO, expediente.getGrupoPago());
        Assert.assertEquals(MENSAJE, ID_BENEFICIARIO_OFERTA, expediente.getIdBeneficiarioOferta());
        Assert.assertEquals(MENSAJE, ID_BENEFICIARIO_POLIZA, expediente.getIdBeneficiarioPoliza());
        Assert.assertEquals(MENSAJE, PARENTESCO, expediente.getParentescoSolicitante());
        Assert.assertEquals(MENSAJE, NOMBRE, expediente.getNombreTitular());
        Assert.assertEquals(MENSAJE, APPATERNO, expediente.getApPaternoTitular());
        Assert.assertEquals(MENSAJE, APMATERNO, expediente.getApMaternoTitular());
        Assert.assertEquals(MENSAJE, NSS, expediente.getNss());
        Assert.assertEquals(MENSAJE, PETICION, expediente.getPeticion());
        Assert.assertEquals(MENSAJE, ID_PETICION, expediente.getIdPeticion());
        Assert.assertEquals(MENSAJE, ID_TRAMITE, expediente.getIdTramite());
        Assert.assertEquals(MENSAJE, TRAMITE, expediente.getTramite());
        Assert.assertEquals(MENSAJE, ID_SUBTRAMITE, expediente.getIdSubtramite());
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO, expediente.getIdParamEnvio());
        Assert.assertEquals(MENSAJE, SUBTRAMITE, expediente.getSubtramite());
        Assert.assertEquals(MENSAJE, OBSERVACIONES, expediente.getObservaciones());
        Assert.assertEquals(MENSAJE, REGIMEN, expediente.getRegimen());
        Assert.assertEquals(MENSAJE, ID_REGIMEN, expediente.getIdTipoRegimen());
        Assert.assertEquals(MENSAJE, FOLIO_MIT, expediente.getFolioMit());
        Assert.assertEquals(MENSAJE, EXCEPCION, expediente.getExcepcion());
        Assert.assertEquals(MENSAJE, ID_EXCEPCION, expediente.getIdExcepcion());
        Assert.assertEquals(MENSAJE, ID_SEXO, expediente.getIdSexo());
        Assert.assertEquals(MENSAJE, SEXO, expediente.getSexo());
    }
}