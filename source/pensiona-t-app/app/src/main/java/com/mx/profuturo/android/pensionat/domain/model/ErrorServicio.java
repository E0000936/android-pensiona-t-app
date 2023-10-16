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
 * <h1>ErrorServicio</h1>
 * Clase que contiene la estructura de error customizada,
 * retornada en las clases de ejecución de los servicios.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-05-24
 */
public class ErrorServicio {
    private int estatus;
    private String titulo;
    private String mensaje;

    public ErrorServicio(int estatus, String titulo, String mensaje) {
        this.estatus = estatus;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
