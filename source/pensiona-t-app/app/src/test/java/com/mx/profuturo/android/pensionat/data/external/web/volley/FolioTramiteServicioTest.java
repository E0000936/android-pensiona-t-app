package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FolioTramiteServicioTest {
    private static final String NUM_EMPLEADO = "005459";
    private static final int ID_ENVIO = 123;
    private static final int ID_TRAMITE = 3746;
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;
    @InjectMocks
    FolioTramiteServicio folioTramiteServicio;
    private Expediente expediente;
    private Solicitante solicitante;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        folioTramiteServicio = new FolioTramiteServicio(context, iServicioRespuesta);

        //cargar Expediente de prueba
        expediente = new Expediente();
        expediente.setIdBeneficiarioOferta("123");
        expediente.setIdBeneficiarioPoliza(1);
        expediente.setIdTipoRegimen(12334);

        //cargar Captura prueba
        solicitante = new Solicitante();
        solicitante.setIdGrpPago(123);
        solicitante.setIdPoliza(3847829);
        solicitante.setIdOferta(434);
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoInterno = new JSONObject();
        objetoInterno.put("id_beneficario_oferta", expediente.getIdBeneficiarioOferta());
        objetoInterno.put("id_beneficiario_pol", expediente.getIdBeneficiarioPoliza());
        objetoInterno.put("id_grupo_pago_pol", solicitante.getIdGrpPago());
        objetoInterno.put("id_poliza", solicitante.getIdPoliza());
        objetoInterno.put("id_oferta", solicitante.getIdOferta());
        objetoInterno.put("id_param_envio", ID_ENVIO);
        objetoInterno.put("id_tipo_regimen", expediente.getIdTipoRegimen());
        objetoInterno.put("id_tramite_sucursal", ID_TRAMITE);
        objetoInterno.put("id_usuario", NUM_EMPLEADO);
        objetoInterno.put("nota", "");
        JSONObject parametros = folioTramiteServicio.obtenerParametros(expediente, solicitante, NUM_EMPLEADO, ID_ENVIO, ID_TRAMITE);
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoInterno.toString());
    }
}
