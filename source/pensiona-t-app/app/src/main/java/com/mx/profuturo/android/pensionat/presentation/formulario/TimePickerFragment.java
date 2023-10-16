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

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * <h1>TimePickerFragment</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-24
 */
public class TimePickerFragment extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener delegado;

    public static TimePickerFragment nuevaInstancia(TimePickerDialog.OnTimeSetListener delegado) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setListener(delegado);
        return fragment;
    }

    public void setListener(TimePickerDialog.OnTimeSetListener delegado) {
        this.delegado = delegado;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener la hora actual para mostrarla en el picker
        final Calendar calendario = Calendar.getInstance();
        int horas = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), delegado, horas, minutos, false);
    }
}
