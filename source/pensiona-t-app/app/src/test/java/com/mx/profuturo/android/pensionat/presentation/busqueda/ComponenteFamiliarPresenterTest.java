package com.mx.profuturo.android.pensionat.presentation.busqueda;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.android.volley.VolleyError;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.DetalleClienteServicio;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;

import org.json.JSONObject;
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
public class ComponenteFamiliarPresenterTest {

    @Mock
    JSONObject objetoRespuesta;
    private IServicioRespuesta listener;
    private Context contexto;
    private DetalleClienteServicio detalleClienteServicio;
    private Integer idPoliza = 121272;
    private Integer idOferta = 1051808;
    private Integer idGrpPago = 613678;

    @Before
    public void inicializar() {
        MockitoAnnotations.initMocks(this);
        listener = mock(IServicioRespuesta.class);
        contexto = mock(Context.class);
        detalleClienteServicio = mock(DetalleClienteServicio.class);
    }

    @Test
    public void obtenerRespuestaError() {
        detalleClienteServicio.ejecutar(idPoliza, idOferta, idGrpPago);
        detalleClienteServicio.onErrorResponse(new VolleyError());
        verify(listener, times(0)).obtenerRespuestaError(Constantes.TipoServicio.DETALLE,
                contexto.getString(R.string.error_conexion_titulo),
                contexto.getString(R.string.error_conexion));

    }

    @Test
    public void obtenerRespuestaExitosa() {
        detalleClienteServicio.ejecutar(idPoliza, idOferta, idGrpPago);
        detalleClienteServicio.onResponse(objetoRespuesta);
        verify(listener, times(0)).obtenerRespuestaExitosa(
                Constantes.TipoServicio.DETALLE, objetoRespuesta);
    }
}