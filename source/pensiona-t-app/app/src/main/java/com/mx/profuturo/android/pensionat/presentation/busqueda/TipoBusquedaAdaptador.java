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

import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * <h1>TipoBusquedaAdaptador</h1>
 * Clase adaptador utilizada para la visualización del dropdown
 * de selección de tipo de búsqueda.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class TipoBusquedaAdaptador extends ArrayAdapter<String> implements SpinnerAdapter {

    public int posicionActual;

    public TipoBusquedaAdaptador(Context contexto, ArrayList<String> tipos) {
        super(contexto, 0, tipos);
    }

    public void setPosicionActual(int posicion) {
        posicionActual = posicion;
        notifyDataSetChanged();
    }

    @Override
    @Nonnull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String tipo = getItem(position);

        ViewHolder viewHolderGetView;
        if (convertView == null) {
            viewHolderGetView = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_simple_celda, parent, false);
            viewHolderGetView.txtTipo = convertView.findViewById(R.id.txtView_spinner);
            viewHolderGetView.fondoSpinner = convertView.findViewById(R.id.layout_spinner);
            viewHolderGetView.icono = convertView.findViewById(R.id.img_spinner);
            convertView.setTag(viewHolderGetView);
        } else {
            viewHolderGetView = (ViewHolder) convertView.getTag();
        }

        viewHolderGetView.txtTipo.setText(tipo);

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return (position != 0);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        String tipo = getItem(position);

        ViewHolder viewHolderDropDown;
        if (convertView == null) {
            viewHolderDropDown = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_simple_celda_desplegable, parent, false);
            viewHolderDropDown.txtTipo = convertView.findViewById(R.id.txtView_spinner);
            viewHolderDropDown.fondoSpinner = convertView.findViewById(R.id.layout_spinner);
            viewHolderDropDown.icono = convertView.findViewById(R.id.img_spinner);
            convertView.setTag(viewHolderDropDown);
        } else {
            viewHolderDropDown = (ViewHolder) convertView.getTag();
        }

        if (posicionActual == position) {
            viewHolderDropDown.txtTipo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAzulCeruleo));
            viewHolderDropDown.fondoSpinner.setBackgroundColor(ContextCompat.getColor(getContext(), (R.color.colorGrisClaro)));
            viewHolderDropDown.icono.setVisibility(View.VISIBLE);
        } else {
            viewHolderDropDown.txtTipo.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextoElemento));
            viewHolderDropDown.fondoSpinner.setBackgroundColor(Color.WHITE);
            viewHolderDropDown.icono.setVisibility(View.GONE);
        }
        viewHolderDropDown.txtTipo.setText(tipo);
        return convertView;
    }

    private static class ViewHolder {
        private TextView txtTipo;
        private View fondoSpinner;
        private ImageView icono;
    }
}

