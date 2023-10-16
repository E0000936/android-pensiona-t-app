package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Componente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class GrupoModeloTest {
    private static final Integer ID = 0;
    private static final String NUMERO = "1053376";
    private static final String SEXO = "F";
    private static final Integer ID_SEXO = 1;
    private static final String AP_PATERNO = "AGUIRRE";
    private static final String AP_MATERNO = "RODRIGUEZ";
    private static final String NOMBRE = "CLAUDIA";
    private static final String TIPO_REGIMEN = "IMSS";
    private static final Integer ID_REGIMEN = 605;
    private static final String TIPO_PENSION = "AS";
    private static final String ESTATUS = "VIGENTE";
    private static final String NSS = "90876851792";
    private static final String FOLIO_OFERTA = "31902003518";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Grupo grupo;

    @Before
    public void setUp() {
        grupo = new Grupo();
        grupo.setIdOferta(ID);
        grupo.setNumeroOferta(NUMERO);
        grupo.setSexoOferta(SEXO);
        grupo.setIdSexoOferta(ID_SEXO);
        grupo.setApPaternoOferta(AP_PATERNO);
        grupo.setApMaternoOferta(AP_MATERNO);
        grupo.setNombreOferta(NOMBRE);
        grupo.setIdTipoRegimenOferta(ID_REGIMEN);
        grupo.setTipoPensionOferta(TIPO_PENSION);
        grupo.setEstatusOferta(ESTATUS);
        grupo.setDescRegimenOferta(TIPO_REGIMEN);
        grupo.setNssOferta(NSS);
        grupo.setFolioOferta(FOLIO_OFERTA);

        grupo.setIdGrupoPago(ID);
        grupo.setGrupoPago(ID);
        grupo.setSexoPoliza(SEXO);
        grupo.setIdSexoPol(ID_SEXO);
        grupo.setApPaternoPoliza(AP_PATERNO);
        grupo.setApMaternoPoliza(AP_MATERNO);
        grupo.setNombrePoliza(NOMBRE);
        grupo.setNssPoliza(NSS);

        grupo.setNumRenta(NUMERO);
        grupo.setApPaternoTc(AP_PATERNO);
        grupo.setApMaternoTc(AP_MATERNO);
        grupo.setNombreTc(NOMBRE);
        grupo.setBeneficiarios(new RealmList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID, grupo.getIdOferta());
        Assert.assertEquals(MENSAJE, NUMERO, grupo.getNumeroOferta());
        Assert.assertEquals(MENSAJE, SEXO, grupo.getSexoOferta());
        Assert.assertEquals(MENSAJE, ID_SEXO, grupo.getIdSexoOferta());
        Assert.assertEquals(MENSAJE, AP_PATERNO, grupo.getApPaternoOferta());
        Assert.assertEquals(MENSAJE, AP_MATERNO, grupo.getApMaternoOferta());
        Assert.assertEquals(MENSAJE, NOMBRE, grupo.getNombreOferta());
        Assert.assertEquals(MENSAJE, ID_REGIMEN, grupo.getIdTipoRegimenOferta());
        Assert.assertEquals(MENSAJE, TIPO_PENSION, grupo.getTipoPensionOferta());
        Assert.assertEquals(MENSAJE, ESTATUS, grupo.getEstatusOferta());
        Assert.assertEquals(MENSAJE, TIPO_REGIMEN, grupo.getDescRegimenOferta());
        Assert.assertEquals(MENSAJE, NSS, grupo.getNssOferta());
        Assert.assertEquals(MENSAJE, FOLIO_OFERTA, grupo.getFolioOferta());

        Assert.assertEquals(MENSAJE, ID, grupo.getIdGrupoPago());
        Assert.assertEquals(MENSAJE, ID, grupo.getGrupoPago());
        Assert.assertEquals(MENSAJE, SEXO, grupo.getSexoPoliza());
        Assert.assertEquals(MENSAJE, ID_SEXO, grupo.getIdSexoPol());
        Assert.assertEquals(MENSAJE, AP_PATERNO, grupo.getApPaternoPoliza());
        Assert.assertEquals(MENSAJE, AP_MATERNO, grupo.getApMaternoPoliza());
        Assert.assertEquals(MENSAJE, NOMBRE, grupo.getNombrePoliza());
        Assert.assertEquals(MENSAJE, NSS, grupo.getNssPoliza());

        Assert.assertEquals(MENSAJE, NUMERO, grupo.getNumRenta());
        Assert.assertEquals(MENSAJE, AP_PATERNO, grupo.getApPaternoTc());
        Assert.assertEquals(MENSAJE, AP_MATERNO, grupo.getApMaternoTc());
        Assert.assertEquals(MENSAJE, NOMBRE, grupo.getNombreTc());
        Assert.assertEquals(MENSAJE, new RealmList<Componente>(), grupo.getBeneficiarios());
    }
}
