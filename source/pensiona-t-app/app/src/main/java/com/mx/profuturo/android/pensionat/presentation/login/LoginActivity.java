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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.external.web.volley.OauthServicio;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.domain.model.ErrorServicio;
import com.mx.profuturo.android.pensionat.presentation.busqueda.BusquedaActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.DialogoPresenter;
import com.mx.profuturo.android.pensionat.utils.RequestPermissionUtil;
import com.mx.profuturo.android.pensionat.utils.TraceLog;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.Progress;
import com.mx.profuturo.grupo.launchermau.data.local.ConfLauncher;
import com.mx.profuturo.grupo.launchermau.main.LauncherMAU;

/**
 * <h1>LoginActivity</h1>
 * Clase inicio de sesión.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-01-22
 */
public class LoginActivity extends AppCompatActivity implements ILoginVista, OauthServicio.IOauthServicio, RequestPermissionUtil.CheckPermissionsListener {
    private TextInputLayout layoutContrasena;
    private TextInputEditText edtContrasena;
    private TextInputLayout layoutUsuario;
    private TextInputEditText edtUsuario;
    private ImageView imgContrasenaError;
    private ImageView imgUsuarioError;
    private Button btnIngresar;
    private Context contexto;
    private DialogoPresenter dialogoPresenter;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        contexto = this;
        cargarVista();
        View viewPrincipal = findViewById(R.id.view_login);
        View viewImagen = findViewById(R.id.img_login_edificio);
        viewPrincipal.setOnClickListener((View v) -> esconderTeclado(viewPrincipal));
        viewImagen.setOnClickListener((View v) -> esconderTeclado(viewImagen));

        TraceLog.getInstance().setCurpProcess("HOLA");
        RequestPermissionUtil.checkPermissions(this, this);
        //Launcher MAU clean old session and restore uuid session
        LauncherMAU.clearProcess(this);
        ConfLauncher.cleanSharedPref(this);
        LauncherMAU.createUuidSession(this);
    }

    public void cargarVista() {
        setContentView(R.layout.actividad_login);
        layoutUsuario = findViewById(R.id.layout_login_usuario);
        layoutContrasena = findViewById(R.id.layout_login_contrasena);
        loginPresenter = new LoginPresenter(contexto, this);
        edtContrasena = findViewById(R.id.edt_login_contrasena);
        edtUsuario = findViewById(R.id.edt_login_usuario);
        btnIngresar = findViewById(R.id.btn_login_ingresar);
        dialogoPresenter = new DialogoPresenter();
        imgContrasenaError = findViewById(R.id.img_login_contrasena_error);
        imgUsuarioError = findViewById(R.id.img_login_usuario_error);
        edtContrasena.addTextChangedListener(new validarActivacionBtnIngresar());
        edtUsuario.addTextChangedListener(new validarActivacionBtnIngresar());
        btnIngresar.setOnClickListener((View view) -> validarCampos());
    }

    public void validarCampos() {
        if (edtUsuario.getText() != null && edtContrasena.getText() != null) {
            String usuario = edtUsuario.getText().toString().trim();
            String contrasena = edtContrasena.getText().toString().trim();

            if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                loginPresenter.validarCredenciales(usuario, contrasena);
            }
        }
    }

    private void limpiarVistaError() {
        layoutUsuario.setError(null);
        layoutContrasena.setError(null);
        imgUsuarioError.setVisibility(View.GONE);
        imgContrasenaError.setVisibility(View.GONE);
    }

    @Override
    public void habilitarBotonIngresar(boolean habilitar) {
        btnIngresar.setEnabled(habilitar);
        Drawable fondoBoton = habilitar ? getDrawable(R.drawable.fondo_boton_azul) : getDrawable(R.drawable.fondo_boton_gris);
        btnIngresar.setBackground(fondoBoton);
    }

    @Override
    public void iniciarSesionExitoso() {
        Progress.ocultar();
        DBHelperRealm.limpiar();
        Intent intent = new Intent(this, BusquedaActivity.class);
        startActivity(intent);
    }

    @Override
    public void mostrarMensaje(String titulo, String mensaje) {
        Progress.ocultar();
        if (mensaje != null) {
            dialogoPresenter.mensajeAlerta(contexto, titulo, mensaje);
        }
    }

    @Override
    public void mostrarErrorUsuario() {
        layoutUsuario.setError(getString(R.string.login_error_formato));
        imgUsuarioError.setVisibility(View.VISIBLE);
    }

    @Override
    public void mostrarErrorContrasena() {
        layoutContrasena.setError(getString(R.string.login_error_formato));
        imgContrasenaError.setVisibility(View.VISIBLE);
    }

    @Override
    public void validacionExitosaCampos(String usuario, String contrasena) {
        Progress.mostrar(contexto);
        new OauthServicio(contexto, this).ejecutar();
    }

    @Override
    public void authenticate(ErrorServicio error, boolean esExitoso) {
        if (esExitoso && edtUsuario.getText() != null && edtContrasena.getText() != null) {
            String usuario = edtUsuario.getText().toString();
            String contrasena = edtContrasena.getText().toString();
            loginPresenter.iniciarSesion(usuario, contrasena);
        } else {
            Progress.ocultar();
            mostrarMensaje(error.getTitulo(), error.getMensaje());
        }
    }

    public void esconderTeclado(View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*String tituloErrorPermisos = contexto.getString(R.string.error);
        String mensajeErrorPermisos = contexto.getString(R.string.login_error_permisos);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) { // falta la cláusula por defecto porque no se ocupa
            case 2:
            case RequestPermissionUtil.REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    validarCampos();
                } else {
                    mostrarMensaje(tituloErrorPermisos, mensajeErrorPermisos);
                }
            default:
        }*/
        String tituloErrorPermisos = contexto.getString(R.string.error);
        String mensajeErrorPermisos = contexto.getString(R.string.login_error_permisos);
        if (requestCode == RequestPermissionUtil.REQUEST_CODE) {
            //Solo se inicia si se conceden los permisos
            if(!RequestPermissionUtil.allPermissionsGranted(permissions, grantResults)){
                mostrarMensaje(tituloErrorPermisos, mensajeErrorPermisos);
                RequestPermissionUtil.showAppSettings(this);
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Cuando presiona el botón atrás en el dispositivo,
        // se llama a esta función y lo lleva a la actividad anterior realizada
        // llamando a la función de superclase, es decir, super.onBackPressed ().
        // Si no se llama a esta función, no ocurrirá nada, por lo tanto,
        // si desea deshabilitar la contrapresión, anule el onBackPressed ()
        // y comente la llamada a la superclase.
    }

    @Override
    public void onPermissionsGranted() {
        validarCampos();
    }


    private class validarActivacionBtnIngresar implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            limpiarVistaError();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //implementación predeterminada ignorada
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edtUsuario.getText() != null && edtContrasena.getText() != null) {
                String usuario = edtUsuario.getText().toString();
                String contrasena = edtContrasena.getText().toString();
                loginPresenter.validarCamposVacios(usuario, contrasena);
            }
        }
    }
}
