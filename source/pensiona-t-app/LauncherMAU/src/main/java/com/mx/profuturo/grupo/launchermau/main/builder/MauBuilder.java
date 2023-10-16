package com.mx.profuturo.grupo.launchermau.main.builder;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.rules.BusinessLine;

import org.jetbrains.annotations.Contract;

public class MauBuilder {

    private String CURP;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email_client;
    private String phone_client;
    private String nss_client;

    private String id_buc;
    private String id_contrato;
    private String number_account;
    private String procedure;
    private String procedure_origin;
    private String origin_application;

    private String origin_business_line;
    private String business_line;
    private String cve_origin;
    private String cve_entity_applicant;
    private String cve_operation;
    private String type_user;
    private String process;
    private String subprocess;

    private String email_adviser;
    private String id_adviser;
    private long timeOutSession;


    private MauBuilder(
            @NonNull String CURP,
            @NonNull String nombre,
            @NonNull String apellidoPaterno,
            @NonNull String apellidoMaterno,
            @NonNull String nss_client,
            @NonNull String id_buc,
            @NonNull String id_contrato,
            @NonNull String number_account,
            @NonNull String procedure,
            @NonNull String procedure_origin,
            @NonNull String origin_application,
            @NonNull String business_line,
            @NonNull String type_user,
            @NonNull String process,
            @NonNull String subprocess) {
        this.CURP = CURP;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nss_client = nss_client;
        this.id_buc = id_buc;
        this.id_contrato = id_contrato;
        this.number_account = number_account;
        this.procedure = procedure;
        this.procedure_origin = procedure_origin;
        this.origin_application = origin_application;
        this.business_line = business_line;
        this.type_user = type_user;
        this.process = process;
        this.subprocess = subprocess;

        switch (business_line) {
            case BusinessLine.AFORE_LINE:
                this.cve_entity_applicant = BusinessLine.AFORE.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.AFORE.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.AFORE.getCveOperacion();
                this.cve_origin = BusinessLine.AFORE.getCveOrigen();
                break;
            case BusinessLine.ASEGURADORA_LINE:
                this.cve_entity_applicant = BusinessLine.ASEGURADORA.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.ASEGURADORA.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.ASEGURADORA.getCveOperacion();
                this.cve_origin = BusinessLine.ASEGURADORA.getCveOrigen();
                break;
            case BusinessLine.SOFOM_LINE:
                this.cve_entity_applicant = BusinessLine.SOFOM.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.SOFOM.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.SOFOM.getCveOperacion();
                this.cve_origin = BusinessLine.SOFOM.getCveOrigen();
                break;
        }
    }

    private MauBuilder(
            @NonNull String CURP,
            @NonNull String nombre,
            @NonNull String apellidoPaterno,
            @NonNull String apellidoMaterno,
            @NonNull String number_account,
            @NonNull String procedure,
            @NonNull String procedure_origin,
            @NonNull String origin_application,
            @NonNull String business_line,
            @NonNull String type_user,
            @NonNull String process,
            @NonNull String subprocess) {
        this.CURP = CURP;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.id_buc = number_account;
        this.id_contrato = number_account;
        this.number_account = number_account;
        this.procedure = procedure;
        this.procedure_origin = procedure_origin;
        this.origin_application = origin_application;
        this.business_line = business_line;
        this.type_user = type_user;
        this.process = process;
        this.subprocess = subprocess;

        switch (business_line) {
            case BusinessLine.AFORE_LINE:
                this.cve_entity_applicant = BusinessLine.AFORE.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.AFORE.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.AFORE.getCveOperacion();
                this.cve_origin = BusinessLine.AFORE.getCveOrigen();
                break;
            case BusinessLine.ASEGURADORA_LINE:
                this.cve_entity_applicant = BusinessLine.ASEGURADORA.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.ASEGURADORA.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.ASEGURADORA.getCveOperacion();
                this.cve_origin = BusinessLine.ASEGURADORA.getCveOrigen();
                break;
            case BusinessLine.SOFOM_LINE:
                this.cve_entity_applicant = BusinessLine.SOFOM.getCveEntSolicitante();
                this.origin_business_line = BusinessLine.SOFOM.getBusinessLineOrgin();
                this.cve_operation = BusinessLine.SOFOM.getCveOperacion();
                this.cve_origin = BusinessLine.SOFOM.getCveOrigen();
                break;
        }
    }

    @NonNull
    @Contract("_, _, _, _, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static MauBuilder getInstance(
            @NonNull String CURP,
            @NonNull String nombre,
            @NonNull String apellidoPaterno,
            @NonNull String apellidoMaterno,
            @NonNull String nss_client,
            @NonNull String id_buc,
            @NonNull String id_contrato,
            @NonNull String number_account,
            @NonNull String procedure,
            @NonNull String procedure_origin,
            @NonNull String origin_application,
            @NonNull String business_line,
            @NonNull String type_user,
            @NonNull String process,
            @NonNull String subprocess) {
        return new MauBuilder(CURP, nombre, apellidoPaterno, apellidoMaterno, nss_client, id_buc, id_contrato, number_account, procedure, procedure_origin, origin_application, business_line, type_user, process, subprocess);
    }

    @NonNull
    @Contract("_, _, _, _, _, _, _, _, _, _, _, _ -> new")
    public static MauBuilder getInstance(
            @NonNull String CURP,
            @NonNull String nombre,
            @NonNull String apellidoPaterno,
            @NonNull String apellidoMaterno,
            @NonNull String poliza,
            @NonNull String procedure,
            @NonNull String procedure_origin,
            @NonNull String origin_application,
            @NonNull String business_line,
            @NonNull String type_user,
            @NonNull String process,
            @NonNull String subprocess) {
        return new MauBuilder(CURP, nombre, apellidoPaterno, apellidoMaterno, poliza, procedure, procedure_origin, origin_application, business_line, type_user, process, subprocess);
    }

    public MauBuilder setEmail_client(String email_client) {
        this.email_client = email_client;
        return this;
    }

    public MauBuilder setPhone_client(String phone_client) {
        this.phone_client = phone_client;
        return this;
    }

    public MauBuilder setEmail_adviser(String email_adviser) {
        this.email_adviser = email_adviser;
        return this;
    }

    public MauBuilder setId_adviser(String id_adviser) {
        this.id_adviser = id_adviser;
        return this;
    }

    public MauBuilder setNSS(String nss) {
        this.nss_client = nss;
        return this;
    }

    public void setTimeOutSession(long timeOutSession) {
        this.timeOutSession = timeOutSession;
    }

    public String getCURP() {
        return CURP;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getEmail_client() {
        return email_client;
    }

    public String getPhone_client() {
        return phone_client;
    }

    public String getNss_client() {
        return nss_client;
    }

    public String getId_buc() {
        return id_buc;
    }

    public String getId_contrato() {
        return id_contrato;
    }

    public String getNumber_account() {
        return number_account;
    }

    public String getProcedure() {
        return procedure;
    }

    public String getProcedure_origin() {
        return procedure_origin;
    }

    public String getOrigin_application() {
        return origin_application;
    }

    public String getOrigin_business_line() {
        return origin_business_line;
    }

    public String getBusiness_line() {
        return business_line;
    }

    public String getCve_origin() {
        return cve_origin;
    }

    public String getCve_entity_applicant() {
        return cve_entity_applicant;
    }

    public String getCve_operation() {
        return cve_operation;
    }

    public String getType_user() {
        return type_user;
    }

    public String getProcess() {
        return process;
    }

    public String getSubprocess() {
        return subprocess;
    }

    public String getEmail_adviser() {
        return email_adviser;
    }

    public String getId_adviser() {
        return id_adviser;
    }

    public long getTimeOutSession() {
        return timeOutSession;
    }

}
