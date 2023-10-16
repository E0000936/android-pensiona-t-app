package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class RegistroAcuseTest {
    private static final String FIRMA = "firma";
    private static final String FECHA = "24/05/2019";
    private static final String NUM_EMPLEADO = "SR005459";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;
    @InjectMocks
    RegistroAcuseServicio registroAcuseServicio;
    private Telefono telefono;
    private Expediente expediente;
    private Solicitante solicitante;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        registroAcuseServicio = new RegistroAcuseServicio(context, iServicioRespuesta);

        //inicializar Telefono pruebas
        telefono = new Telefono();
        telefono.setNumero("2434242");
        telefono.setIdTipo(1);
        telefono.setTipo("CASA");

        //inicializar Expediente pruebas
        expediente = new Expediente();
        expediente.setFolioMit("0323918");
        expediente.setPoliza("20190011245");
        expediente.setNss("41018422703");
        expediente.setGrupoPago(1);
        expediente.setSubtramite("PENSIONADO");
        expediente.setParentescoSolicitante("HIJO");
        expediente.setExcepcion("NO CUENTA CON UNA MANO");
        expediente.setIdExcepcion(2);
        expediente.setObservaciones("observaciones");

        //inicializar Solicitante pruebas
        solicitante = new Solicitante();
        solicitante.setIdPoliza(123);
        solicitante.setNombre("JUAN CARLOS");
        solicitante.setApMaterno("JIMENEZ");
        solicitante.setApPaterno("JUAREZ");
        solicitante.setTelefonos(new RealmList<>());
    }

    @Test
    public void testValidarFormacionParametrosTelefono() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero", telefono.getNumero());
        jsonObject.put("id_tipo_telefono", telefono.getIdTipo());
        jsonObject.put("descripcion_tipo_telefono", telefono.getTipo());
        jsonObject.put("hora_de", null);
        jsonObject.put("hora_hasta", null);
        JSONObject parametros = Utilidades.obtenerObjetoTelefono(telefono);
        Assert.assertEquals(MENSAJE, parametros.toString(), jsonObject.toString());
    }

    @Test
    public void testValidarFormacionParametros() throws JSONException {
        JSONObject objetoSolicitante = new JSONObject();
        objetoSolicitante.put("folio", expediente.getFolioMit());
        objetoSolicitante.put("id_poliza", solicitante.getIdPoliza());
        objetoSolicitante.put("numero_renta_vitalicia", expediente.getPoliza());
        objetoSolicitante.put("nombre_titular", "");
        objetoSolicitante.put("apellido_paterno_titular", "");
        objetoSolicitante.put("apellido_materno_titular", "");
        objetoSolicitante.put("nss_titular", expediente.getNss());
        objetoSolicitante.put("id_grupo_pago", expediente.getGrupoPago());

        objetoSolicitante.put("tipo_tramite", expediente.getSubtramite());
        objetoSolicitante.put("desc_parentesco", expediente.getParentescoSolicitante());
        objetoSolicitante.put("nombre_solicitante", solicitante.getNombre());
        objetoSolicitante.put("apellido_paterno_solicitante", solicitante.getApPaterno());
        objetoSolicitante.put("apellido_materno_solicitante", solicitante.getApMaterno());
        objetoSolicitante.put("id_excepcion", expediente.getIdExcepcion());
        objetoSolicitante.put("descripcion_excepcion", expediente.getExcepcion());
        objetoSolicitante.put("observaciones", "observaciones NO CUENTA CON UNA MANO");

        JSONArray telefonos = new JSONArray();
        objetoSolicitante.put("Telefonos", telefonos);

        JSONArray digitalizaciones = new JSONArray();

        objetoSolicitante.put("Documentos", digitalizaciones);
        objetoSolicitante.put("firma", FIRMA);
        objetoSolicitante.put("fecha_formato", FECHA);
        objetoSolicitante.put("numero_empleado", NUM_EMPLEADO);

        List<Digitalizacion> digitalizacionList = new ArrayList<>();
        JSONObject parametros = registroAcuseServicio.obtenerParametros(solicitante,
                digitalizacionList,
                expediente,
                FIRMA,
                FECHA,
                NUM_EMPLEADO);
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoSolicitante.toString());
    }
}
