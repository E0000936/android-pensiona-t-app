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

/**
 * <h1>ValidarCLABE</h1>
 * La ABM diseño el procedimiento para generar el dígito verificador
 * de las cuentas tipo CLABE. En esta clase ser emplea el procedimiento
 * para obtener el digito verificador de una cuenta.
 *
 * @author Everis
 * @version 1.0
 * @since 12/03/2019
 */
public class ValidarCLABE {

    private static final int FACTOR_UNO = 1;
    private static final int FACTOR_TRES = 3;
    private static final int FACTOR_SIETE = 7;
    private static final int TAMANO_CUENTA = 17;
    private static final int MOD_10 = 10;

    public ValidarCLABE() {
        throw new IllegalStateException("Clase de ayuda");
    }

    /**
     * Obtiene el dígito verificador de la cuenta CLABE.
     * - Debe tener una longitud de 17.
     * 1. Multiplicar cada dígito del número de cuenta por el factor de ponderación
     * respectivo
     * 2. Tomar módulo 10 de cada resultado obtenido en el paso 1
     * 3. Sumar los resultados de cada una de las operaciones módulo realizadas en
     * el paso 2
     * 4. Tomar el módulo 10 de la suma calculada del paso 3
     * 5. Tomar el valor obtenido en el paso 4 y restarlo de 10.
     * 6. El Dígito verificador es el resultado de obtner el módulo 10 del número B
     * calculado en paso 5
     * 7. La CLABE se obtiene al agregar al número de cuenta original el dígito
     * verificador calculado.
     *
     * @param cuenta cuenta clabe a validar
     * @return int digíto verificador.
     */

    public static int validarCuentaBancaria(int cuenta[]) {
        int suma = 0;
        int resultado;
        int digitoVerificador = -1;
        int[] factoresPonderacion = new int[]{
                FACTOR_TRES, FACTOR_SIETE, FACTOR_UNO,
                FACTOR_TRES, FACTOR_SIETE, FACTOR_UNO,
                FACTOR_TRES, FACTOR_SIETE, FACTOR_UNO,
                FACTOR_TRES, FACTOR_SIETE, FACTOR_UNO,
                FACTOR_TRES, FACTOR_SIETE, FACTOR_UNO,
                FACTOR_TRES, FACTOR_SIETE};
        if (cuenta.length == TAMANO_CUENTA) {
            //paso 1
            for (int x = 0; x < factoresPonderacion.length; x++) {
                cuenta[x] = cuenta[x] * factoresPonderacion[x];
            }

            //paso 2
            for (int valor : cuenta) {
                int modulo10 = valor % MOD_10;
                //paso 3
                suma = suma + modulo10;
            }

            //paso 4
            suma = suma % MOD_10;
            //paso 5
            resultado = MOD_10 - suma;
            //paso 6
            digitoVerificador = resultado % MOD_10;
        }

        return digitoVerificador;
    }
}
