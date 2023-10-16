package com.mx.profuturo.android.pensionat.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Creado por Jose Antonio Acevedo Trejo en 20/08/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class ChecksumTest {
    private static final String NOMBRE = "/storage/emulated/0/TramitesDigitales/SOLICITUD IDENTIFICACION PENSIONADO.pdf";
    private static final String MENSAJE = "validacion de checksum";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalStateException.class)
    public void testIllegalStateExceptionValidador() {
        new Checksum();
    }

    @Test
    public void obtenerMD5() {
        Assert.assertEquals(MENSAJE, Checksum.obtenerMD5(NOMBRE), "");
    }

    @Test
    public void validarNombreNull() {
        Assert.assertEquals(MENSAJE, Checksum.obtenerMD5(null), "");
    }

    @Test
    public void validarCreacionCadena() {
        byte[] bytes = "Any String you want".getBytes();
        Assert.assertEquals(MENSAJE, Checksum.convertirBytesEnCadena(bytes),
                "416E7920537472696E6720796F752077616E74");
    }

    @Test
    public void validarChecksum() {
        FileInputStream fileInputStream = Mockito.mock(FileInputStream.class);
        try {
            Mockito.when(fileInputStream.read(new byte[1024])).thenReturn(0).thenReturn(-1);
            String respuesta = Checksum.obtenerByteArray(fileInputStream);
            Assert.assertEquals(MENSAJE, respuesta, "D41D8CD98F00B204E9800998ECF8427E");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}