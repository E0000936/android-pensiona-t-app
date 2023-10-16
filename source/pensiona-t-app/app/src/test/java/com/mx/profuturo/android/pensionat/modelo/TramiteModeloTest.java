package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Subtramite;
import com.mx.profuturo.android.pensionat.domain.model.Tramite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class TramiteModeloTest {
    private static final Integer ID_TRAMITE = 88;
    private static final String NOMBRE = "B ENDOSOS IMSS";
    private static final Integer ID_TIPO = 7500;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Tramite tramite;

    @Before
    public void setUp() {
        tramite = new Tramite();
        tramite.setId(ID_TRAMITE);
        tramite.setNombre(NOMBRE);
        tramite.setIdTipoPeticion(ID_TIPO);
        tramite.setSubtramites(new RealmList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID_TRAMITE, tramite.getId());
        Assert.assertEquals(MENSAJE, NOMBRE, tramite.getNombre());
        Assert.assertEquals(MENSAJE, ID_TIPO, tramite.getIdTipoPeticion());
        Assert.assertEquals(MENSAJE, new RealmList<Subtramite>(), tramite.getSubtramites());
    }
}