package com.mx.profuturo.android.pensionat.presentation.busqueda;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Categoria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class GenericoAdaptadorTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";
    @Mock
    Context context;

    private ArrayList<Categoria> categorias;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categorias = new ArrayList<>();
        Categoria categoria = new Categoria();
        categorias.add(categoria);
    }

    @Test
    public void testPosicionSeleccionada() {
        GenericoAdaptador adapter = new GenericoAdaptador(context, categorias);
        adapter.posicionSeleccionada = 1;
        Assert.assertEquals(MENSAJE, 1, adapter.posicionSeleccionada);
    }

    @Test
    public void testGetEnableTrue() {
        GenericoAdaptador adapter = new GenericoAdaptador(context, categorias);
        Assert.assertTrue(MENSAJE_EXITO, adapter.isEnabled(1));
    }
}
