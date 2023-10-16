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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h1>Checksum</h1>
 * Clase de utilidad para obtener CheckSum o Suma de Verificación
 * Cuyo propósito principal es verificar la integridad del documento a subir
 * en el servicio "CargarDocumentosServicio".
 *
 * @author Jose Antonio Acevedo Trejo
 * @version 1.0
 * @since 2019-08-01
 */
public class Checksum {
    public Checksum() {
        throw new IllegalStateException("Clase de ayuda");
    }

    /**
     * A partir de la ruta de un archivo alojado en la tableta
     * (imagen capturada desde el motor de imagenes o documentos pdf)
     * se aplica un algoritmo criptográfico de 128 bits ampliamente difundido MD5,
     * para realizar la comprobación de la integridad de archivos.
     *
     * @param nombre Ruta de un archivo alojado en la tableta
     * @return Retorna una cadena vacía si el archivo no es encontrado,
     * en caso satisfactorio retorna la cadena correspondiente al
     * MD5 resultante.
     */
    public static String obtenerMD5(String nombre) {
        try {
            File archivo = new File(nombre);
            try (FileInputStream imageInFile = new FileInputStream(archivo)) {
                return obtenerByteArray(imageInFile);
            } catch (IOException e) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }

    public static String obtenerByteArray(FileInputStream imageInFile) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            //Leer los datos del archivo y actualizarlos en el mensaje
            while ((bytesCount = imageInFile.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            //Obtener hash's bytes
            return convertirBytesEnCadena(digest.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * A partir de un arreglo de bytes se retorna una cadena
     * convertida a mayúsculas, para la obtención del MD5.
     *
     * @param bytes Arreglo de bytes
     * @return Retorna una cadena convertida a mayusculas.
     */
    public static String convertirBytesEnCadena(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
    }
}
