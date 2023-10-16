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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <h1>DatePickerFragment</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-20
 */
public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener delegado;

    public static DatePickerFragment nuevaInstancia(String fecha, DatePickerDialog.OnDateSetListener delegado) {
        Bundle bundle = new Bundle();
        bundle.putString("fecha", fecha);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(delegado);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener delegado) {
        this.delegado = delegado;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener la fecha actual para mostrarla en el picker
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        if (getArguments() != null) {
            String fecha = getArguments().getString("fecha");
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));
            try {
                Date fechaNacimiento = format1.parse(fecha);
                final Calendar calendarioNacimiento = new GregorianCalendar();
                calendarioNacimiento.setTime(fechaNacimiento);
                ano = calendarioNacimiento.get(Calendar.YEAR);
                mes = calendarioNacimiento.get(Calendar.MONTH);
                dia = calendarioNacimiento.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (getActivity() != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), delegado, ano, mes, dia);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        } else {
            throw new IllegalStateException("null retornado por getActivity()");
        }
    }
}