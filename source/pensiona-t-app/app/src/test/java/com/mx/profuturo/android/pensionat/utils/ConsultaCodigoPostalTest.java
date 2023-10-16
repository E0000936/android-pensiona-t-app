package com.mx.profuturo.android.pensionat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ConsultaCodigoPostalTest {
    private static final String CODIGO_POSTAL = "54477";
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    Context context;
    @Mock
    Cursor cursor;
    @Mock
    ContentResolver contentResolver;

    @Before
    public void initMocks() {
        //Esto inicializará los mock anotados
        MockitoAnnotations.initMocks(this);
        contentResolver = Mockito.mock(ContentResolver.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionConsulta() {
        new ConsultaMotorCodigosPostales();
    }


    @Test
    public void testObtenerCursorMotor() {
        Uri uriMock = Mockito.mock(Uri.class);
        Mockito.when(context.getContentResolver()).thenReturn(contentResolver);
        Mockito.when(ConsultaMotorCodigosPostales.consultaCodigosPostales(context, uriMock)).thenReturn(cursor);
        Assert.assertEquals(MENSAJE, ConsultaMotorCodigosPostales.consultaCodigosPostales(context, uriMock), cursor);
    }
}
