package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Documento;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DocumentoModeloTest {
    private static final Integer ID_PARAM_ENVIO = 155969;
    private static final String AYUDA = "SE VALIDA NOMBRE, DATOS GENERALES Y VIGENCIA";
    private static final String DESCRIPCION = "IDENTIFICACION OFICIAL REVERSO";
    private static final Integer OBLIGATORIO = 1;
    private static final Integer NUM_DOCUMENTOS = 2;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";

    private Documento documento;

    @Before
    public void setUp() {
        documento = new Documento();
        documento.setIdParamEnvioProceso(ID_PARAM_ENVIO);
        documento.setIdTipoDoc(ID_PARAM_ENVIO);
        documento.setAyuda(AYUDA);
        documento.setDescripcion(DESCRIPCION);
        documento.setEsObligatorio(OBLIGATORIO);
        documento.setNumeroDocumentos(NUM_DOCUMENTOS);
    }

    @Test
    public void testCatalogoDetalles() {
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO, documento.getIdParamEnvioProceso());
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO, documento.getIdTipoDoc());
        Assert.assertEquals(MENSAJE, AYUDA, documento.getAyuda());
        Assert.assertEquals(MENSAJE, DESCRIPCION, documento.getDescripcion());
        Assert.assertEquals(MENSAJE, OBLIGATORIO, documento.getEsObligatorio());
        Assert.assertEquals(MENSAJE, NUM_DOCUMENTOS, documento.getNumeroDocumentos());
    }
}