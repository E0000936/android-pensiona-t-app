package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Peticion;
import com.mx.profuturo.android.pensionat.domain.model.Tramite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class PeticionModeloTest {
    private static final Integer ID = 1;
    private static final String NOMBRE = "TRAMITE";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Peticion peticion;

    @Before
    public void setUp() {
        peticion = new Peticion();
        peticion.setId(ID);
        peticion.setNombre(NOMBRE);
        peticion.setTramites(new RealmList<>());
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, ID, peticion.getId());
        Assert.assertEquals(MENSAJE, NOMBRE, peticion.getNombre());
        Assert.assertEquals(MENSAJE, new RealmList<Tramite>(), peticion.getTramites());
    }
}
