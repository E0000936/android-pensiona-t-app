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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Categoria;

import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * <h1>GenericoAdaptador</h1>
 * Clase adaptador utilizada para la visualización de
 * los dropdowns.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class GenericoAdaptador extends ArrayAdapter<Categoria> implements SpinnerAdapter {

    public int posicionSeleccionada;

    public GenericoAdaptador(Context contexto, ArrayList<Categoria> tipos) {
        super(contexto, 0, tipos);
    }

    public void setPosicionSeleccionada(int posicion) {
        posicionSeleccionada = posicion;
        notifyDataSetChanged();
    }

    @Override
    @Nonnull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Categoria categoria = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_simple_celda, parent, false);
            viewHolder.txtTipo = convertView.findViewById(R.id.txtView_spinner);
            viewHolder.fondoSpinner = convertView.findViewById(R.id.layout_spinner);
            viewHolder.icono = convertView.findViewById(R.id.img_spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (categoria != null) {
            String tipo = categoria.getDescripcion();
            viewHolder.txtTipo.setText(tipo);
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        Categoria categoria = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_simple_celda_desplegable, parent, false);
            viewHolder.txtTipo = convertView.findViewById(R.id.txtView_spinner);
            viewHolder.fondoSpinner = convertView.findViewById(R.id.layout_spinner);
            viewHolder.icono = convertView.findViewById(R.id.img_spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (posicionSeleccionada == position) {
            viewHolder.txtTipo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAzulCeruleo));
            viewHolder.fondoSpinner.setBackgroundColor(ContextCompat.getColor(getContext(), (R.color.colorGris)));
            viewHolder.icono.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtTipo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextoElemento));
            viewHolder.fondoSpinner.setBackgroundColor(Color.WHITE);
            viewHolder.icono.setVisibility(View.GONE);
        }

        if (categoria != null) {
            String tipo = categoria.getDescripcion();
            viewHolder.txtTipo.setText(tipo);
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView txtTipo;
        private View fondoSpinner;
        private ImageView icono;
    }
}

