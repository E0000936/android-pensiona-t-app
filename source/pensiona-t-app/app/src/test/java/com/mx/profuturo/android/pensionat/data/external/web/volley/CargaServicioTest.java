package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CargaServicioTest {
    private static final int NUMERO = 2;
    private static final int TOTAL = 7;
    private static final String NOMBRE = "Archivo.pdf";
    private static final String DOCUMENTO = "JFSSJKFHFS";
    private static final String CHECKSUM = "AD54454A";
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;
    @InjectMocks
    CargarDocumentosServicio cargarDocumentosServicio;
    private Expediente expediente;
    private Captura captura;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        cargarDocumentosServicio = new CargarDocumentosServicio(context, iServicioRespuesta);

        //cargar Expediente de prueba
        expediente = new Expediente();
        expediente.setFolioMit("123");
        expediente.setIdPeticion(1);
        expediente.setIdTramite(12334);
        expediente.setIdParamEnvio(10);

        //cargar Captura prueba
        captura = new Captura();
        captura.setIdParamEnvioProceso(230);
        captura.setDescripcion("Documento representante legal");
        captura.setNumero(2);
        captura.setIdTipoDoc(123);
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoDocumento = new JSONObject();
        objetoDocumento.put("folio", expediente.getFolioMit());
        objetoDocumento.put("id_peticion", expediente.getIdPeticion());
        objetoDocumento.put("id_tramite", expediente.getIdTramite());
        objetoDocumento.put("id_subtramite", expediente.getIdSubtramite());
        objetoDocumento.put("id_documento", captura.getIdParamEnvioProceso());
        objetoDocumento.put("desc_documento", captura.getDescripcion());
        objetoDocumento.put("documento", DOCUMENTO);
        objetoDocumento.put("nombre", NOMBRE);
        objetoDocumento.put("consecutivo", captura.getNumero());
        objetoDocumento.put("numero", NUMERO);
        objetoDocumento.put("total", TOTAL);
        objetoDocumento.put("id_tipo_doc", captura.getIdTipoDoc());
        objetoDocumento.put("id_param_envio", expediente.getIdParamEnvio());
        objetoDocumento.put("checksum", CHECKSUM);


        JSONObject parametros = cargarDocumentosServicio.obtenerParametros(expediente, captura, NOMBRE, DOCUMENTO, CHECKSUM, NUMERO, TOTAL);
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoDocumento.toString());
    }
}
