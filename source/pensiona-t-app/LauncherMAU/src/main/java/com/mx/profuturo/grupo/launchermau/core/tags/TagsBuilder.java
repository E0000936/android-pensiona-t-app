package com.mx.profuturo.grupo.launchermau.core.tags;

public class TagsBuilder {
    //Orgin aplication
    public static final String ORIGIN_IDENTIFICAT = "37";
    public static final String ORIGIN_PENSIONAT = "33";
    //General information about the client
    public static final String ARG_CURP = "curp";
    public static final String ARG_NOMBRE = "nombre";
    public static final String ARG_APELLIDO_PATERNO = "apellidopaterno";
    public static final String ARG_APELLIDO_MATERNO = "apellidomaterno";
    public static final String ARG_EMAIL_CLIENT = "cliente_correo";
    public static final String ARG_PHONE_CLIENT = "cliente_celular";
    public static final String ARG_NSS = "NSS";

    //get from identificaT
    public static final String ARG_ID_BUC = "idbuc";
    public static final String ARG_ID_CONTRATO = "idContrato";
    public static final String ARG_NUMBER_ACCOUNT = "numeroCuenta";
    public static final String ARG_TRAMITE = "tramite";
    public static final String ARG_TRAMITE_ORIGEN_NOMBRE = "TramiteOrigenNombre";
    public static final String ARG_APLICATIVO_ORIGEN = "aplicativoorigen";
    public static final String ARG_ID_UNICO_PADRE = "IdUnicoPadre";

    //for business rule
    public static final String ARG_ORIGIN_BUSINESS_LINE = "lineanegocioorigen";
    public static final String ARG_BUSINESS_LINE = "businessLine";
    public static final String ARG_CVE_ORIGEN = "cveOrigen";
    public static final String ARG_CVE_ENT_SOLICITANTE = "cveEntSolicitante";
    public static final String ARG_CVE_OPERACION = "cveOperacion";
    public static final String ARG_TIPO_USUARIO = "tipoUsuario";
    public static final String ARG_PROCESO = "proceso";
    public static final String ARG_SUB_PROCESO = "subproceso";
    public static final String ARG_RULE_CONF = "ruleConfiguration";
    //Rule time out session
    public static final String ARG_SESSION_TIMEOUT = "sessionTimeout";

    //Asesor informaciones
    public static final String ARG_CORREO_ASESOR = "asesor_correo";
    public static final String ARG_ASESOR_ID = "asesor_id";

    /**
     * Package name engine
     */
    public static final String TAG_PACKAGE_NAME = "com.mx.profuturo.grupo.autenticacionlib";
    public static final String TAG_CLASS_MAU = "com.mx.profuturo.grupo.autenticacionlib.presentation.Contenedor.MainContenedor";
    //Name of rules for config MAU
    public static final String RULE_FULL_CONFIG = "rule full configuration";
    public static final String RULE_ENROLLMENT = "rule enrollment";
    public static final String RULE_AUTH = "rule authentication";

    //FLAGS to know if a session is active
    public static final String TAG_SESSION_ACTIVE = "2";
    public static final String TAG_SESSION_INACTIVE = "1";

}
