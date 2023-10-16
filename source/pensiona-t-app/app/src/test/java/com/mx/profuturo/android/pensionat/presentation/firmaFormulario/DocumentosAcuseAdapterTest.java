package com.mx.profuturo.android.pensionat.presentation.firmaFormulario;

import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class DocumentosAcuseAdapterTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCount() {
        List<Digitalizacion> documentos;
        documentos = new ArrayList<>();
        Digitalizacion digitalizacion = new Digitalizacion();
        documentos.add(digitalizacion);
        DocumentosAcuseAdapter adapter = new DocumentosAcuseAdapter(documentos);
        Assert.assertEquals(MENSAJE, 1, adapter.getItemCount());
    }
}
