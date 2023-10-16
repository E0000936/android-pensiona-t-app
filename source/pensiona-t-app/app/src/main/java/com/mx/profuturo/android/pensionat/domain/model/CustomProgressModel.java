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
package com.mx.profuturo.android.pensionat.domain.model;

/**
 * <h1>CustomProgressModel</h1>
 * Clase que sirve para la visualización
 * del componente customizado "CustomProgressBar"
 * en la sección de formulario.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-16
 */
public class CustomProgressModel {
    private int sectionCount;
    private String labelName;

    public CustomProgressModel(int sectionCount, String labelName) {
        this.sectionCount = sectionCount;
        this.labelName = labelName;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public String getLabelName() {
        return labelName;
    }
}

