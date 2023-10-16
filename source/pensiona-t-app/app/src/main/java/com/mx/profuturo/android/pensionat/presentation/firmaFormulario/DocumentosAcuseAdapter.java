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
package com.mx.profuturo.android.pensionat.presentation.firmaFormulario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Digitalizacion;

import java.util.List;

/**
 * <h1>DocumentosAcuseAdapter</h1>
 * Clase adaptador utilizado para representar la lista de documentos
 * entregados en la previsualización del formato acuse.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-27
 */
public class DocumentosAcuseAdapter extends RecyclerView.Adapter<DocumentosAcuseAdapter.DocumentosAcuseViewHolder> {
    private static final String TEXTO_ENTREGADO = "SI ESTA ENTREGADO";
    private static final String TEXTO_NO_ENTREGADO = "NO";
    private List<Digitalizacion> documentos;

    DocumentosAcuseAdapter(List<Digitalizacion> documentos) {
        this.documentos = documentos;
    }

    @NonNull
    @Override
    public DocumentosAcuseAdapter.DocumentosAcuseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_acuse_documento, viewGroup, false);
        return new DocumentosAcuseAdapter.DocumentosAcuseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentosAcuseAdapter.DocumentosAcuseViewHolder documentoViewHolder, int i) {
        Digitalizacion documento = documentos.get(i);
        documentoViewHolder.txtDocumento.setText(documento.getDescripcion());
        if (documento.getTomarCaptura() > 0) {
            documentoViewHolder.txtEntregado.setText(TEXTO_ENTREGADO);
        } else {
            documentoViewHolder.txtEntregado.setText(TEXTO_NO_ENTREGADO);
        }
    }

    @Override
    public int getItemCount() {
        return documentos.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class DocumentosAcuseViewHolder extends RecyclerView.ViewHolder {
        TextView txtDocumento;
        TextView txtEntregado;

        DocumentosAcuseViewHolder(View itemView) {
            super(itemView);
            txtDocumento = itemView.findViewById(R.id.txt_acuse_celda_documento);
            txtEntregado = itemView.findViewById(R.id.txt_acuse_celda_entregado);
        }
    }
}