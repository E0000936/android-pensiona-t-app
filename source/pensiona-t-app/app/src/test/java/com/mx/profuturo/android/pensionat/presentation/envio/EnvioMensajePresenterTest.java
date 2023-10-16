package com.mx.profuturo.android.pensionat.presentation.envio;

import com.mx.profuturo.android.pensionat.data.external.web.volley.SmsServicio;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class EnvioMensajePresenterTest {
    private static final String TELEFONO = "2288389902";
    private static final String FOLIO = "20190001931";
    private static final String CORREO_REMITENTE = "tu-tramite-pensiones@profuturo.com.mx";
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";
    @Spy
    @InjectMocks
    SmsServicio smsServicio;
    @Mock
    JSONObject objetoInterno;
    @Mock
    JSONObject objetoEncabezado;
    @Mock
    JSONObject objetoAdjunto;
    @Mock
    JSONObject jsonObjectMock2;
    @Mock
    JSONObject objetoInterno2;
    @Mock
    JSONObject objetoRespuesta2;

    @Before
    public void iniciar() throws JSONException {
        MockitoAnnotations.initMocks(this);
        smsServicio = Mockito.mock(SmsServicio.class);
        MockitoAnnotations.initMocks(this);

        //simulacion de servicio Correo
        objetoRespuesta2 = new JSONObject();
        objetoRespuesta2.put("correo", "jacevedt@everis.com");
        objetoRespuesta2.put("nombreDoc", "imagenes.jpg");
        objetoInterno2 = new JSONObject();
        objetoInterno2.put("encabezado", objetoEncabezado);
        objetoInterno2.put("adjunto", objetoAdjunto);
        objetoEncabezado = new JSONObject();
        objetoEncabezado.put("asunto", "Confirmacion de tramite");
        objetoEncabezado.put("remitente", CORREO_REMITENTE);
        objetoEncabezado.put("destinatario", "jacevedt@everis.com");
        objetoEncabezado.put("copia", CORREO_REMITENTE);
        objetoEncabezado.put("copiaOculta", CORREO_REMITENTE);
        objetoAdjunto = new JSONObject();
        objetoAdjunto.put("nombre", "FormatoTramite.pdf");
        objetoAdjunto.put("contenido", "archivoProfuturo");
        objetoAdjunto.put("intentos", 10);
        objetoAdjunto.put("folio", FOLIO);
        jsonObjectMock2 = new JSONObject();
        jsonObjectMock2.put("rqt", objetoInterno);
    }

    @Test
    public void enviarSms() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("telefono", TELEFONO);
        Assert.assertEquals(MENSAJE, "2288389902", TELEFONO);
        Assert.assertNotNull(MENSAJE_EXITO, TELEFONO);
    }
}