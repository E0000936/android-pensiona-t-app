package com.mx.profuturo.android.pensionat.data.external.web.volley;

import android.content.Context;

import com.mx.profuturo.android.pensionat.domain.model.Banco;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
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

public class RegistroTramiteTest {
    private static final String FIRMA = "firma";
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Mock
    IServicioRespuesta iServicioRespuesta;
    @Mock
    Context context;

    @InjectMocks
    RegistroTramiteServicio registroTramiteServicio;
    private Telefono telefono;
    private Expediente expediente;
    private Solicitante solicitante;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        registroTramiteServicio = new RegistroTramiteServicio(context, iServicioRespuesta);
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

        //inicializar Domicilio pruebas
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("CHABACANO");
        domicilio.setNumExt("38 FRACC");
        domicilio.setNumInt("");
        domicilio.setCp("01807");
        domicilio.setIdColonia(328);
        domicilio.setAsentamiento("RANCHO SAN FRANCISCO PUEBLO SAN BARTOLO AMEYALCO");
        domicilio.setIdMunicipio(10);
        domicilio.setMunicipio("CIUDAD DE MEXICO");
        domicilio.setEstado("DISTRITO FEDERAL");
        domicilio.setIdCiudad(1);
        domicilio.setIdEstado(9);
        domicilio.setCiudad("CIUDAD DE MEXICO");
        solicitante.setDomicilio(domicilio);

        //inicializar Banco pruebas
        Banco banco = new Banco();
        banco.setClabe("021180060501169127");
        banco.setDescripcion("HSBC");
        banco.setTipoCuenta("DEPOSITO EN CUENTA BANCARIA");
        solicitante.setBanco(banco);
        solicitante.setReferencias(new RealmList<>());
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
        objetoSolicitante.put("id_peticion", expediente.getIdPeticion());
        objetoSolicitante.put("id_tramite", expediente.getIdTramite());
        objetoSolicitante.put("descripcion_tramite", expediente.getTramite());
        objetoSolicitante.put("id_subtramite", expediente.getIdSubtramite());
        objetoSolicitante.put("descripcion_subtramite", expediente.getSubtramite());

        objetoSolicitante.put("nombre_solicitante", solicitante.getNombre());
        objetoSolicitante.put("apellido_paterno_solicitante", solicitante.getApPaterno());
        objetoSolicitante.put("apellido_materno_solicitante", solicitante.getApMaterno());
        objetoSolicitante.put("rfc_solicitante", solicitante.getRfc());
        objetoSolicitante.put("curp_solicitante", solicitante.getCurp());
        objetoSolicitante.put("efirma_solicitante", solicitante.geteFirma());
        objetoSolicitante.put("fecha_nacimiento_solicitante", solicitante.getFechaNacimiento());
        objetoSolicitante.put("nss_solicitante", expediente.getNss());
        objetoSolicitante.put("correo_electronico", solicitante.getCorreo());

        Domicilio domicilio = solicitante.getDomicilio();
        objetoSolicitante.put("id_pais", domicilio.getIdPais());
        objetoSolicitante.put("descripcion_pais", domicilio.getPais());
        objetoSolicitante.put("id_nacionalidad", solicitante.getIdNacionalidad());
        objetoSolicitante.put("nacionalidad", solicitante.getNacionalidad());
        objetoSolicitante.put("id_ocupacion", solicitante.getIdOcupacion());
        objetoSolicitante.put("descripcion_ocupacion", solicitante.getOcupacion());
        objetoSolicitante.put("id_titular_cobro", solicitante.getIdTitularCobro());
        objetoSolicitante.put("id_oferta", solicitante.getIdOferta());
        objetoSolicitante.put("id_poliza", solicitante.getIdPoliza());
        objetoSolicitante.put("id_grupo_pago", solicitante.getIdGrpPago());
        objetoSolicitante.put("folio_oferta", solicitante.getFolioOferta());
        objetoSolicitante.put("regimen", expediente.getRegimen());
        objetoSolicitante.put("id_excepcion", expediente.getIdExcepcion());
        objetoSolicitante.put("descripcion_excepcion", expediente.getExcepcion());
        objetoSolicitante.put("id_grupo_pago_pol", solicitante.getIdGrpPago());
        objetoSolicitante.put("id_beneficiario_pol", expediente.getIdBeneficiarioPoliza());

        objetoSolicitante.put("observaciones", "observaciones NO CUENTA CON UNA MANO");
        objetoSolicitante.put("id_sexo", expediente.getIdSexo());
        objetoSolicitante.put("desc_sexo", expediente.getSexo());
        objetoSolicitante.put("poliza", expediente.getPoliza());
        objetoSolicitante.put("id_param_envio", expediente.getIdParamEnvio());

        JSONArray domicilios = new JSONArray();
        JSONObject objetoDomicilio = new JSONObject();
        objetoDomicilio.put("calle", domicilio.getCalle());
        objetoDomicilio.put("numero_exterior", domicilio.getNumExt());
        objetoDomicilio.put("numero_interior", domicilio.getNumInt());
        objetoDomicilio.put("codigo_postal", domicilio.getCp());
        objetoDomicilio.put("id_colonia", domicilio.getIdColonia());
        objetoDomicilio.put("colonia", domicilio.getAsentamiento());
        objetoDomicilio.put("id_municipio", domicilio.getIdMunicipio());
        objetoDomicilio.put("alcaldia", domicilio.getMunicipio());
        objetoDomicilio.put("id_ciudad", domicilio.getIdCiudad());
        objetoDomicilio.put("ciudad", domicilio.getCiudad());
        objetoDomicilio.put("id_entidad", domicilio.getIdEstado());
        objetoDomicilio.put("entidad", domicilio.getEstado());
        domicilios.put(objetoDomicilio);
        objetoSolicitante.put("Domicilio", domicilios);

        JSONArray telefonos = new JSONArray();
        objetoSolicitante.put("Telefonos", telefonos);

        JSONArray referencias = new JSONArray();
        objetoSolicitante.put("Referencias", referencias);

        JSONArray bancos = new JSONArray();
        JSONObject objetoBancarios = new JSONObject();
        objetoBancarios.put("id_banco", solicitante.getBanco().getId());
        objetoBancarios.put("descripcion_banco", solicitante.getBanco().getDescripcion());
        objetoBancarios.put("tipo_cuenta", solicitante.getBanco().getTipoCuenta());
        objetoBancarios.put("clabe", solicitante.getBanco().getClabe());
        bancos.put(objetoBancarios);
        objetoSolicitante.put("DatosBancarios", bancos);

        JSONArray digitalizaciones = new JSONArray();
        objetoSolicitante.put("Documentos", digitalizaciones);
        objetoSolicitante.put("firma", FIRMA);

        List<Digitalizacion> digitalizacionList = new ArrayList<>();
        JSONObject parametros = registroTramiteServicio.obtenerParametros(solicitante,
                digitalizacionList,
                expediente,
                FIRMA);
        Assert.assertEquals(MENSAJE, parametros.toString(), objetoSolicitante.toString());
    }
}
