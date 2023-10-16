package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DetalleServicioTest {
    private static final Integer ID_GRUPO = 124243;
    private static final Integer ID_OFERTA = 29943004;
    private static final Integer ID_POLIZA = 90011245;
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;
    @InjectMocks
    DetalleClienteServicio detalleClienteServicio;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        detalleClienteServicio = new DetalleClienteServicio(context, iServicioRespuesta);
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objeto = new JSONObject();
        objeto.put("consecutivo_beneficiario", 0);
        objeto.put("id_beneficiario_poliza", 0);
        objeto.put("id_grupo_pago", ID_GRUPO);
        objeto.put("id_oferta", ID_OFERTA);
        objeto.put("id_poliza", ID_POLIZA);
        detalleClienteServicio.setIdGrpPago(ID_GRUPO);
        detalleClienteServicio.setIdOferta(ID_OFERTA);
        detalleClienteServicio.setIdPoliza(ID_POLIZA);
        JSONObject parametros = detalleClienteServicio.obtenerParametros();
        Assert.assertEquals(MENSAJE, parametros.toString(), objeto.toString());
    }
}
