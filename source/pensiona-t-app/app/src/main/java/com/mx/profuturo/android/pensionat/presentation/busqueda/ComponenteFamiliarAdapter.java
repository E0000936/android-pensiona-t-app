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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Componente;

import java.util.List;

/**
 * <h1>ComponenteFamiliarAdapter</h1>
 * Adaptador de selección componente familiar.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class ComponenteFamiliarAdapter extends RecyclerView.Adapter<ComponenteFamiliarAdapter.PolizaViewHolder> {

    private ComponenteFamiliarInterface delegado;
    private List<Componente> componentes;
    private int posicionSeleccionada = -1;

    ComponenteFamiliarAdapter(List<Componente> componentes, ComponenteFamiliarInterface delegado) {
        this.componentes = componentes;
        this.delegado = delegado;
    }

    @NonNull
    @Override
    public PolizaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_componente_familiar, viewGroup, false);
        return new PolizaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PolizaViewHolder polizaViewHolder, int i) {
        Componente componente = componentes.get(i);
        polizaViewHolder.txtNombre.setText(componente.getNombreCompleto());
        polizaViewHolder.txtSexo.setText(componente.getSexo());
        polizaViewHolder.txtNacimiento.setText(componente.getFechaNacimiento());
        polizaViewHolder.txtParentesco.setText(componente.getParentesco());

        if (posicionSeleccionada == i) {
            polizaViewHolder.componenteFamiliar.setChecked(true);
            polizaViewHolder.contornoSeleccion.setVisibility(View.VISIBLE);
        } else {
            polizaViewHolder.componenteFamiliar.setChecked(false);
            polizaViewHolder.contornoSeleccion.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return componentes.size();
    }

    @FunctionalInterface
    public interface ComponenteFamiliarInterface {
        void componenteSeleccionado(Componente componente);
    }

    class PolizaViewHolder extends RecyclerView.ViewHolder {
        RadioButton componenteFamiliar;
        TextView txtNombre;
        TextView txtParentesco;
        TextView txtNacimiento;
        TextView txtSexo;
        View contornoSeleccion;

        PolizaViewHolder(View itemView) {
            super(itemView);
            componenteFamiliar = itemView.findViewById(R.id.radio_componente_familiar_nombre);
            txtNombre = itemView.findViewById(R.id.txt_componente_nombre);
            txtParentesco = itemView.findViewById(R.id.txt_componente_parentesco);
            txtNacimiento = itemView.findViewById(R.id.txt_componente_nacimiento);
            txtSexo = itemView.findViewById(R.id.txt_componente_sexo);
            contornoSeleccion = itemView.findViewById(R.id.view_componente_contorno);
            componenteFamiliar.setOnClickListener((View vista) -> {
                if (posicionSeleccionada != getAdapterPosition()) {
                    posicionSeleccionada = getAdapterPosition();
                } else {
                    posicionSeleccionada = -1;
                }
                if (posicionSeleccionada >= 0) {
                    Componente componente = componentes.get(posicionSeleccionada);
                    delegado.componenteSeleccionado(componente);
                } else {
                    delegado.componenteSeleccionado(new Componente());
                }

                notifyDataSetChanged();
            });
        }
    }
}
