package com.mx.profuturo.android.pensionat.presentation.busqueda;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import android.content.Context;
import android.view.View;

import com.mx.profuturo.android.pensionat.domain.model.Grupo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


public class GrupoFamiliarAdaptadorTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";
    private List<Grupo> grupos;
    @Mock
    private GrupoFamiliarAdaptador.GrupoFamiliarInterfaz delegado;
    @Mock
    private Context context;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        delegado = Mockito.mock(GrupoFamiliarAdaptador.GrupoFamiliarInterfaz.class);
        context = Mockito.mock(Context.class);
        grupos = new ArrayList<>();
        Grupo grupo = new Grupo();
        grupos.add(grupo);
    }

    @Test
    public void testCount() {
        GrupoFamiliarAdaptador adapter = new GrupoFamiliarAdaptador(context, grupos, delegado);
        Assert.assertEquals(MENSAJE, 1, adapter.getCount());
    }

    @Test
    public void testGetItem() {
        GrupoFamiliarAdaptador adapter = new GrupoFamiliarAdaptador(context, grupos, delegado);
        Assert.assertEquals(MENSAJE, POSITION_NONE, adapter.getItemPosition(0));
    }

    @Test
    public void testValidarView() {
        View view = Mockito.mock(View.class);
        GrupoFamiliarAdaptador adapter = new GrupoFamiliarAdaptador(context, grupos, delegado);
        Assert.assertTrue(MENSAJE_EXITO, adapter.isViewFromObject(view, view));
    }

    @Test
    public void testValidarTituloVacio() {
        GrupoFamiliarAdaptador adapter = new GrupoFamiliarAdaptador(context, grupos, delegado);
        Assert.assertEquals(MENSAJE, "", adapter.getPageTitle(0));
    }
}
