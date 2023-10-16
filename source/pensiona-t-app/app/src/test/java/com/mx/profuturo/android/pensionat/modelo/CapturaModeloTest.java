package com.mx.profuturo.android.pensionat.modelo;

import com.mx.profuturo.android.pensionat.domain.model.Captura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CapturaModeloTest {
    private static final Integer NUMERO = 0;
    private static final String NOMBRE = "IDENTIFICACION OFICIAL REVERSO";
    private static final String DESCRIPCION = "IDENTIFICACION OFICIAL REVERSO";
    private static final String RUTA = "BBVA BANCOMER";
    private static final Integer ESTATUS = 0;
    private static final Integer ID_PARAM_ENVIO_PROCESO = 155900;
    private static final Integer CARGA_EXITOSA = 0;
    private static final String MENSAJE = "Este valor debe ser igual al valor declarado en la constante";
    private Captura captura;

    @Before
    public void setUp() {
        captura = new Captura();
        captura.setNumero(NUMERO);
        captura.setNombre(NOMBRE);
        captura.setDescripcion(DESCRIPCION);
        captura.setRuta(RUTA);
        captura.setEstatus(ESTATUS);
        captura.setIdParamEnvioProceso(ID_PARAM_ENVIO_PROCESO);
        captura.setIdTipoDoc(ID_PARAM_ENVIO_PROCESO);
        captura.setCargaExitosa(CARGA_EXITOSA);
    }

    @Test
    public void testCapturaDetalles() {
        Assert.assertEquals(MENSAJE, NUMERO, captura.getNumero());
        Assert.assertEquals(MENSAJE, NOMBRE, captura.getNombre());
        Assert.assertEquals(MENSAJE, DESCRIPCION, captura.getDescripcion());
        Assert.assertEquals(MENSAJE, RUTA, captura.getRuta());
        Assert.assertEquals(MENSAJE, ESTATUS, captura.getEstatus());
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO_PROCESO, captura.getIdParamEnvioProceso());
        Assert.assertEquals(MENSAJE, ID_PARAM_ENVIO_PROCESO, captura.getIdTipoDoc());
        Assert.assertEquals(MENSAJE, CARGA_EXITOSA, captura.getCargaExitosa());
    }
}
