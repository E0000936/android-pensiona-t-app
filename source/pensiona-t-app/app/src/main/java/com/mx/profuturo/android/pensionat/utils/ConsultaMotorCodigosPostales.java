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
package com.mx.profuturo.android.pensionat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * <h1>ConsultaMotorCodigosPostales</h1>
 * Clase de utilidad para obtener la información de un domicilio por medio
 * del código postal invocando al apk de códigos postales Profuturo
 * que deberá estar instalada en el dispositivo.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-01
 */
public class ConsultaMotorCodigosPostales {
    private static String URI_MOTOR_CODIGOS_POSTALES = "content://" + Constantes.Packages.PACKAGE_MOTOR_CODIGOS_POSTALES;

    public ConsultaMotorCodigosPostales() {
        throw new IllegalStateException("Clase de ayuda");
    }

    public static Cursor consultaCodigosPostales(Context context, Uri clientesUri) {
        ContentResolver cr = context.getContentResolver();
        return cr.query(clientesUri, null, null, null, null);
    }

    public static Uri obtenerUriCodigoPostal(String codigoPostal) {
        return Uri.parse(URI_MOTOR_CODIGOS_POSTALES + "/codigoPostales/" + codigoPostal);
    }
}
