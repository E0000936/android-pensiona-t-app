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
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Creado por Jose Antonio Acevedo Trejo en 22/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CorreoServicioTest {
    private static final String FOLIO = "20190001931";
    private static final String CORREO = "jacevedt@everis.com";
    private static final String NOMBRE = "Antonio";
    private static final String ARCHIVO = "archivoProfuturo";
    private static final int INTENTOS = 10;
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @InjectMocks
    CorreoServicio correoServicio;
    @Mock
    private Context contexto;
    @Mock
    private IServicioRespuesta iServicioRespuesta;

    @Before
    public void inicializar() {
        MockitoAnnotations.initMocks(this);
        correoServicio = new CorreoServicio(contexto, iServicioRespuesta, "acusePDF");
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        JSONObject jsonObjectMock = new JSONObject();
        JSONObject objetoEncabezado = new JSONObject();
        JSONObject objetoAdjunto = new JSONObject();
        objetoEncabezado.put("asunto", "Confirmacion de tramite");
        objetoEncabezado.put("remitente", "tu-tramite-pensiones@profuturo.com.mx");
        objetoEncabezado.put("destinatario", CORREO);

        objetoAdjunto.put("nombre", NOMBRE);
        objetoAdjunto.put("contenido", ARCHIVO);

        objetoInterno.put("encabezado", objetoEncabezado);
        objetoInterno.put("adjunto", objetoAdjunto);
        objetoInterno.put("intentos", INTENTOS);
        objetoInterno.put("folio", FOLIO);
        jsonObjectMock.put("rqt", objetoInterno);
        JSONObject parametros = correoServicio.obtenerParametros(CORREO, NOMBRE, ARCHIVO, INTENTOS, FOLIO);
        Assert.assertEquals(MENSAJE, parametros.toString(), jsonObjectMock.toString());
    }

    @Test
    public void testValidarRespuestaFormateada() throws JSONException {
        JSONObject corrreoJSON = new JSONObject();
        corrreoJSON.put("nombreDoc", "acusePDF");
        Assert.assertEquals(MENSAJE, corrreoJSON.toString(), correoServicio.obtenerRespuestaFormato().toString());
    }
}