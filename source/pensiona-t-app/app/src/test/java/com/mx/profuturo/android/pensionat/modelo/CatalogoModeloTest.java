package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Catalogo;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class CatalogoModeloTest {
    private static final Integer ID_CATALOGO = 7000;
    private static final String DESCRIPCION = "LINEA DE NEGOCIO";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Catalogo catalogo;

    @Before
    public void setUp() {
        catalogo = new Catalogo();
        catalogo.setId(ID_CATALOGO);
        catalogo.setDescripcion(DESCRIPCION);
        catalogo.setElementos(new RealmList<>());
    }

    @Test
    public void testCatalogoDetalles() {
        Assert.assertEquals(MENSAJE, ID_CATALOGO, catalogo.getId());
        Assert.assertEquals(MENSAJE, DESCRIPCION, catalogo.getDescripcion());
        Assert.assertEquals(MENSAJE, new RealmList<Categoria>(), catalogo.getElementos());
    }
}
