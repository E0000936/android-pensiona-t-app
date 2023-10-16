package com.mx.profuturo.android.pensionat.utils;

import android.content.Context;

import com.android.volley.VolleyError;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class UtilidadesTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    Context context;

    @Before
    public void initMocks() {
        //Esto inicializará los mock anotados
        MockitoAnnotations.initMocks(this);
        context = Mockito.mock(Context.class);
        Mockito.when(context.getString(R.string.error_conexion)).thenReturn("Ocurrió un problema");
        Mockito.when(context.getString(R.string.error_conexion_titulo)).thenReturn("ERROR DE CONEXIÓN");
    }

    @Test
    public void testObtenerTiempoFormateado() {
        Assert.assertNotNull(Utilidades.obtenerTiempoConFormato(0, 0));
    }

    @Test
    public void testObtenerTiempoFormateadoPM() {
        Assert.assertEquals(MENSAJE, "12:23 PM", Utilidades.obtenerTiempoConFormato(12, 23));
    }

    @Test
    public void testObtenerTiempoFormateado24H() {
        Assert.assertEquals(MENSAJE, "11:10 PM", Utilidades.obtenerTiempoConFormato(23, 10));
    }

    @Test
    public void testObtenerTiempoFormateadoAM() {
        Assert.assertEquals(MENSAJE, "12:00 AM", Utilidades.obtenerTiempoConFormato(0, 0));
    }

    @Test
    public void testObtenerFechaFormateada() {
        Assert.assertEquals(MENSAJE, "12/11/1980", Utilidades.obtenerFechaConFormato(1980, 10, 12));
    }

    @Test
    public void testObtenerFechaFormateadaDos() {
        Assert.assertEquals(MENSAJE, "02/02/1997", Utilidades.obtenerFechaConFormato(1997, 1, 2));
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionUtilidades() {
        new Utilidades();
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionManejoArchivos() {
        new ManejoArchivosHelper();
    }

    @Test
    public void testObtenerBase64NoNulo() {
        Assert.assertNotNull(ManejoArchivosHelper.obtenerBase64(null));
    }

    @Test
    public void testObtenerBase64ArchivoNoEncontrado() {
        Assert.assertEquals(MENSAJE, "", ManejoArchivosHelper.obtenerBase64(""));
    }

    @Test
    public void testCrearDirectorioEncontrado() {
        File mockitoFile = Mockito.mock(File.class);
        Mockito.when(mockitoFile.exists()).thenReturn(true);
        Mockito.when(mockitoFile.getAbsolutePath()).thenReturn("prueba1");
        Assert.assertEquals(MENSAJE, "prueba1/", ManejoArchivosHelper.crearDirectorio(mockitoFile));
    }

    @Test
    public void testODirectorioNCreado() {
        File mockitoFile = Mockito.mock(File.class);
        Mockito.when(mockitoFile.exists()).thenReturn(false);
        Assert.assertTrue(ManejoArchivosHelper.crearDirectorio(mockitoFile).isEmpty());
    }

    @Test
    public void testValidarParseoRespuestaError() {
        String titulo = "ERROR DE CONEXIÓN";
        String msj = "Ocurrió un problema";
        int code = 200;
        ErrorServicio errorServicio = new ErrorServicio(code, titulo, msj);
        ErrorServicio respuesta = Utilidades.obtenerErrorVolley(context, new VolleyError(), msj);
        Assert.assertEquals(MENSAJE, errorServicio.getEstatus(), respuesta.getEstatus());
        Assert.assertEquals(MENSAJE, errorServicio.getMensaje(), respuesta.getMensaje());
        Assert.assertEquals(MENSAJE, errorServicio.getTitulo(), respuesta.getTitulo());
    }
}
