package com.mx.profuturo.android.pensionat.presentation.digitalizacion;

import static org.mockito.Mockito.spy;

import android.content.Context;
import android.view.View;

import com.mx.profuturo.android.pensionat.domain.model.Captura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CapturaAdaptadorTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";
    @Mock
    Context context;
    private List<Captura> capturas;
    private Captura captura;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        captura = new Captura();
        captura.setIdTipoDoc(1);
        captura.setNumero(1);
        captura.setEstatus(0);
        captura.setDescripcion("INE ANVERSO");
        captura.setIdParamEnvioProceso(120495);
        captura.setCargaExitosa(0);
        capturas = new ArrayList<>();
        capturas.add(captura);
    }

    @Test
    public void testCount() {
        CapturasAdaptador adapter = new CapturasAdaptador(context, capturas);
        Assert.assertEquals(MENSAJE, 1, adapter.getCount());
    }

    @Test
    public void testGet() {
        CapturasAdaptador adapter = new CapturasAdaptador(context, capturas);
        Assert.assertEquals(MENSAJE, captura, adapter.getItem(0));
    }

    @Test
    public void testGetId() {
        CapturasAdaptador adapter = new CapturasAdaptador(context, capturas);
        Assert.assertEquals(MENSAJE, 0, adapter.getItemId(0));
    }

    @Test
    public void testGetViewNotNull() {
        View view = Mockito.mock(View.class);
        CapturasAdaptador adapter = spy(new CapturasAdaptador(context, capturas));
        Assert.assertNotNull(MENSAJE_EXITO, adapter.getView(0, view, null));
    }
}
