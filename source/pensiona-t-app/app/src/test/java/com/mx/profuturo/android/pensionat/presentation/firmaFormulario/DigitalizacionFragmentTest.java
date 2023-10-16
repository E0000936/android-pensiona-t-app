package com.mx.profuturo.android.pensionat.presentation.firmaFormulario;

import com.mx.profuturo.android.pensionat.domain.model.Captura;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;
import com.mx.profuturo.android.pensionat.presentation.digitalizacion.DigitalizacionFragment;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class DigitalizacionFragmentTest {
    private static final String RUTA = "path/imgengine/";
    private static final String NOMBRE = "INE_ANVERSO.jpeg";
    private static final String MENSAJE = "Los objectos deben ser iguales";
    private static final String MENSAJE_EXITO = "Validacion exitosa";

    @Test
    public void validarObtenerNumCaptura() {
        Digitalizacion digitalizacion = new Digitalizacion();
        Captura captura = new Captura();
        captura.setNumero(1);
        captura.setEstatus(1);
        Captura captura2 = new Captura();
        captura2.setNumero(2);
        captura2.setEstatus(0);
        RealmList<Captura> capturas = new RealmList<>();
        capturas.add(captura);
        capturas.add(captura2);
        digitalizacion.setCapturasArchivo(capturas);
        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        int numCaptura = digitalizacionFragment.obtenerNumeroCaptura(digitalizacion);
        Assert.assertEquals(MENSAJE, 2, numCaptura);
    }

    @Test
    public void validarNoHabilitarBtnSiguiente() {
        List<Digitalizacion> digitalizacions = new ArrayList<>();
        Digitalizacion digitalizacion = new Digitalizacion();
        digitalizacion.setEsObligatorio(1);
        digitalizacion.setTomarCaptura(0);
        digitalizacions.add(digitalizacion);
        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        Assert.assertFalse(MENSAJE_EXITO, digitalizacionFragment.validarBotonSiguiente(digitalizacions));
    }

    @Test
    public void validarHabilitarBtnSiguiente() {
        List<Digitalizacion> digitalizacions = new ArrayList<>();
        Digitalizacion digitalizacion = new Digitalizacion();
        digitalizacion.setEsObligatorio(1);
        digitalizacion.setTomarCaptura(1);
        digitalizacions.add(digitalizacion);
        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        Assert.assertTrue(MENSAJE_EXITO, digitalizacionFragment.validarBotonSiguiente(digitalizacions));
    }

    @Test
    public void validarCreacionCaptura() {
        Captura capturaAnterior = new Captura();
        capturaAnterior.setIdTipoDoc(1);
        capturaAnterior.setIdParamEnvioProceso(525);
        capturaAnterior.setDescripcion(NOMBRE);
        Captura captura = new Captura();
        captura.setNumero(1);
        captura.setIdTipoDoc(1);
        captura.setIdParamEnvioProceso(525);
        captura.setDescripcion(NOMBRE);
        captura.setCargaExitosa(0);

        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        Captura captura1 = digitalizacionFragment.crearObjetoCaptura(1, 0, capturaAnterior, RUTA, NOMBRE);
        Assert.assertEquals(MENSAJE, captura1.getCargaExitosa(), captura.getCargaExitosa());
        Assert.assertEquals(MENSAJE, captura1.getNumero(), captura.getNumero());
    }

    @Test
    public void validarCreacionCapturaDatosPrevios() {
        Captura capturaAnterior = new Captura();
        capturaAnterior.setIdTipoDoc(1);
        capturaAnterior.setIdParamEnvioProceso(525);
        capturaAnterior.setDescripcion(NOMBRE);
        capturaAnterior.setEstatus(1);
        capturaAnterior.setNombre(NOMBRE);
        capturaAnterior.setRuta(RUTA);

        Captura captura = new Captura();
        captura.setNumero(1);
        captura.setIdTipoDoc(1);
        captura.setIdParamEnvioProceso(525);
        captura.setDescripcion(NOMBRE);
        captura.setCargaExitosa(0);
        captura.setRuta(RUTA);
        captura.setEstatus(1);
        captura.setNombre(NOMBRE);

        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        Captura captura1 = digitalizacionFragment.crearObjetoCaptura(2, 0, capturaAnterior, RUTA, NOMBRE);
        Assert.assertEquals(MENSAJE, captura1.getCargaExitosa(), captura.getCargaExitosa());
        Assert.assertEquals(MENSAJE, captura1.getNumero(), captura.getNumero());
        Assert.assertEquals(MENSAJE, captura1.getRuta(), captura.getRuta());
        Assert.assertEquals(MENSAJE, captura1.getEstatus(), captura.getEstatus());
        Assert.assertEquals(MENSAJE, captura1.getNombre(), captura.getNombre());
    }

    @Test
    public void validarListaDocumentos() {
        Digitalizacion digitalizacion = new Digitalizacion();
        RealmList<Captura> capturas = new RealmList<>();
        Captura captura = new Captura();
        capturas.add(captura);
        digitalizacion.setCapturasArchivo(capturas);
        DigitalizacionFragment digitalizacionFragment = new DigitalizacionFragment();
        Assert.assertEquals(MENSAJE, 1, digitalizacionFragment.listaCapturaDoc(1, digitalizacion, RUTA, NOMBRE).size());
    }
}
