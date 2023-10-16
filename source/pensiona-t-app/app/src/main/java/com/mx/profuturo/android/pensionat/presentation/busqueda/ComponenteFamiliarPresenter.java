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
import com.mx.profuturo.android.pensionat.data.external.web.volley.DetalleClienteServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.Solicitante;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;

import org.json.JSONObject;

/**
 * <h1>ComponenteFamiliarPresenter</h1>
 * Clase que lleva la lógica para la selección
 * del componente familiar o grupo de pago.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-4
 */
public class ComponenteFamiliarPresenter implements IServicioRespuesta {

    private Context context;
    private IComponenteFamiliarVista iComponenteVista;

    ComponenteFamiliarPresenter(Context context, IComponenteFamiliarVista iComponenteVista) {
        this.context = context;
        this.iComponenteVista = iComponenteVista;
    }

    void obtenerDetalleSolicitante(Integer idPoliza,
                                   Integer idOferta,
                                   Integer idGrpPago) {
        new DetalleClienteServicio(context, this).ejecutar(idPoliza, idOferta, idGrpPago);
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iComponenteVista.consultarDetalleSolicitanteError(titulo, mensaje);
    }

    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        try {
            JSONObject objeto = jsonObjeto.optJSONObject("response").optJSONObject("solicitante");
            Solicitante solicitante = new Gson().fromJson(String.valueOf(objeto), Solicitante.class);
            DBHelperRealm.insertarActualizarSolicitante(solicitante);
            iComponenteVista.consultarDetalleSolicitanteExitoso(solicitante);
        } catch (Exception e) {
            iComponenteVista.consultarDetalleSolicitanteError(context.getString(R.string.error_conexion_titulo),
                    context.getString(R.string.error_conexion));

        }
    }
}
