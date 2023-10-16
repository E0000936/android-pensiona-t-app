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
package com.mx.profuturo.android.pensionat.presentation.formulario;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.data.local.db.realm.DBHelperRealm;
import com.mx.profuturo.android.pensionat.data.local.preferences.Sesion;
import com.mx.profuturo.android.pensionat.domain.model.CustomProgressModel;
import com.mx.profuturo.android.pensionat.presentation.busqueda.PrincipalActivity;
import com.mx.profuturo.android.pensionat.presentation.dialogs.AlertaDialogo;
import com.mx.profuturo.android.pensionat.presentation.tramite.TramiteFragment;
import com.mx.profuturo.android.pensionat.utils.Utilidades;
import com.mx.profuturo.android.pensionat.utils.VariablesGlobales;
import com.mx.profuturo.android.pensionat.vistasCustomizadas.CustomProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>FormularioFragment</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class FormularioFragment extends Fragment
        implements DatosPersonalesFragment.FormularioGeneralesInterfaz,
        ReferenciasFragment.ReferenciasInterfaz, DatosBancariosFragment.DatosBancariosInterfaz {

    private static final int NUM_CAMPOS_OBLIGATORIOS_SOLICITANTE = 16;
    private static final int NUM_CAMPOS_OBLIGATORIOS_REFERENCIAS = 4;
    private static final int NUM_CAMPOS_OBLIGATORIOS_BANCO = 3;
    private static final int SECCION_GENERALES = 0;
    private static final int SECCION_REFERENCIAS = 1;
    private static final int SECCION_BANCO = 2;
    public FormularioFragmentInterfaz delegado;
    private CustomProgressBar customProgressBar1;
    private CustomViewPager pager;
    private Context contexto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context contexto = container.getContext();
        if ((getActivity()) != null) {
            ((PrincipalActivity) getActivity()).cambiarTituloBarra(contexto.getString(R.string.formulario_titulo), contexto.getString(R.string.formulario_subtitulo));
        }
        return inflater.inflate(R.layout.fragmento_formulario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView txtEmpleado = view.findViewById(R.id.txt_componente_empleado);
        String numeroEmpleado = getString(R.string.busqueda_empleado, Sesion.getInstancia(getContext()).getNumeroEmpleado());
        txtEmpleado.setText(numeroEmpleado);
        TextView tvTitularComponente = view.findViewById(R.id.tvComponente);
        tvTitularComponente.setText(DBHelperRealm.obtenerTitularPoliza());
        List<Fragment> fragmentList = new ArrayList<>();
        DatosPersonalesFragment dp = new DatosPersonalesFragment();
        dp.delegado = this;
        ReferenciasFragment rf = new ReferenciasFragment();
        rf.delegado = this;
        DatosBancariosFragment db = new DatosBancariosFragment();
        db.delegado = this;
        fragmentList.add(dp);
        fragmentList.add(rf);
        fragmentList.add(db);

        pager = view.findViewById(R.id.pager);
        pager.setPagingEnabled(false);
        SingleFragmentPagerAdapter adapter = new SingleFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        pager.setAdapter(adapter);
        pager.setCurrentItem(SECCION_GENERALES);
        pager.setPageMargin(0);

        customProgressBar1 = view.findViewById(R.id.custom_progress);
        CustomProgressModel customProgressModel1 = new CustomProgressModel(NUM_CAMPOS_OBLIGATORIOS_SOLICITANTE, "");
        CustomProgressModel customProgressModel2 = new CustomProgressModel(NUM_CAMPOS_OBLIGATORIOS_REFERENCIAS, "Datos del solicitante");
        CustomProgressModel customProgressModel3 = new CustomProgressModel(NUM_CAMPOS_OBLIGATORIOS_BANCO, "Referencias personales");
        CustomProgressModel customProgressModel4 = new CustomProgressModel(0, "Datos bancarios");

        List<CustomProgressModel> formsModelList = new ArrayList<>();
        formsModelList.add(customProgressModel1);
        formsModelList.add(customProgressModel2);
        formsModelList.add(customProgressModel3);
        formsModelList.add(customProgressModel4);

        customProgressBar1.addProgressForms(formsModelList);
        customProgressBar1.iniciarSeccion(SECCION_GENERALES);

        if (VariablesGlobales.getInstance().progressCompleto) {
            VariablesGlobales.getInstance().progressCompleto = false;
            llenarProgressBar();
        }
        View viewContenedor = view.findViewById(R.id.formularioConstraint);
        viewContenedor.setOnClickListener((View v) -> esconderTeclado(viewContenedor));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            contexto = context;
        }
    }

    private void llenarProgressBar() {
        customProgressBar1.cambiarProgreso(NUM_CAMPOS_OBLIGATORIOS_SOLICITANTE, SECCION_GENERALES);
        customProgressBar1.cambiarProgreso(NUM_CAMPOS_OBLIGATORIOS_REFERENCIAS, SECCION_REFERENCIAS);
        customProgressBar1.cambiarProgreso(NUM_CAMPOS_OBLIGATORIOS_BANCO, SECCION_BANCO);
        customProgressBar1.iniciarSeccion(SECCION_BANCO);
    }

    public void moverPagina() {
        if (pager.getCurrentItem() == SECCION_BANCO) {
            pager.setCurrentItem(SECCION_REFERENCIAS);
            customProgressBar1.iniciarSeccion(pager.getCurrentItem());
        } else if (pager.getCurrentItem() > SECCION_GENERALES) {
            pager.setCurrentItem(SECCION_GENERALES);
            customProgressBar1.iniciarSeccion(pager.getCurrentItem());
        } else {
            delegado.ejecutarNavegacion();
        }
    }

    @Override
    public void enviarProgreso(int progreso, int seccion) {
        if (seccion == pager.getCurrentItem()) {
            customProgressBar1.cambiarProgreso(progreso, seccion);
        }
    }

    @Override
    public void cancelarTramite() {
        String titulo = contexto.getString(R.string.componente_familiar_cancelar_tramite);
        String mensaje = contexto.getString(R.string.componente_familiar_cancelar_aviso);
        String confirmacion = contexto.getString(R.string.componente_familiar_cancelar_confirmacion);
        AlertaDialogo cancelarDialogo = AlertaDialogo.getInstancia(titulo, mensaje, confirmacion);
        cancelarDialogo.setDelegado(() -> {
            Utilidades.limpiarDatosTramite(contexto);
            delegado.cancelarTramite();
        });
        if (getFragmentManager() != null) {
            cancelarDialogo.show(getFragmentManager(), "");
        }
    }

    @Override
    public void mostrarReferencias() {
        customProgressBar1.iniciarSeccion(SECCION_REFERENCIAS);
        pager.setCurrentItem(SECCION_REFERENCIAS);
    }

    @Override
    public void mostrarSeleccionTramite() {
        if (getFragmentManager() != null) {
            FragmentTransaction formulario = getFragmentManager().beginTransaction();
            TramiteFragment tramiteFragment = new TramiteFragment();
            formulario.replace(R.id.frame_principal, tramiteFragment, "TramiteFragment");
            formulario.addToBackStack("FormularioFragment");
            formulario.commit();
        }
    }

    @Override
    public void mostrarDatosBancarios() {
        customProgressBar1.iniciarSeccion(SECCION_BANCO);
        pager.setCurrentItem(SECCION_BANCO);
    }

    public void esconderTeclado(View vista) {
        InputMethodManager inputMethodManager = (InputMethodManager) contexto.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(vista.getWindowToken(), 0);
    }

    public interface FormularioFragmentInterfaz {
        void cancelarTramite();

        void ejecutarNavegacion();
    }


}
