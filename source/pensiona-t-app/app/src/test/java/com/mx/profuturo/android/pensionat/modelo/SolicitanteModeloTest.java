package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Banco;
import com.mx.profuturo.android.pensionat.domain.model.Domicilio;
import com.mx.profuturo.android.pensionat.domain.model.Referencia;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.domain.model.Telefono;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

public class SolicitanteModeloTest {
    private static final Integer ID = 0;
    private static final String CURP = "AAIS900612MMNLSN09";
    private static final String FOLIO_OFERTA = "31801001585";
    private static final String RFC = "AAIS900612";
    private static final String NOMBRE = "MIGUEL";
    private static final String APPATERNO = "GOMEZ";
    private static final String APMATERNO = "TORRES";
    private static final String OCUPACION = "PENSIONADO";
    private static final String FECHA_NACIMIENTO = "10/01/1988";
    private static final String E_FIRMA = "FKDHFSDJK";
    private static final String CORREO = "prueba@correo.com";
    private static final String NACIONALIDAD = "MEXICANA";
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Solicitante solicitante;
    private Domicilio domicilio;
    private Banco banco;

    @Before
    public void setUp() {
        solicitante = new Solicitante();
        domicilio = new Domicilio();
        banco = new Banco();

        solicitante.setCurp(CURP);
        solicitante.setIdOferta(ID);
        solicitante.setIdPoliza(ID);
        solicitante.setIdGrpPago(ID);
        solicitante.setFolioOferta(FOLIO_OFERTA);
        solicitante.setRfc(RFC);
        solicitante.setApPaterno(APPATERNO);
        solicitante.setApMaterno(APMATERNO);
        solicitante.setNombre(NOMBRE);
        solicitante.setOcupacion(OCUPACION);
        solicitante.setDomicilio(domicilio);
        solicitante.setTelefonos(new RealmList<>());
        solicitante.setReferencias(new RealmList<>());
        solicitante.setBanco(banco);
        solicitante.setFechaNacimiento(FECHA_NACIMIENTO);
        solicitante.seteFirma(E_FIRMA);
        solicitante.setCorreo(CORREO);
        solicitante.setIdTitularCobro(ID);
        solicitante.setIdOcupacion(ID);
        solicitante.setIdNacionalidad(ID);
        solicitante.setNacionalidad(NACIONALIDAD);
    }

    @Test
    public void testBancoDetalles() {
        Assert.assertEquals(MENSAJE, CURP, solicitante.getCurp());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdOferta());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdPoliza());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdGrpPago());
        Assert.assertEquals(MENSAJE, FOLIO_OFERTA, solicitante.getFolioOferta());
        Assert.assertEquals(MENSAJE, RFC, solicitante.getRfc());
        Assert.assertEquals(MENSAJE, APPATERNO, solicitante.getApPaterno());
        Assert.assertEquals(MENSAJE, APMATERNO, solicitante.getApMaterno());
        Assert.assertEquals(MENSAJE, NOMBRE, solicitante.getNombre());
        Assert.assertEquals(MENSAJE, OCUPACION, solicitante.getOcupacion());
        Assert.assertEquals(MENSAJE, domicilio, solicitante.getDomicilio());
        Assert.assertEquals(MENSAJE, new RealmList<Telefono>(), solicitante.getTelefonos());
        Assert.assertEquals(MENSAJE, new RealmList<Referencia>(), solicitante.getReferencias());
        Assert.assertEquals(MENSAJE, banco, solicitante.getBanco());
        Assert.assertEquals(MENSAJE, FECHA_NACIMIENTO, solicitante.getFechaNacimiento());
        Assert.assertEquals(MENSAJE, E_FIRMA, solicitante.geteFirma());
        Assert.assertEquals(MENSAJE, CORREO, solicitante.getCorreo());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdTitularCobro());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdOcupacion());
        Assert.assertEquals(MENSAJE, ID, solicitante.getIdNacionalidad());
        Assert.assertEquals(MENSAJE, NACIONALIDAD, solicitante.getNacionalidad());
    }
}
