package com.mx.profuturo.android.pensionat.presentation.busqueda;

import com.mx.profuturo.android.pensionat.domain.model.Componente;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ComponenteFamiliarAdapterTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private List<Componente> componentes;
    @Mock
    private ComponenteFamiliarAdapter.ComponenteFamiliarInterface delegado;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        delegado = Mockito.mock(ComponenteFamiliarAdapter.ComponenteFamiliarInterface.class);
        componentes = new ArrayList<>();
        Componente componente = new Componente();
        componentes.add(componente);
    }

    @Test
    public void testCount() {
        ComponenteFamiliarAdapter adapter = new ComponenteFamiliarAdapter(componentes, delegado);
        Assert.assertEquals(MENSAJE, 1, adapter.getItemCount());
    }
}
