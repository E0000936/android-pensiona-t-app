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
package com.mx.profuturo.android.pensionat.presentation.login;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.CatalogosServicio;
import com.mx.profuturo.android.pensionat.data.external.web.volley.LoginServicio;
import com.mx.profuturo.android.pensionat.data.external.web.volley.TramitesServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.Catalogo;
import com.mx.profuturo.android.pensionat.domain.model.Peticion;
import com.mx.profuturo.android.pensionat.presentation.IServicioRespuesta;
import com.mx.profuturo.android.pensionat.utils.Constantes;
import com.mx.profuturo.android.pensionat.utils.ManejoArchivosHelper;
import com.mx.profuturo.android.pensionat.utils.ValidadorFormulario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>LoginPresenter</h1>
 * Clase que implementa la lógica de inicio de sesión.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-01-22
 */
public class LoginPresenter implements IServicioRespuesta {

    private Context contexto;
    private ILoginVista iLoginVista;

    LoginPresenter(Context contexto, ILoginVista iLoginVista) {
        this.contexto = contexto;
        this.iLoginVista = iLoginVista;
    }

    void iniciarSesion(String usuario, String contrasena) {
        new LoginServicio(contexto, this).ejecutar(usuario, contrasena);
    }

    void validarCredenciales(String usuario, String contrasena) {
        if (usuario != null && contrasena != null) {
            boolean contrasenaValida = ValidadorFormulario.validarContrasena(contrasena);
            boolean usuarioValido = ValidadorFormulario.validarUsuario(usuario);
            if (contrasenaValida && usuarioValido) {
                iLoginVista.validacionExitosaCampos(usuario, contrasena);
            } else {
                if (!contrasenaValida) {
                    iLoginVista.mostrarErrorContrasena();
                }
                if (!usuarioValido) {
                    iLoginVista.mostrarErrorUsuario();
                }
            }
        }
    }

    void validarCamposVacios(String usuario, String contrasena) {

        iLoginVista.habilitarBotonIngresar(!usuario.trim().isEmpty() && !contrasena.trim().isEmpty());
    }

    @Override
    public void obtenerRespuestaError(Constantes.TipoServicio tipoServicio, String titulo, String mensaje) {
        iLoginVista.mostrarMensaje(titulo, mensaje);
    }

    @Override
    public void obtenerRespuestaExitosa(Constantes.TipoServicio tipoServicio, JSONObject jsonObjeto) {
        switch (tipoServicio) {
            case LOGIN:
                inicioSesionExitoso(jsonObjeto);
                break;
            case CATALOGO:
                almacenarCatalogos(jsonObjeto);
                break;
            case TRAMITES:
                almacenarTramites(jsonObjeto);
                break;
            default:
                break;
        }
    }

    private void inicioSesionExitoso(JSONObject jsonObjeto) {
        String numeroEmpleado = jsonObjeto.optString("numeroEmpleado");
        Sesion.getInstancia(contexto).setNumeroEmpleado(numeroEmpleado);
        obtenerTramites();
    }

    private void obtenerTramites() {
        new TramitesServicio(contexto, this).ejecutar();
    }

    private void almacenarCatalogos(JSONObject jsonObjeto) {
        try {
            JSONArray catalogosArray = jsonObjeto.optJSONArray("catalogos");
            List<Catalogo> catalogos = Arrays.asList(new Gson().fromJson(String.valueOf(catalogosArray), Catalogo[].class));
            if (catalogos.get(0).getElementos().get(0) != null) {
                DBHelperRealm.guadarCatalogos(catalogos);
                crearDirectorioArchivos();
            }
        } catch (Exception e) {
            Log.d("Realm", e.getMessage());
            iLoginVista.mostrarMensaje(contexto.getString(R.string.error_catalogos),
                    contexto.getString(R.string.error_guardar_catalogos));
        }
    }

    private void almacenarTramites(JSONObject jsonObjeto) {
        try {
            JSONArray tramitesArray = jsonObjeto.optJSONArray("tramites");
            JSONObject peticion = tramitesArray.getJSONObject(0);
            JSONArray peticionesArray = peticion.optJSONArray("peticion");
            List<Peticion> peticiones = Arrays.asList(new Gson().fromJson(String.valueOf(peticionesArray), Peticion[].class));
            DBHelperRealm.guardarPeticiones(peticiones);
            obtenerCatalogos();
        } catch (Exception e) {
            Log.d("Realm", e.getMessage());
            iLoginVista.mostrarMensaje(contexto.getString(R.string.error_catalogos),
                    contexto.getString(R.string.error_guardar_tramites));
        }
    }

    private void obtenerCatalogos() {
        if (DBHelperRealm.validaExistenCatalogos()) {
            crearDirectorioArchivos();
        } else {
            new CatalogosServicio(contexto, this).ejecutar();
        }
    }

    private void crearDirectorioArchivos() {
        String tituloErrorDir = contexto.getString(R.string.error);
        String mensajeErrorDir = contexto.getString(R.string.error_crear_directorio);
        try {
            String rutaDirectorio = ManejoArchivosHelper.crearDirectorio(
                    ManejoArchivosHelper.obtenerArchivoSDCardRuta(Constantes.Nombres.DIR_ARCHIVOS));
            if (rutaDirectorio.isEmpty()) {
                iLoginVista.mostrarMensaje(tituloErrorDir, mensajeErrorDir);
            } else {
                Sesion.getInstancia(contexto).setPathPdf(rutaDirectorio);
                iLoginVista.iniciarSesionExitoso();
            }
        } catch (Exception e) {
            iLoginVista.mostrarMensaje(tituloErrorDir, mensajeErrorDir);
        }
    }
}
