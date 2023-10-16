package com.mx.profuturo.android.pensionat.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SesionTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @InjectMocks
    Sesion sesion;
    @Mock
    Context context;
    private SharedPreferences sharedPreferences;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        sesion = new Sesion(context);
    }

    @Test
    public void validarObtenerInstancia() {
        Assert.assertEquals(MENSAJE, Sesion.getInstancia(context), Sesion.getInstancia(context));
    }

    @Test
    public void validarObtenerSharedPreferences() {
        Assert.assertEquals(MENSAJE, sesion.getPreferencias(), sharedPreferences);
    }

    @Test(expected = NullPointerException.class)
    public void testIllegalStateExceptionValidadorMotor() {
        sesion.getPathMotor();
    }

    @Test(expected = NullPointerException.class)
    public void testIllegalStateExceptionValidadorPdf() {
        sesion.getPathPdf();
    }

    @Test(expected = NullPointerException.class)
    public void testIllegalStateExceptionValidadorEmpleado() {
        sesion.getNumeroEmpleado();
    }
}
