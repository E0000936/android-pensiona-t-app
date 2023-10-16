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

@RunWith(MockitoJUnitRunner.class)
public class LoginServicioTest {
    private static final String USUARIO = "SP005989";
    private static final String CONTRASENA = "Pre*r00P*";
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;
    // Esto inyectará la simulación de "IServicioRespuesta" en su instancia de "LoginServicio"
    @Spy
    @InjectMocks
    LoginServicio loginServicio;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        loginServicio = new LoginServicio(context, iServicioRespuesta);
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        JSONObject jsonObjectMock = new JSONObject();
        objetoInterno.put("aplicacion", "expedientePensiones");
        objetoInterno.put("contrasena", CONTRASENA);
        objetoInterno.put("usuario", USUARIO);
        jsonObjectMock.put("rqt", objetoInterno);
        JSONObject parametros = loginServicio.obtenerParametros(USUARIO, CONTRASENA);
        Assert.assertEquals(MENSAJE, parametros.toString(), jsonObjectMock.toString());
    }
}