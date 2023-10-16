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
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Componente;
import com.mx.profuturo.android.pensionat.domain.model.Grupo;

import java.util.List;

/**
 * <h1>GrupoFamiliarAdaptador</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class GrupoFamiliarAdaptador extends PagerAdapter implements ComponenteFamiliarAdapter.ComponenteFamiliarInterface {
    private static final String DEFAULT_NOMBRE_GRUPO = "Grupo familiar";
    private SparseArray<ViewGroup> registeredFragments = new SparseArray<>();
    private GrupoFamiliarInterfaz delegado;
    private Context contexto;
    private List<Grupo> grupos;

    GrupoFamiliarAdaptador(Context contexto, List<Grupo> grupos, GrupoFamiliarInterfaz delegado) {
        this.contexto = contexto;
        this.grupos = grupos;
        this.delegado = delegado;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.vista_grupo_familiar, collection, false);
        collection.addView(layout);
        RadioButton grupoFamiliar = layout.findViewById(R.id.radio_grupo_familiar_nombre);
        TextView txtSexo = layout.findViewById(R.id.txt_grupo_sexo);
        RecyclerView componentes = layout.findViewById(R.id.recycler_componente_familiar);
        LinearLayoutManager llm = new LinearLayoutManager(contexto);
        componentes.setLayoutManager(llm);

        Grupo grupo = grupos.get(position);
        if (grupo.getApMaternoTc() != null &&
                grupo.getApPaternoTc() != null &&
                grupo.getNombreTc() != null) {
            String nombreGrupo = grupo.getApPaternoTc() + " " + grupo.getApMaternoTc() + " " + grupo.getNombreTc();
            grupoFamiliar.setText(nombreGrupo);
        } else {
            grupoFamiliar.setText(DEFAULT_NOMBRE_GRUPO);
        }

        grupoFamiliar.setOnClickListener((View vista) -> cambiarDisenoSeleccionado(grupo));
        if (grupo.getSexoOferta() != null) {
            txtSexo.setText(grupo.getSexoOferta());
        }

        if (grupo.getSexoPoliza() != null) {
            txtSexo.setText(grupo.getSexoPoliza());
        }
        ComponenteFamiliarAdapter adapter = new ComponenteFamiliarAdapter(grupo.getBeneficiarios(), this);
        componentes.setAdapter(adapter);

        registeredFragments.put(position, layout);
        return layout;
    }

    private void cambiarDisenoSeleccionado(Grupo grupo) {
        delegado.notificarGrupoSeleccionado(grupo);
    }

    public ViewGroup getRegisteredFragment(int posicion) {
        return registeredFragments.get(posicion);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        registeredFragments.remove(position);
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return grupos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public void componenteSeleccionado(Componente componente) {
        delegado.notificarComponenteSeleccionado(componente);
    }

    public interface GrupoFamiliarInterfaz {
        void notificarGrupoSeleccionado(Grupo grupo);

        void notificarComponenteSeleccionado(Componente componente);
    }
}
