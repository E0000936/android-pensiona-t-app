package com.mx.profuturo.android.pensionat.test;


/**
 * Creado por Jose Antonio Acevedo Trejo en 06/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
public class ClabeBanco implements envioClabeInterfaz {
    @Override
    public String envioClabe(ClabeBancoInterfaz clabeBancoInterfaz) {
        String clabeBody;
        try {
            clabeBody = clabeBancoInterfaz.getClabe();

        } catch (NullPointerException e) {
            clabeBody = "fallo";

        }
        return clabeBody;
    }
}
