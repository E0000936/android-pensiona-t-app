package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class DigitalizacionModeloTest {
    private static final Integer ID_PARAM_ENVIO = 155969;
    private static final String AYUDA = "SE VALIDA NOMBRE, DATOS GENERALES Y VIGENCIA";
    private static final String DESCRIPCION = "IDENTIFICACION OFICIAL REVERSO";
    private static final Integer OBLIGATORIO = 1;
    private static final Integer CAPTURAS = 2;
    private static final Integer CAPTURAS_TOMADAS = 1;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Digitalizacion digitalizacion;

    @Before
    public void setUp() {
        digitalizacion = new Digitalizacion();
        digitalizacion.setIdParamEnvioProceso(ID_PARAM_ENVIO);
        digitalizacion.setAyuda(AYUDA);
        digitalizacion.setDescripcion(DESCRIPCION);
        digitalizacion.setEsObligatorio(OBLIGATORIO);
        digitalizacion.setCapturas(CAPTURAS);
        digitalizacion.setTomarCaptura(CAPTURAS_TOMADAS);
        digitalizacion.setCapturasArchivo(new RealmList<>());
    }

    @Test
    public void testCatalogoDetalles() {
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO, digitalizacion.getIdParamEnvioProceso());
        Assert.assertEquals(MENSAJE, AYUDA, digitalizacion.getAyuda());
        Assert.assertEquals(MENSAJE, DESCRIPCION, digitalizacion.getDescripcion());
        Assert.assertEquals(MENSAJE, OBLIGATORIO, digitalizacion.getEsObligatorio());
        Assert.assertEquals(MENSAJE, CAPTURAS, digitalizacion.getCapturas());
        Assert.assertEquals(MENSAJE, CAPTURAS_TOMADAS, digitalizacion.getTomarCaptura());
        Assert.assertEquals(MENSAJE, new RealmList<Captura>(), digitalizacion.getCapturasArchivo());
    }
}