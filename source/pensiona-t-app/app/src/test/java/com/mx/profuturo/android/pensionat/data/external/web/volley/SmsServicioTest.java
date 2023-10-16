package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Creado por Jose Antonio Acevedo Trejo en 22/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SmsServicioTest {
    private static final String TELEFONO = "2288389902";
    private static final String TEXTO = "Apreciable cliente: hemos registrado tu tramite con el folio %1$s";
    private static final String MENSAJE = "Los objectos deben ser iguales";
    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context contexto;
    @Spy
    @InjectMocks
    SmsServicio smsServicio;

    @Before
    public void inicializar() {
        MockitoAnnotations.initMocks(this);
        smsServicio = new SmsServicio(contexto, iServicioRespuesta);
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        JSONObject jsonObjectMock = new JSONObject();
        objetoInterno.put("telefono", TELEFONO);
        objetoInterno.put("message", TEXTO);
        jsonObjectMock.put("rqt", objetoInterno);
        JSONObject parametros = smsServicio.obtenerParametros(TELEFONO, TEXTO);
        Assert.assertEquals(MENSAJE, parametros.toString(), jsonObjectMock.toString());
    }
}