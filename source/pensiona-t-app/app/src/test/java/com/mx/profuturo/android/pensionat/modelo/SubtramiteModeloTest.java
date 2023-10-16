package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Documento;
import com.mx.profuturo.android.pensionat.domain.model.Subtramite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import io.realm.RealmList;

public class SubtramiteModeloTest {
    private static final Integer ID = 359;
    private static final String TRAMITE_SUCURSAL = "BS01 SEGUNDAS NUPCIAS";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Subtramite subtramite;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subtramite = new Subtramite();
        subtramite.setId(ID);
        subtramite.setIdParamEnvio(ID);
        subtramite.setNombre(TRAMITE_SUCURSAL);
        subtramite.setDocumentos(new RealmList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID, subtramite.getId());
        Assert.assertEquals(MENSAJE, ID, subtramite.getIdParamEnvio());
        Assert.assertEquals(MENSAJE, TRAMITE_SUCURSAL, subtramite.getNombre());
        Assert.assertEquals(MENSAJE, new RealmList<Documento>(), subtramite.getDocumentos());
    }
}
