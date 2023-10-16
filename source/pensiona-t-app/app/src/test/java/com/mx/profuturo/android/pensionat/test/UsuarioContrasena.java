package com.mx.profuturo.android.pensionat.test;

/**
 * Creado por Jose Antonio Acevedo Trejo en 02/05/2019.
 * Everis
 * antonioacevedot@gmail.com
 * version 1.0
 */
public class UsuarioContrasena implements LoginServicioInterfaz {
    @Override
    public String envioUsuario(Usuario usuario) {
        String usuarioBody;
        try {
            usuarioBody = usuario.getUsuario();

        } catch (NullPointerException e) {
            usuarioBody = "fallo";

        }
        return usuarioBody;
    }

}
