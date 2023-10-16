package com.mx.profuturo.grupo.launchermau.core.tags;

public class TagResponse {

    public static final String MAU_RESPONSE = "mauResponse";
    public static final String NO_MAU_INSTALLED = "no mau installed";
    public static final String TAG_KEY_NOT_FOUND = "KEY NOT FOUND: ";
    public static final String TAG_INVALIDATE_FIELD = "INVALIDATE FIELD: ";
    public static final String RULE_EXCEPTION_NOT_FOUND_CONF = "RULE EXCEPTION NOT FOUND CONFIGURATION";

    public static final String ENROLLMENT_NOT_FOUND = "ENROLLMENT NOT FOUND";
    /**
     * Response launcher case Enrolamieno completo
     */
    public static final String ENROLLMENT_DONE = "ENROLLMENT DONE";
    /**
     * Response launcher case Autenticacion exitosa
     */
    public  static final String AUTH_SUCCESS = "AUTH SUCCESS";
    /**
     * Response launcher case Cierre de sesion, por timeout
     */
    public static final String CLOSE_MAU = "CLOSE MAU";
    public static final String OMIT_CURP = "OMIT CURP";
    /**
     * Response launcher case la CURP es de un menor de edad
     */
    public static final String CURP_IS_A_MINIOR = "CURP IS A MINIOR";
    /**
     * Response launcher case Error de conexion en servicios
     */
    public static final String ERROR_SERVICE = "ERROR SERVICE";
    /**
     * Response launcher case session timeout
     */
    public static final String SESSION_TIMEOUT = "SESSION TIMEOUT";
    public static final String HAVE_BIOMETRIC = "HAVE BOMETRIC";
    public static final String HAVE_NOT_BIOMETRIC = "HAVE NOT BOMETRIC";
    public static final String SERVICE_EXCEPTION = "SERVICE EXCEPTION: ";
    public static final String MAU_BUILDER_NOT_FOUND = "MAU BUILDER NOT FOUND";

    public static final int MAU_INTERNAL_ERROR = -1;

    // 138 don't have
    public static final String CODE_STATUS_NOT_CHECK = "138";
}
