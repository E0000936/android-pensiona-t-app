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

import android.graphics.Bitmap;
import android.util.Base64;

import com.mx.profuturo.android.pensionat.data.local.db.realm.RealmConfiguracion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h1>ManejoArchivosHelper</h1>
 * Clase de utilidad para obtener manejo de directorios.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-04-21
 */
public class ManejoArchivosHelper {
    public ManejoArchivosHelper() {
        throw new IllegalStateException("Clase de ayuda");
    }

    public static void limpiarArchivosDirectorio(String path) {
        if (path != null) {
            File dir = new File(path);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if (children != null) {
                    for (String archivo : children) {
                        new File(dir, archivo).delete();
                    }
                }
            }
        }
    }

    public static String obtenerBase64(String nombre) {
        String base64File = "";
        if (nombre != null) {
            File archivo = new File(nombre);
            if (archivo.exists()) {
                try (FileInputStream imageInFile = new FileInputStream(archivo)) {
                    byte[] fileData = new byte[(int) archivo.length()];
                    int length = imageInFile.read(fileData);
                    base64File = Base64.encodeToString(fileData, 0, length, Base64.NO_WRAP);
                } catch (FileNotFoundException e) {
                    return base64File;
                } catch (IOException ioe) {
                    return base64File;
                }
            }
        }

        return base64File;
    }

    public static String crearDirectorio(File directorio) {
        String pathDirectorio = "";
        try {
            if (!directorio.exists()) {
                boolean exito = directorio.mkdirs();
                if (exito) {
                    pathDirectorio = directorio.getAbsolutePath().concat("/");
                }
            } else {
                pathDirectorio = directorio.getAbsolutePath().concat("/");
            }
            return pathDirectorio;
        } catch (NullPointerException e) {
            return pathDirectorio;
        }
    }

    public static File obtenerArchivoSDCardRuta(String nombreDirectorio) {
        return new File(RealmConfiguracion.getContext().getExternalFilesDir(null), nombreDirectorio);
    }

    public static void guardarArchivoFirma(String rutaCompleta, Bitmap newBitmap) {
        if (rutaCompleta != null) {
            try {
                FileOutputStream fos = new FileOutputStream(rutaCompleta);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
