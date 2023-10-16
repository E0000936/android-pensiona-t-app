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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.formulario.FormularioFragment;
import com.mx.profuturo.android.pensionat.presentation.login.LoginActivity;
import com.mx.profuturo.android.pensionat.presentation.login.MainActivity;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;

/**
 * <h1>PrincipalActivity</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-21
 */
public class PrincipalActivity extends MainActivity
        implements FormularioFragment.FormularioFragmentInterfaz {

    private static final String TAG = PrincipalActivity.class.getSimpleName();
    private TextView txtTitulo;
    private TextView txtSubtitulo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        FormularioFragment fragment = new FormularioFragment();
        fragment.delegado = this;

        // Si entra en la validación de caso cerrado se direcciona a captura formulario,
        // de lo contrario se muestra pantalla selección componente familiar
        Boolean validarCasoCerrado = getIntent().getBooleanExtra("casoCerrado", false);
        VariablesGlobales.getInstance().folio = validarCasoCerrado;

        if (validarCasoCerrado) {
            FragmentTransaction navegacionSinDatos = getSupportFragmentManager().beginTransaction();
            navegacionSinDatos.replace(R.id.frame_principal, fragment, "FormularioFragment");
            navegacionSinDatos.commit();
        } else {
            FragmentTransaction navegacion = getSupportFragmentManager().beginTransaction();
            navegacion.replace(R.id.frame_principal, new ComponenteFamiliarFragment(), "ComponenteFamiliarFragment");
            navegacion.commit();
        }

        View barra = findViewById(R.id.include_barra_titulo_principal);
        txtTitulo = barra.findViewById(R.id.text_barra_principal_titulo);
        txtSubtitulo = barra.findViewById(R.id.text_barra_principal_subtitulo);
        ImageButton btnCerrarSesion = barra.findViewById(R.id.btn_cerrar_sesion);
        ImageButton btnAtras = barra.findViewById(R.id.btn_barra_principal_atras);

        btnCerrarSesion.setOnClickListener((View v) -> mostrarDialogoCerrarSesion());
        btnAtras.setOnClickListener((View v) -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_principal);
            if (currentFragment != null && currentFragment.getClass() == FormularioFragment.class) {
                ((FormularioFragment) currentFragment).moverPagina();
            } else {
                ejecutarNavegacion();
            }
        });
    }

    @Override
    public void ejecutarNavegacion() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * La sección de formulario esta conformada por los apartados de datos personales
     * referencias y datos bancarios dentro de un fragment page adapter,
     * mediante el backpressed se realiza el cambio entre dichos apartados.
     */
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_principal);
        if (currentFragment != null && currentFragment.getClass() == FormularioFragment.class) {
            ((FormularioFragment) currentFragment).moverPagina();
        } else {
            ejecutarNavegacion();
        }
    }

    private void mostrarDialogoCerrarSesion() {
        AlertaDialogo cerrarSesionDialogo = new AlertaDialogo();
        cerrarSesionDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(getApplicationContext());
            Intent next = new Intent(PrincipalActivity.this, LoginActivity.class);
            next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(next);
            finish();
        });
        cerrarSesionDialogo.show(getSupportFragmentManager(), TAG);
    }

    public void cambiarTituloBarra(String titulo, String subtitulo) {
        txtTitulo.setText(titulo);
        txtSubtitulo.setText(subtitulo);
    }

    @Override
    public void cancelarTramite() {
        finish();
    }
}
