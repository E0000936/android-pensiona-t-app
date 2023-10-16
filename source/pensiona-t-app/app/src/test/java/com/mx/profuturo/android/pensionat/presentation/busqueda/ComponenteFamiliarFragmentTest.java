package com.mx.profuturo.android.pensionat.presentation.busqueda;

import android.content.Context;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;
import com.mx.profuturo.android.pensionat.domain.model.Componente;
import com.mx.profuturo.android.pensionat.domain.model.Expediente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class ComponenteFamiliarFragmentTest {
    private static final String MENSAJE = "Los objectos deben ser iguales";

    @Test
    public void validarObtenerCategoriasVacia() {
        List<Poliza> polizas = new ArrayList<>();
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        Assert.assertEquals(MENSAJE, componenteFamiliarFragment.obtenerListFormatoPolizas(polizas), new ArrayList<>());
    }

    @Test
    public void validarObtenerFormatoPolizas() {
        Categoria categoria = new Categoria(1, 0, "Póliza 20190011245", "");
        List<Poliza> polizas = new ArrayList<>();
        Poliza poliza = new Poliza();
        poliza.setIdPoliza(1);
        Grupo grupo = new Grupo();
        grupo.setNumRenta("20190011245");
        RealmList<Grupo> grupos = new RealmList<>();
        grupos.add(grupo);
        poliza.setGrupos(grupos);
        polizas.add(poliza);
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        ArrayList<Categoria> categorias1 = componenteFamiliarFragment.obtenerListFormatoPolizas(polizas);
        Assert.assertEquals(MENSAJE, categorias1.get(0).getDescripcion(), categoria.getDescripcion());
    }

    @Test
    public void validarObtenerCategorias() {
        List<Poliza> polizas = new ArrayList<>();
        Poliza poliza = new Poliza();
        poliza.setIdPoliza(1);
        Grupo grupo = new Grupo();
        grupo.setNumRenta("39848");
        RealmList<Grupo> grupos = new RealmList<>();
        grupos.add(grupo);
        poliza.setGrupos(grupos);

        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria();
        categoria.setId(poliza.getIdPoliza());
        categoria.setDescripcion("Póliza".concat(grupo.getNumRenta()));
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        Assert.assertEquals(MENSAJE, componenteFamiliarFragment.obtenerListFormatoPolizas(polizas), categorias);
    }

    @Test
    public void validarLeyendaUnaPoliza() {
        Context context = Mockito.mock(Context.class);
        Mockito.when(context.getString(R.string.componente_familiar_resultado, "1")).thenReturn("1 resultado");
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        componenteFamiliarFragment.contexto = context;
        Assert.assertNotNull(componenteFamiliarFragment.obtenerLeyendaNumPolizas(1));
    }

    @Test
    public void validarLeyendaPolizas() {
        Context context = Mockito.mock(Context.class);
        Mockito.when(context.getString(R.string.componente_familiar_resultados, "4")).thenReturn("4 resultados");
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        componenteFamiliarFragment.contexto = context;
        Assert.assertNotNull(componenteFamiliarFragment.obtenerLeyendaNumPolizas(4));
    }

    @Test
    public void validarCrearObjExpediente() {
        Expediente expediente = new Expediente();
        expediente.setSexo("M");
        expediente.setIdSexo(1);
        expediente.setRegimen("ISSSTE");
        expediente.setIdTipoRegimen(3);
        expediente.setNombreTitular("JAVIER");
        expediente.setApMaternoTitular("SOLIS");
        expediente.setApPaternoTitular("FLORES");
        expediente.setPoliza("2144343");
        expediente.setNss("82318369");
        expediente.setGrupoPago(5);
        expediente.setIdBeneficiarioOferta("2");
        expediente.setIdBeneficiarioPoliza(3);
        expediente.setParentescoSolicitante("HIJO(A)");

        Grupo grupo = new Grupo();
        grupo.setSexoPoliza("M");
        grupo.setIdSexoPol(1);
        grupo.setDescRegimenOferta("ISSSTE");
        grupo.setIdTipoRegimenOferta(3);
        grupo.setNombrePoliza("JAVIER");
        grupo.setApMaternoPoliza("SOLIS");
        grupo.setApPaternoPoliza("FLORES");
        grupo.setNumRenta("2144343");
        grupo.setNssOferta("82318369");
        grupo.setGrupoPago(5);
        Componente componente = new Componente();
        componente.setIdBeneficiarioOferta("2");
        componente.setIdBeneficiarioPoliza(3);
        componente.setParentesco("HIJO(A)");
        componente.setSexo("F");
        componente.setIdSexo(2);
        Poliza poliza = new Poliza();
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        Expediente expediente1 = componenteFamiliarFragment.crearObjetoExpediente(grupo, componente, poliza);
        Assert.assertEquals(MENSAJE, expediente1.getParentescoSolicitante(), expediente.getParentescoSolicitante());
    }

    @Test
    public void validarCrearObjExpediente2() {
        Expediente expediente = new Expediente();
        expediente.setSexo("M");
        expediente.setIdSexo(1);
        expediente.setRegimen("IMSS");
        expediente.setIdTipoRegimen(6);
        expediente.setNombreTitular("JAVIER");
        expediente.setApMaternoTitular("SOLIS");
        expediente.setApPaternoTitular("FLORES");
        expediente.setPoliza("2144343");
        expediente.setNss("82318369");
        expediente.setGrupoPago(5);
        expediente.setIdBeneficiarioOferta("2");
        expediente.setIdBeneficiarioPoliza(3);
        expediente.setParentescoSolicitante("HIJO(A)");

        Grupo grupo = new Grupo();
        grupo.setSexoOferta("M");
        grupo.setIdSexoOferta(1);
        grupo.setNombrePoliza("JAVIER");
        grupo.setApMaternoPoliza("SOLIS");
        grupo.setApPaternoPoliza("FLORES");
        grupo.setNumRenta("2144343");
        grupo.setNssPoliza("2131369");
        grupo.setGrupoPago(5);
        Componente componente = new Componente();
        componente.setIdBeneficiarioOferta("2");
        componente.setIdBeneficiarioPoliza(3);
        componente.setParentesco("HIJO(A)");
        componente.setSexo("F");
        componente.setIdSexo(2);
        Poliza poliza = new Poliza();
        poliza.setIdRegimen(6);
        poliza.setTipoRegimen("IMSS");
        ComponenteFamiliarFragment componenteFamiliarFragment = new ComponenteFamiliarFragment();
        Expediente expediente1 = componenteFamiliarFragment.crearObjetoExpediente(grupo, componente, poliza);
        Assert.assertEquals(MENSAJE, expediente1.getParentescoSolicitante(), expediente.getParentescoSolicitante());
    }
}
