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
package com.mx.profuturo.android.pensionat.vistasCustomizadas;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

/**
 * <h1>SpinnerCaja</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-07
 */
public class SpinnerCaja extends AppCompatSpinner {
    private OnSpinnerEventsListener delegado;
    private boolean estaAbierto = false;


    public SpinnerCaja(Context contexto, AttributeSet atributos, int defEstilo) {
        super(contexto, atributos, defEstilo);
    }

    public SpinnerCaja(Context contexto, AttributeSet atributos) {
        super(contexto, atributos);
    }

    public SpinnerCaja(Context contexto) {
        super(contexto);
    }

    @Override
    public boolean performClick() {
        estaAbierto = true;
        if (delegado != null) {
            delegado.onSpinnerOpened(this);
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener) {
        delegado = onSpinnerEventsListener;
    }

    /**
     * Propaga el evento hacia el delegado cuando el Spinner se ha cerrado
     */
    public void llamarEventoCierre() {
        estaAbierto = false;
        if (delegado != null) {
            delegado.onSpinnerClosed(this);
        }
    }

    /**
     * Una bandera booleana que indica que el Spinner activó un evento abierto
     *
     * @return true cuando el spinner ha sido abierto
     */
    public boolean seHaAbierto() {
        return estaAbierto;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (seHaAbierto() && hasWindowFocus) {
            llamarEventoCierre();
        }
    }

    public interface OnSpinnerEventsListener {
        void onSpinnerOpened(Spinner spinner);

        void onSpinnerClosed(Spinner spinner);
    }
}