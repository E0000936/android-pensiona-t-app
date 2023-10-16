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

import androidx.core.util.PatternsCompat;

import java.util.regex.Pattern;

/**
 * <h1>ValidadorFormulario</h1>
 * Clase de utilidad para realizar comparaciones con expresiones regulares.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-11
 */
public class ValidadorFormulario {
    private static final int MAX_NUM_REPETIDOS = 9;
    private static final int MAX_NUM_CONSECUTIVOS = 7;
    private static final Pattern contrasenaRegex = Pattern.compile("^(?=(.*[a-zA-Z\\u00f1\\u00d1]){4})" +
            "(?=(.*[@#_\\-/*$¡¿=+]){2})(?=(.*\\d){2})" +
            "[a-zA-Z\\u00f1\\u00d1@#_\\-/*$¡¿=+\\d]{12,16}$");
    private static final Pattern usuarioRegex = Pattern.compile("^[a-zA-Z\\d]{6,8}$");
    private static final Pattern curpRegex = Pattern.compile("^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\" +
            "d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|" +
            "S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$");
    private static final Pattern rfcRegex = Pattern.compile("[a-zA-Z]{4}([0-9]{2})(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])");
    private static final Pattern polizaRegex = Pattern.compile("[a-zA-Z\\d]{11,13}");
    private static final Pattern folioRegex = Pattern.compile("[\\d]{11}");
    private static final Pattern nssRegex = Pattern.compile("^(\\d{2})(\\d{2})(\\d{2})\\d{5}$");
    private static final Pattern clabeRegex = Pattern.compile("[\\d]{18}");
    private static final Pattern visaRegex = Pattern.compile("^(?:4\\d([\\- ])?\\d{6}\\1\\d{5}|(?:4\\d{3}|5[1-5]\\d{2}|6011)" +
            "([\\- ])?\\d{4}\\2\\d{4}\\2\\d{4})$");
    private static final Pattern masterRegex = Pattern.compile("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])" +
            "[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35d{3})d{11})$");

    public ValidadorFormulario() {
        throw new IllegalStateException("Clase de ayuda");
    }

    /**
     * Valida que la contraseña cumpla los criterios mínimos establecidos.
     * - Debe tener entre 12 y 16 caracteres de longitud.
     * - Al menos 4 de los caracteres deben ser una letra.
     * - Al menos 2 de los caracteres deben ser un número.
     * - Al menos 2 de los caracteres deben ser un carácter especial (@,#,_,-,/,*,$,¡,¿,=,+)
     * - Debe contener letras en mayúsculas y en minúsculas.
     *
     * @param contrasena Contraseña por validar
     * @return true si la contraseña es válida.
     */
    public static boolean validarContrasena(String contrasena) {
        return contrasenaRegex.matcher(contrasena).matches();
    }

    /**
     * Valida que el usuario cumpla con los siguientes criterio.
     * - Debe tener entre 6 y 8 caracteres de longitud.
     * - Solo números y letras permitidos
     *
     * @param usuario Número de empleado por validar
     * @return true si el formato de número de empelado es válido.
     */
    public static Boolean validarUsuario(String usuario) {
        return usuarioRegex.matcher(usuario).matches();
    }

    /**
     * Valida que la CURP cumpla con las validaciones para la longitud y reglas especiales
     * - Debe tener 18 caracteres de longitud.
     * - Solo números y letras permitidos
     *
     * @param curp Cadena de curp por validar
     * @return true si el formato de curp es válido.
     */
    public static Boolean validarCurp(String curp) {
        return curpRegex.matcher(curp).matches();
    }

    /**
     * Valida que la RFC cumpla con las validaciones para la longitud y reglas especiales
     * - Los primeros 3 (persona moral) o 4 (persona física) caracteres
     * - Fecha válida
     *
     * @param rfc Cadena de rfc por validar
     * @return true si el formato de rfc es válido.
     */
    public static Boolean validarRFC(String rfc) {
        return rfcRegex.matcher(rfc).matches();
    }

    /**
     * Valida que la Poliza cumpla con las validaciones para la longitud y reglas especiales
     *
     * @param poliza Cadena de poliza por validar
     * @return true si el formato de poliza es válido.
     */
    public static boolean validarPoliza(String poliza) {
        return polizaRegex.matcher(poliza).matches();
    }

    /**
     * Valida que el Folio cumpla con las validaciones para la longitud y reglas especiales
     *
     * @param folio Cadena de folio por validar
     * @return true si el formato de folio es válido.
     */
    public static boolean validarFolio(String folio) {
        return folioRegex.matcher(folio).matches();
    }

    /**
     * Valida que el NSS cumpla con las validaciones para la longitud y reglas especiales
     *
     * @param nss Cadena de poliza por validar
     * @return true si el formato de poliza es válido.
     */
    public static boolean validarNSS(String nss) {
        return nssRegex.matcher(nss).matches();
    }

    /**
     * Valida que el correo electrónico cumpla con las validaciones y reglas especiales
     *
     * @param correo Cadena de correo electrónico por validar
     * @return true si el formato de correo electrónico es válido.
     */
    public static boolean validarCorreo(String correo) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(correo).matches();
    }

    /**
     * Valida que el número telefónico cumpla con las validaciones y reglas especiales
     *
     * @param telefono Cadena de correo electrónico por validar
     * @return true si el formato de correo electrónico es válido.
     */
    public static boolean validarTelefono(String telefono) {
        if (telefono.length() == Constantes.TamanoCampos.TELEFONO) {
            return validacionTelefono(telefono);
        } else {
            return false;
        }
    }

    private static boolean validacionTelefono(String telefono) {
        int validacionConsecutivos = 0;
        int validacionRepetidos = 0;
        char[] cadena = telefono.toCharArray();

        for (int x = 0; x < cadena.length; x++) {
            if (x != 0 && cadena[x - 1] == cadena[x]) {
                validacionRepetidos += 1;
            }

            if (x + 1 != cadena.length && cadena[x + 1] == cadena[x] + 1) {
                validacionConsecutivos += 1;
            }
        }

        if (validacionRepetidos == MAX_NUM_REPETIDOS || validacionConsecutivos >= MAX_NUM_CONSECUTIVOS) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Valida tarjetas de tipo Visa, Mastercard, Discover
     *
     * @param numTarjeta Cadena de número de tarjeta por validar
     * @return true si el formato de número tarjeta es válido.
     */
    public static boolean validarNumeroTarjeta(String numTarjeta) {
        return visaRegex.matcher(numTarjeta).matches() || masterRegex.matcher(numTarjeta).matches();
    }

    /**
     * Valida que la CLABE cumpla con las validaciones y reglas especiales
     *
     * @param clabe Cadena de clabe por validar
     * @return true si el formato de clabe es válido.
     */
    public static boolean validarCLABE(String clabe) {
        return clabeRegex.matcher(clabe).matches();
    }
}