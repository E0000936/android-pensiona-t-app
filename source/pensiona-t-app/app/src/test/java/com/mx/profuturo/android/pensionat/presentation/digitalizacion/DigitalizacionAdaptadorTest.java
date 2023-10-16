package com.mx.profuturo.android.pensionat.presentation.digitalizacion;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class DigitalizacionAdaptadorTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    @Mock
    Context context;

    private List<Digitalizacion> digitalizaciones;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        digitalizaciones = new ArrayList<>();
        Digitalizacion digitalizacion = new Digitalizacion();
        digitalizaciones.add(digitalizacion);
    }

    @Test
    public void testPosicionSeleccionada() {
        DigitalizacionAdaptador adapter = new DigitalizacionAdaptador(digitalizaciones, context);
        adapter.posicionSeleccionada = 1;
        Assert.assertEquals(MENSAJE, 1, adapter.posicionSeleccionada);
    }

    @Test
    public void testObtenerItemView() {
        DigitalizacionAdaptador adapter = new DigitalizacionAdaptador(digitalizaciones, context);
        Assert.assertEquals(MENSAJE, 0, adapter.getItemViewType(0));
    }

    @Test
    public void testGetItemCount() {
        DigitalizacionAdaptador adapter = new DigitalizacionAdaptador(digitalizaciones, context);
        Assert.assertEquals(MENSAJE, 1, adapter.getItemCount());
    }
}
