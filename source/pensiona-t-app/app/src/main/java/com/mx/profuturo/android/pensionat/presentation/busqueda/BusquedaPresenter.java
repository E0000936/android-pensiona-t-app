/*
 * Copyright (C) Profuturo All rights reserved. Todos los derechos reservados 2019.
 * mailto:
 *
 * Tramites digitales solo se puede distribuir con la autorización de Profuturo
 * License as published by Profuturo. Licencia publicada por Profuturo
 * version 1 .
 *
 * Tramites digitales is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package com.mx.profuturo.android.pensionat.presentation.busqueda;

import android.content.Context;

import com.google.gson.Gson;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.BusquedaClienteServicio;
import com.mx.profuturo.android.pensionat.domain.model.Componente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;
import com.mx.profuturo.android.pensionat.domain.model.Poliza;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.TraceLog;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.ValidadorFormulario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;

/**
 * <h1>BusquedaPresenter</h1>
 * Clase  que implementa la lógica de búsqueda solicitante.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class BusquedaPresenter implements IServicioRespuesta {

    private Context contexto;
    private IBusquedaVista iBusquedaVista;

    BusquedaPresenter(Context context, IBusquedaVista iBusquedaVista) {
        this.contexto = context;
        this.iBusquedaVista = iBusquedaVista;
    }

    void validarCampoBusqueda(int posicion, String valor) {
        limpiarDatosAplicacion();
        validarBusqueda(posicion, valor);
    }

    void limpiarDatosAplicacion() {
        Utilidades.limpiarDatosTramite(contexto);
    }

    private void validarBusqueda(int posicion, String valor) {
        switch (posicion) {
            case 0:
                break;
            case 1:
                validarNSS(String.valueOf(valor));
                break;
            case 2:
                validarPoliza(String.valueOf(valor));
                break;
            case 3:
                validarFolio(String.valueOf(valor));
                break;
            case 4:
                validarCURP(String.valueOf(valor));
                break;
            default:
                break;
        }
    }

    void buscarUsuario(String valorBusqueda, Constantes.TipoBusqueda tipoBusqueda) {
        new BusquedaClienteServicio(contexto, this).ejecutar(valorBusqueda, tipoBusqueda);
    }

    void validarNSS(String nss) {
        boolean validacion = ValidadorFormulario.validarNSS(nss);
        if (validacion) {
            iBusquedaVista.validacionExitosaCampo(nss);
        } else {
            iBusquedaVista.mostrarErrorValidacion();
        }
    }

    void validarCURP(String curp) {
        boolean validacion = ValidadorFormulario.validarCurp(curp);
        if (validacion) {
            /** Se agrega customKey para Crashalytics CURP **/
            TraceLog.getInstance().setCurpProcess(curp);
            /** == **/

            iBusquedaVista.validacionExitosaCampo(curp);
        } else {
            iBusquedaVista.mostrarErrorValidacion();
        }
    }

    void validarPoliza(String poliza) {
        boolean validacion = ValidadorFormulario.validarPoliza(poliza);
        if (validacion) {
            iBusquedaVista.validacionExitosaCampo(poliza);
        } else {
            iBusquedaVista.mostrarErrorValidacion();
        }
    }

    void validarFolio(String folio) {
        boolean validacion = ValidadorFormulario.validarFolio(folio);
        if (validacion) {
            iBusquedaVista.validacionExitosaCampo(folio);
        } else {
            iBusquedaVista.mostrarErrorValidacion();
        }
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iBusquedaVista.iniciarBusquedaError(titulo, mensaje);
    }

    /**
     * Se evalúan los datos de respuesta en caso de no tener beneficiarios
     * entra en caso cerrado
     */
    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        try {
            JSONArray polizasArray = jsonObjeto.optJSONArray("poliza");
            List<Poliza> polizas = Arrays.asList(new Gson().fromJson(String.valueOf(polizasArray), Poliza[].class));
            boolean esCasoCerrado = false;
            if (polizas.size() > 0) {
                RealmList<Grupo> comparacion = polizas.get(0).getGrupos();
                RealmList<Componente> beneficiarioDatos = comparacion.get(0).getBeneficiarios();
                Integer compararPoliza = beneficiarioDatos.get(0).getIdBeneficiarioPoliza();
                esCasoCerrado = compararPoliza == null || compararPoliza.toString().isEmpty();
            }
            iBusquedaVista.iniciarBusquedaExitoso(polizas, esCasoCerrado);
        } catch (Exception e) {
            iBusquedaVista.iniciarBusquedaError(contexto.getString(R.string.error_conexion_titulo),
                    contexto.getString(R.string.error_conexion));
        }
    }
}
