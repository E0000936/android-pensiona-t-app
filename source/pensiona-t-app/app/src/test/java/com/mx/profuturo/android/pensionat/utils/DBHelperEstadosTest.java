package com.mx.profuturo.android.pensionat.utils;

import android.content.Context;
import android.database.Cursor;

import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.CodigoPostal;
import com.mx.profuturo.android.pensionat.domain.model.baseDatosEstado.Colonia;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

public class DBHelperEstadosTest {
    private static final String ID_ENTIDAD_FEDERATIVA = "id_entidad_federativa";
    private static final String CLAVE_ALFANUMERICA_ENTIDAD = "clave_alfanumerica_entidad";
    private static final String ENTIDAD_FEDERATIVA = "entidad_federativa";
    private static final String ID_MUNICIPIO = "id_municipio";
    private static final String MUNICIPIO = "municipio";
    private static final String ID_COLONIA = "id_colonia";
    private static final String COLONIA = "colonia";
    private static final String ID_CIUDAD = "id_ciudad";
    private static final String CIUDAD = "ciudad";
    private static final String CODIGO_POSTAL = "codigo_postal";
    @Mock
    private Context context;

    @Test
    public void validarObtenerColonia() {
        Colonia colonia = new Colonia();
        colonia.setNombre("San Isidro");
        colonia.setIdColonia(1);
        Cursor c = Mockito.mock(Cursor.class);
        Mockito.when(c.getInt(c.getColumnIndex(ID_COLONIA))).thenReturn(1);
        Mockito.when(c.getString(c.getColumnIndex(COLONIA))).thenReturn("San Isidro");
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Assert.assertEquals(dbHelperEstados.obtenerColonia(c).getNombre(), colonia.getNombre());
        Assert.assertEquals(dbHelperEstados.obtenerColonia(c).getIdColonia(), colonia.getIdColonia());
    }

    @Test
    public void validarCodigoPostal() {
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Cursor c = Mockito.mock(Cursor.class);
        Mockito.when(c.moveToFirst()).thenReturn(true);
        CodigoPostal codigoPostalTest = new CodigoPostal();
        ArrayList<Colonia> listColonias = new ArrayList<>();
        listColonias.add(new Colonia());
        codigoPostalTest.setListaColonias(listColonias);
        CodigoPostal codigoPostal = dbHelperEstados.recorrerCursorMotor(c);
        Assert.assertEquals(codigoPostal.getCiudad(), codigoPostalTest.getCiudad());
    }

    @Test
    public void validarCodigoPostal2() {
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Cursor c = Mockito.mock(Cursor.class);
        Mockito.when(c.moveToFirst()).thenReturn(true);
        Mockito.when(c.getInt(c.getColumnIndex(CODIGO_POSTAL))).thenReturn(0);
        CodigoPostal codigoPostalTest = new CodigoPostal();
        ArrayList<Colonia> listColonias = new ArrayList<>();
        listColonias.add(new Colonia());
        codigoPostalTest.setListaColonias(listColonias);
        CodigoPostal codigoPostal = dbHelperEstados.recorrerCursorMotor(c);
        Assert.assertEquals(codigoPostal.getCiudad(), codigoPostalTest.getCiudad());
    }

    @Test
    public void validarCodigoPostalSinCursor() {
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Assert.assertNull(dbHelperEstados.recorrerCursorMotor(null));
    }

    @Test
    public void validarCodigoPostalNull() {
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Assert.assertNull(dbHelperEstados.recorrerCursorMotor(null));
    }

    @Test
    public void validarObtenerCP() {
        CodigoPostal codigoPostal = new CodigoPostal();
        codigoPostal.setCodigoPostal("54477");
        codigoPostal.setIdEntidadFederativa(12);
        codigoPostal.setEntidadFederativa("México");
        codigoPostal.setIdMunicipio(3);
        codigoPostal.setMunicipio("NR");
        codigoPostal.setClaveAlfanumericaEntidad("MEX");
        codigoPostal.setIdCiudad(141);
        codigoPostal.setCiudad("Ciudad");
        Cursor c = Mockito.mock(Cursor.class);
        Mockito.when(c.getInt(c.getColumnIndex(ID_ENTIDAD_FEDERATIVA))).thenReturn(12);
        Mockito.when(c.getString(c.getColumnIndex(ENTIDAD_FEDERATIVA))).thenReturn("México");
        Mockito.when(c.getInt(c.getColumnIndex(ID_MUNICIPIO))).thenReturn(3);
        Mockito.when(c.getString(c.getColumnIndex(MUNICIPIO))).thenReturn("NR");
        Mockito.when(c.getString(c.getColumnIndex(CLAVE_ALFANUMERICA_ENTIDAD))).thenReturn("MEX");
        Mockito.when(c.getInt(c.getColumnIndex(ID_CIUDAD))).thenReturn(141);
        Mockito.when(c.getString(c.getColumnIndex(CIUDAD))).thenReturn("Ciudad");

        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        CodigoPostal codigoPostal1 = dbHelperEstados.obtenerCP(c, 54477);
        Assert.assertEquals(codigoPostal1.getCodigoPostal(), codigoPostal.getCodigoPostal());
    }

    @Test
    public void validarObtenerDireccion() {
        DbHelperEstados dbHelperEstados = new DbHelperEstados(context);
        Assert.assertNull(dbHelperEstados.obtenerDireccion("54477"));
    }
}
