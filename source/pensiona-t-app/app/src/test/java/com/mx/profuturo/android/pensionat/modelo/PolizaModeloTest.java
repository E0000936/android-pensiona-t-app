package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Grupo;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class PolizaModeloTest {
    private static final Integer ID = 90482;
    private static final String TIPO_REGIMEN = "IMSS";
    private static final String TIPO_PENSION = "AS";
    private static final String ESTATUS = "VIGENTE";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Poliza poliza;

    @Before
    public void setUp() {
        poliza = new Poliza();
        poliza.setIdPoliza(ID);
        poliza.setIdOferta(ID);
        poliza.setIdRegimen(ID);
        poliza.setTipoRegimen(TIPO_REGIMEN);
        poliza.setIdEstatus(ID);
        poliza.setEstatus(ESTATUS);
        poliza.setIdTipoPension(ID);
        poliza.setTipoPension(TIPO_PENSION);
        poliza.setGrupos(new RealmList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID, poliza.getIdPoliza());
        Assert.assertEquals(MENSAJE, ID, poliza.getIdOferta());
        Assert.assertEquals(MENSAJE, ID, poliza.getIdRegimen());
        Assert.assertEquals(MENSAJE, TIPO_REGIMEN, poliza.getTipoRegimen());
        Assert.assertEquals(MENSAJE, ID, poliza.getIdEstatus());
        Assert.assertEquals(MENSAJE, ESTATUS, poliza.getEstatus());
        Assert.assertEquals(MENSAJE, ID, poliza.getIdTipoPension());
        Assert.assertEquals(MENSAJE, TIPO_PENSION, poliza.getTipoPension());
        Assert.assertEquals(MENSAJE, new RealmList<Grupo>(), poliza.getGrupos());
    }
}
