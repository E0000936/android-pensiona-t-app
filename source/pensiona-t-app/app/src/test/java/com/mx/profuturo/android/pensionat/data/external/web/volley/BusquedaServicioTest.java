package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class BusquedaServicioTest {
    private static final String POLIZA = "20190011245";
    private static final String NSS = "00005997461";
    private static final String CURP = "TESG750314HSPRNL06";
    private static final String FOLIO = "18029434";
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;

    @Mock
    Context context;

    @Spy
    @InjectMocks
    BusquedaClienteServicio busquedaClienteServicio;

    @Before
    public void initMocks() {
        //Esto inicializará los mock anotados
        MockitoAnnotations.initMocks(this);
        busquedaClienteServicio = new BusquedaClienteServicio(context, iServicioRespuesta);
    }

    @Test
    public void testValidarFormacionParametrosNSS() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("nss", NSS);
        busquedaClienteServicio.setTipoBusqueda(Constantes.TipoBusqueda.NSS);
        busquedaClienteServicio.setValorBusqueda(NSS);
        JSONObject parametros = busquedaClienteServicio.obtenerParametros();
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoInterno.toString());
    }

    @Test
    public void testValidarFormacionParametrosCurp() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("curp", CURP);
        busquedaClienteServicio.setTipoBusqueda(Constantes.TipoBusqueda.CURP);
        busquedaClienteServicio.setValorBusqueda(CURP);
        JSONObject parametros = busquedaClienteServicio.obtenerParametros();
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoInterno.toString());
    }

    @Test
    public void testValidarFormacionParametrosFolio() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("folioOferta", FOLIO);
        busquedaClienteServicio.setTipoBusqueda(Constantes.TipoBusqueda.FOLIO);
        busquedaClienteServicio.setValorBusqueda(FOLIO);
        JSONObject parametros = busquedaClienteServicio.obtenerParametros();
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoInterno.toString());
    }

    @Test
    public void testValidarFormacionParametrosPoliza() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("poliza", POLIZA);
        busquedaClienteServicio.setTipoBusqueda(Constantes.TipoBusqueda.POLIZA);
        busquedaClienteServicio.setValorBusqueda(POLIZA);
        JSONObject parametros = busquedaClienteServicio.obtenerParametros();
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoInterno.toString());
    }
}
