package com.mx.profuturo.android.pensionat.presentation.busqueda;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.android.volley.VolleyError;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.BusquedaClienteServicio;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Creado por Jose Antonio Acevedo Trejo en 23/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class BusquedaPresenterTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";
    @Mock
    JSONObject jsonObjectMock;
    @Mock
    JSONObject objetoInterno;
    @Mock
    JSONObject objetoRespuesta;
    private Context contexto;
    private IServicioRespuesta iServicioRespuesta;
    private BusquedaClienteServicio busquedaClienteServicio;
    private String valorBusqueda = "POLIZA";
    @Mock
    private IBusquedaVista iBusquedaVista;
    private BusquedaPresenter busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);

    @Before
    public void inicializar() throws JSONException {
        MockitoAnnotations.initMocks(this);
        contexto = mock(Context.class);
        busquedaClienteServicio = mock(BusquedaClienteServicio.class);
        iServicioRespuesta = mock(IServicioRespuesta.class);
        //servicio de Busqueda usuario
        objetoRespuesta = new JSONObject();
        objetoRespuesta.put("poliza", valorBusqueda);
        objetoInterno = new JSONObject();
        jsonObjectMock = new JSONObject();
        jsonObjectMock.put("rqt", objetoInterno);
    }

    @Test
    public void busquedaPresenterConstructor() {
        BusquedaPresenter busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);
        Assert.assertNotNull(busquedaPresenter);
    }

    @Test
    public void validarNSS() {
        busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);
        String nss = "00001210096";
        String nssNovalido = "000ah";
        busquedaPresenter.validarNSS(nss);
        busquedaPresenter.validarNSS(nssNovalido);
        Assert.assertNotNull(nss);
        Assert.assertNotNull(nssNovalido);
        Assert.assertEquals(MENSAJE, "00001210096", nss);
    }

    @Test
    public void validarCURP() {
        busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);
        String curp = "NIVG610730HDFTLR09";
        String curpNOvalido = "6118677862";
        busquedaPresenter.validarCURP(curp);
        busquedaPresenter.validarCURP(curpNOvalido);
        Assert.assertNotNull(curp);
        Assert.assertNotNull(curpNOvalido);
        Assert.assertEquals(MENSAJE, "NIVG610730HDFTLR09", curp);
    }

    @Test
    public void validarPoliza() {
        String poliza = "20190011864";
        String polizaNo = "ajs";
        busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);
        busquedaPresenter.validarPoliza(poliza);
        busquedaPresenter.validarPoliza(polizaNo);
        Assert.assertNotNull(poliza);
        Assert.assertNotNull(polizaNo);
        Assert.assertEquals(MENSAJE, "20190011864", poliza);

    }

    @Test
    public void validarFolio() {
        String folio = "31902003162";
        String folioNO = "ajsa";
        busquedaPresenter = new BusquedaPresenter(contexto, iBusquedaVista);
        busquedaPresenter.validarFolio(folio);
        busquedaPresenter.validarFolio(folioNO);
        Assert.assertNotNull(folio);
        Assert.assertNotNull(folioNO);
        Assert.assertEquals(MENSAJE, "31902003162", folio);
    }

    @Test
    public void obtenerRespuestaError() {
        String valorBusqueda = "POLIZA";
        busquedaClienteServicio.ejecutar(valorBusqueda, Constantes.TipoBusqueda.POLIZA);
        busquedaClienteServicio.onErrorResponse(new VolleyError());
        verify(iServicioRespuesta, times(0)).obtenerRespuestaError(Constantes.TipoServicio.BUSQUEDA,
                contexto.getString(R.string.error_conexion_titulo),
                contexto.getString(R.string.error_conexion));
    }

    @Test
    public void obtenerRespuestaExitosa() {
        busquedaClienteServicio.ejecutar(valorBusqueda, Constantes.TipoBusqueda.POLIZA);
        busquedaClienteServicio.onResponse(objetoRespuesta);
        verify(iServicioRespuesta, times(0)).obtenerRespuestaExitosa(
                Constantes.TipoServicio.BUSQUEDA, objetoRespuesta);
    }
}