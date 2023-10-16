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
package com.mx.profuturo.android.pensionat.presentation.tramite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Documento;

import java.util.List;

/**
 * <h1>DocumentoTramiteAdapter</h1>
 * Clase adaptador de la lista de documentos asociados
 * a un trámite.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-25
 */
public class DocumentoTramiteAdapter extends RecyclerView.Adapter<DocumentoTramiteAdapter.DocumentoViewHolder> {
    private List<Documento> documentos;

    DocumentoTramiteAdapter(List<Documento> documentos) {
        this.documentos = documentos;
    }

    @NonNull
    @Override
    public DocumentoTramiteAdapter.DocumentoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.celda_tramite_documento, viewGroup, false);
        return new DocumentoTramiteAdapter.DocumentoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentoTramiteAdapter.DocumentoViewHolder documentoViewHolder, int i) {
        Documento documento = documentos.get(i);
        documentoViewHolder.txtDocumento.setText(documento.getDescripcion());
        documentoViewHolder.txtAyuda.setText(documento.getAyuda());

        if (documento.getEsObligatorio() == 1) {
            documentoViewHolder.circuloObligatorio.setVisibility(View.VISIBLE);
        } else {
            documentoViewHolder.circuloObligatorio.setVisibility(View.GONE);
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

    class DocumentoViewHolder extends RecyclerView.ViewHolder {
        TextView txtDocumento;
        TextView txtAyuda;
        View circuloObligatorio;

        DocumentoViewHolder(View itemView) {
            super(itemView);
            txtDocumento = itemView.findViewById(R.id.txt_tramite_documento_nombre);
            txtAyuda = itemView.findViewById(R.id.txt_tramite_documento_ayuda);
            circuloObligatorio = itemView.findViewById(R.id.view_tramite_celda_rojo);
        }
    }
}
