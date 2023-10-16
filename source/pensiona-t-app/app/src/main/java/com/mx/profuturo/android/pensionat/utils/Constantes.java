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
 * <h1>Constantes</h1>
 *
 * @author Everis
 * @version 1.0
 * @since 2019-02-01
 */
public class Constantes {
    public enum TipoTelefono {
        CASA,
        MOVIL,
        RECADOS
    }

    public enum TipoServicio {
        LOGIN,
        BUSQUEDA,
        DETALLE,
        CATALOGO,
        TRAMITES,
        FOLIO,
        REGISTRO,
        ACUSE,
        CARGAR,
        SMS,
        CORREO
    }

    public enum TipoBusqueda {
        NSS,
        CURP,
        FOLIO,
        POLIZA
    }

    public class Url {
        public static final String OAUTH = "oauth2/token";
        public static final String LOGIN = "1/empleados/autenticacionUsuario";
        public static final String BUSQUEDA = "pensiones/1/pensionado/grupoFamiliar";
        public static final String DETALLE = "pensiones/1/pensionado/detalleCliente";
        public static final String CATALOGO = "pensiones/1/catalogos";
        public static final String TRAMITE = "pensiones/1/tramites/listaDocumentos";
        public static final String FOLIO = "pensiones/1/tramites/folio-mit";
        public static final String REGISTRO = "pensiones/1/tramites/tramite";
        public static final String ACUSE = "pensiones/1/tramites/acuseSolicitud";
        public static final String CARGAR = "pensiones/1/tramites/documentos";
        public static final String SMS = "pensiones/1/tramites/notificacion/sms";
        public static final String CORREO = "pensiones/1/tramites/notificacion/email";
    }

    public class Configuraciones {
        public static final int MILISEGUNDOS_REINTENTO = 50000;
        public static final int REINTENTOS = 1;
    }

    public class TamanoCampos {
        public static final int CURP = 18;
        public static final int POLIZA = 13;
        public static final int NSS = 11;
        public static final int TELEFONO = 10;
        public static final int CP = 5;
        public static final int NUMERODETARJETA = 16;
        public static final int CLABE = 18;
    }

    public class Nombres {
        public static final String ARCHIVO_TRAMITE = "SOLICITUD IDENTIFICACION PENSIONADO.pdf";
        public static final String ARCHIVO_ACUSE = "ACUSE DE TRAMITE PENSIONES.pdf";
        public static final String FIRMA_ACUSE_AZUL = "firmaAcuseAzul.jpeg";
        public static final String FIRMA_ACUSE = "firmaAcuse.jpeg";
        public static final String NOMBRE_FIRMA_FORM_AZUL = "firmaFormularioAzul.jpeg";
        public static final String NOMBRE_FIRMA_FORM = "firmaFormulario.jpeg";
        public static final String DESC_PARENTESCO = "PRINCIPAL";
        public static final String VALOR_SI = "X";
        public static final String DIR_ARCHIVOS = "TramitesDigitales";
    }

    public class Catalogos {
        public static final int BANCO = 1000;
        public static final int NACIONALIDAD = 2000;
        public static final int PARENTESCO = 3000;
        public static final int PAIS = 6000;
        public static final int PROFESION = 8000;
        public static final int EXCEPCION = 10000;
    }

    public class Packages {
        public static final String PACKAGE_MOTOR_CODIGOS_POSTALES = "com.mx.profuturo.android.codigospostales";
        public static final String PACKAGE_MOTOR_IMAGENES = "com.mx.profuturo.android.motor.imagenes";

        public static final String PACKAGE_MOTOR_MAU = "com.mx.profuturo.grupo.autenticacionlib";
    }

    public class HTTPError {
        public static final String INVALID_TOKEN = "401-01";
    }
}