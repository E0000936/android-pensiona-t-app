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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.profuturo.android.pensionat.R;
import com.mx.profuturo.android.pensionat.domain.model.Captura;

import java.io.File;
import java.util.List;

/**
 * <h1>CapturasAdaptador</h1>
 * Clase adaptador para la captura de imágenes.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-16
 */
public class CapturasAdaptador extends BaseAdapter {
    //Lista de capturas
    private List<Captura> capturas;
    //contexto del adaptador
    private Context context;

    //constructor del adaptador
    public CapturasAdaptador(Context context, List<Captura> capturas) {
        this.context = context;
        this.capturas = capturas;
    }

    //tamaño de la lista
    @Override
    public int getCount() {
        return capturas.size();
    }

    //elemento de la lista
    @Override
    public Object getItem(int position) {
        return capturas.get(position);
    }

    //posicion del elemento de la lista
    @Override
    public long getItemId(int position) {
        return position;
    }

    //inflamos el layout asignamos una imagen y validamos si la imagen esta caputara y si no es null capturamos y si no tenemos una imagen pone el numero en el grid
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            //obtenermos la posicion
            Captura captura = capturas.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //si el estatus de la captura es igual a 1 cargamos la imagen de la toma de documento
            if (captura.getEstatus() == 1) {
                view = inflater.inflate(R.layout.celda_digitalizacion_captura_lista, viewGroup, false);
                ImageView imagenDocumento = view.findViewById(R.id.img_digitalizacion_celda_lista_documento);
                //validacion si es diferente de null si tiene un nombre la captura
                if (captura.getRuta() != null) {
                    File archivoImagen = new File(captura.getRuta());
                    Log.wtf("RUTA ARCHIVO", captura.getRuta());
                    if (archivoImagen.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(archivoImagen.getAbsolutePath());
                        imagenDocumento.setImageBitmap(bitmap);
                    }
                }
            } else {
                view = inflater.inflate(R.layout.celda_digitalizacion_captura, viewGroup, false);
                TextView numero = view.findViewById(R.id.txt_digitalizacion_celda_num_captura);
                numero.setText(String.valueOf(captura.getNumero()));
            }
        }
        //retorno del adaptador
        return view;
    }
}
