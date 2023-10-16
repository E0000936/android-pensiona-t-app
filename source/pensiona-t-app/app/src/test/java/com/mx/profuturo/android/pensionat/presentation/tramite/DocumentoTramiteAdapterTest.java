package com.mx.profuturo.android.pensionat.presentation.tramite;

import com.mx.profuturo.android.pensionat.domain.model.Documento;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DocumentoTramiteAdapterTest {
    @Test
    public void testCount() {
        List<Documento> documentos = new ArrayList<>();
        Documento documento = new Documento();
        documentos.add(documento);
        DocumentoTramiteAdapter adapter = new DocumentoTramiteAdapter(documentos);
        Assert.assertEquals(1, adapter.getItemCount());
    }
}
