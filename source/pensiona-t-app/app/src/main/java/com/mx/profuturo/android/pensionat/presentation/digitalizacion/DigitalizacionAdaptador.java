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
package com.mx.profuturo.android.pensionat.presentation.digitalizacion;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;

import java.util.List;

/**
 * <h1>DigitalizacionAdaptador</h1>
 * Clase adaptador de la lista de documentos asociados al trámite
 * aplica el diseño de los distintos estados de captura en los que
 * se encuentra.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-20
 */
public class DigitalizacionAdaptador extends RecyclerView.Adapter<DigitalizacionAdaptador.DigitalizacionViewHolder> {
    private static final String DESC_OBSERVACIONES = "OBSERVACIONES";
    public Context contexto;
    public int posicionSeleccionada = 0;
    OnItemClickListener listener;
    private List<Digitalizacion> items;


    public DigitalizacionAdaptador(List<Digitalizacion> items, Context contexto) {
        this.items = items;
        this.contexto = contexto;
    }

    public void setPosicionSeleccionada(int posicion) {
        posicionSeleccionada = posicion;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public DigitalizacionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_documentos, viewGroup, false);
        return new DigitalizacionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DigitalizacionViewHolder viewHolder, final int position) {
        Digitalizacion documento = items.get(position);
        viewHolder.txtNombreDocumento.setText(documento.getDescripcion());
        if (documento.getDescripcion().equals(DESC_OBSERVACIONES)) {
            viewHolder.txtNumeroCapturas.setText(documento.getAyuda());
        } else {
            String capturasTomadasVsObligatorias = documento.getTomarCaptura() + " de " + documento.getCapturas();
            viewHolder.txtNumeroCapturas.setText(capturasTomadasVsObligatorias);
        }

        if (documento.getEsObligatorio() == 1) {
            viewHolder.circuloObligatorio.setVisibility(View.VISIBLE);
        } else {
            viewHolder.circuloObligatorio.setVisibility(View.GONE);
        }

        if (position == posicionSeleccionada && documento.getTomarCaptura() > 0) {
            // Diseño celda seleccionada y con más de una captura tomada
            viewHolder.cardView.setBackgroundResource(R.drawable.fondo_borde_azul_ceruleo);
            viewHolder.flechaItem.setVisibility(View.GONE);
            viewHolder.completado.setVisibility(View.VISIBLE);
            viewHolder.cardView.setCardElevation(10);
            viewHolder.cardView.setUseCompatPadding(true);
            viewHolder.txtNombreDocumento.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzulCeruleo));
            viewHolder.txtNumeroCapturas.setTextColor(ContextCompat.getColor(contexto, R.color.colorTextoSecondario));
        } else if (documento.getTomarCaptura() != 0) {
            // Diseño celda no seleccionada y con más de una captura tomada
            viewHolder.cardView.setBackgroundResource(R.drawable.fondo_azul_documento);
            viewHolder.cardView.setCardElevation(0);
            viewHolder.constraintLayout.setElevation(0);
            viewHolder.circuloObligatorio.setVisibility(View.GONE);
            viewHolder.flechaItem.setVisibility(View.GONE);
            viewHolder.completado.setVisibility(View.VISIBLE);
            viewHolder.txtNombreDocumento.setTextColor(Color.WHITE);
            viewHolder.txtNumeroCapturas.setTextColor(Color.WHITE);
        } else if (position == posicionSeleccionada) {
            // Diseño celda seleccionada
            viewHolder.cardView.setCardElevation(10);
            viewHolder.cardView.setBackground(contexto.getDrawable(R.drawable.fondo_borde_azul_ceruleo));
            viewHolder.flechaItem.setVisibility(View.VISIBLE);
            viewHolder.completado.setVisibility(View.GONE);
            viewHolder.cardView.setUseCompatPadding(true);
            viewHolder.constraintLayout.setElevation(10);
            viewHolder.txtNombreDocumento.setTextColor(ContextCompat.getColor(contexto, R.color.colorAzulCeruleo));
        } else {
            // Diseño celda no seleccionada y sin capturas tomadas
            viewHolder.cardView.setCardElevation(0);
            viewHolder.constraintLayout.setElevation(0);
            viewHolder.flechaItem.setVisibility(View.GONE);
            viewHolder.cardView.setUseCompatPadding(true);
            viewHolder.cardView.setBackground(contexto.getDrawable(R.drawable.item_sombra));
            viewHolder.txtNombreDocumento.setTextColor(ContextCompat.getColor(contexto, R.color.colorTextoSecondario));
            viewHolder.txtNumeroCapturas.setTextColor(ContextCompat.getColor(contexto, R.color.colorTextoSecondario));
        }
    }

    // Se utiliza para almacenar en caché las vistas dentro del diseño del elemento para un acceso rápido.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Definir la interfaz del oyente.
    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    class DigitalizacionViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreDocumento;
        TextView txtNumeroCapturas;
        RelativeLayout constraintLayout;
        CardView cardView;
        View circuloObligatorio;
        ImageView flechaItem;
        ImageView completado;

        DigitalizacionViewHolder(final View itemView) {
            super(itemView);
            this.circuloObligatorio = itemView.findViewById(R.id.circulo_rojo_digitalizacion_celda);
            this.cardView = itemView.findViewById(R.id.celda_digitalizacion_uno);
            this.txtNombreDocumento = itemView.findViewById(R.id.txt_digitalizacion_celda_documento);
            this.txtNumeroCapturas = itemView.findViewById(R.id.txt_digitalizacion_celda_capturas);
            this.cardView = itemView.findViewById(R.id.celda_digitalizacion_uno);
            this.flechaItem = itemView.findViewById(R.id.flecha_item);
            this.completado = itemView.findViewById(R.id.img_completado);
            cardView.setCardElevation(0);
            cardView.setBackground(contexto.getDrawable(R.drawable.item_sombra));
            this.constraintLayout = itemView.findViewById(R.id.celda_digitalizacion_dos);

            itemView.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                posicionSeleccionada = position;
                setPosicionSeleccionada(position);
                listener.onItemClick(v, position);
            });
        }
    }
}


