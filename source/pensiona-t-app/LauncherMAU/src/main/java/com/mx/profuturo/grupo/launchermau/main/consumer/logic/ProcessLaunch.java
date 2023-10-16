package com.mx.profuturo.grupo.launchermau.main.consumer.logic;

import static com.mx.profuturo.grupo.launchermau.core.tags.TagResponse.TAG_INVALIDATE_FIELD;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_APELLIDO_MATERNO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_APELLIDO_PATERNO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_APLICATIVO_ORIGEN;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_ASESOR_ID;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_BUSINESS_LINE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_CORREO_ASESOR;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_CURP;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_CVE_ENT_SOLICITANTE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_CVE_OPERACION;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_CVE_ORIGEN;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_EMAIL_CLIENT;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_ID_BUC;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_ID_CONTRATO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_ID_UNICO_PADRE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_NOMBRE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_NSS;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_NUMBER_ACCOUNT;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_ORIGIN_BUSINESS_LINE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_PHONE_CLIENT;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_PROCESO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_RULE_CONF;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_SUB_PROCESO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_TIPO_USUARIO;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_TRAMITE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.ARG_TRAMITE_ORIGEN_NOMBRE;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.TAG_CLASS_MAU;
import static com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder.TAG_SESSION_ACTIVE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.rules.Rules;
import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;
import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;
import com.mx.profuturo.grupo.launchermau.data.local.ConfLauncher;
import com.mx.profuturo.grupo.launchermau.main.builder.MauBuilder;
import com.mx.profuturo.grupo.launchermau.main.callback.OnResponseLauncher;
import com.mx.profuturo.grupo.launchermau.utils.LiveDataUtil;
import com.mx.profuturo.grupo.launchermau.utils.ValidateField;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public class ProcessLaunch {

    private String log_msg;
    private final CheckProcedureConsumer checkProcedureConsumer = new CheckProcedureConsumer();
    private final UniqueIdFatherConsumer uniqueIdFatherConsumer = new UniqueIdFatherConsumer();

    public ProcessLaunch() {}

    public String getLog_msg() {
        return log_msg;
    }

    public boolean validateFields(@NonNull MauBuilder mauBuilder) {
        try {
            if (!ValidateField.isCURPValid(mauBuilder.getCURP())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_CURP);
            if (!ValidateField.isAlfaNumber(mauBuilder.getNombre())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_NOMBRE);
            if (!ValidateField.isAlfaNumber(mauBuilder.getApellidoPaterno())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_APELLIDO_PATERNO);
            if (Objects.isNull(mauBuilder.getId_buc())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_ID_BUC);
            if (Objects.isNull(mauBuilder.getId_contrato())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_ID_CONTRATO);
            if (Objects.isNull(mauBuilder.getNumber_account())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_NUMBER_ACCOUNT);
            if (Objects.isNull(mauBuilder.getProcedure())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_TRAMITE);
            if (Objects.isNull(mauBuilder.getProcedure_origin())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_TRAMITE_ORIGEN_NOMBRE);
            if (!ValidateField.isNumeric(mauBuilder.getOrigin_application())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_APLICATIVO_ORIGEN);
            if (!ValidateField.isAlfaNumber(mauBuilder.getOrigin_business_line())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_ORIGIN_BUSINESS_LINE);
            if (!ValidateField.isAlfaNumber(mauBuilder.getBusiness_line())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_BUSINESS_LINE);
            if (!ValidateField.isAlfaNumber(mauBuilder.getCve_origin())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_CVE_ORIGEN);
            if (!ValidateField.isAlfaNumber(mauBuilder.getCve_operation())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_CVE_OPERACION);
            if (!ValidateField.isAlfaNumber(mauBuilder.getCve_entity_applicant())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_CVE_ENT_SOLICITANTE);
            if (!ValidateField.isAlfaNumber(mauBuilder.getType_user())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_TIPO_USUARIO);
            if (!ValidateField.isNumeric(mauBuilder.getProcess())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_PROCESO);
            if (!ValidateField.isNumeric(mauBuilder.getSubprocess())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_SUB_PROCESO);
            if (!Objects.isNull(mauBuilder.getPhone_client()) && !mauBuilder.getPhone_client().isEmpty())
                if (!ValidateField.isNumeric(mauBuilder.getPhone_client())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_PHONE_CLIENT);
            if (!Objects.isNull(mauBuilder.getEmail_client()) && !mauBuilder.getEmail_client().isEmpty())
                if (!ValidateField.isValidateEmail(mauBuilder.getEmail_client())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_EMAIL_CLIENT);
            if (!Objects.isNull(mauBuilder.getNss_client()) && !mauBuilder.getNss_client().isEmpty())
                if (!ValidateField.isNumeric(mauBuilder.getNss_client())) throw new RuntimeException(TAG_INVALIDATE_FIELD + ARG_NSS);

        } catch (Exception e) {
            Log.e("ERROROPENMAU", e.toString());
            log_msg = e.toString();
            return false;
        }
        return true;
    }

    public void buildIntentByRule(@NonNull MauBuilder mauBuilder, @NonNull OnResponseLauncher onResponseLauncher, @NonNull Context context) {
        String uuid = ConfLauncher.getStringByTag(ConfLauncher.TAG_UUID_SESSION, context);
        if (uuid == null) {
            uniqueIdFatherConsumer.consumeUniqueIdFather();
            if (!uniqueIdFatherConsumer.onError.hasActiveObservers())
                LiveDataUtil.observeOnce(uniqueIdFatherConsumer.onError, uniqueIdFatherConsumer.onError.getValue(), data -> {
                    onResponseLauncher.onError(TagResponse.SERVICE_EXCEPTION + data, data);
                });
            if (!uniqueIdFatherConsumer.uuid_session.hasActiveObservers()) {
                LiveDataUtil.observeOnce(uniqueIdFatherConsumer.uuid_session, uniqueIdFatherConsumer.uuid_session.getValue(),chain -> {
                    ConfLauncher.setStringByTag(context, ConfLauncher.TAG_UUID_SESSION, chain);
                    evaluateRules(mauBuilder, onResponseLauncher, chain);
                });
                uniqueIdFatherConsumer.consumeUniqueIdFather();
            }
        } else {
            evaluateRules(mauBuilder, onResponseLauncher, uuid);
        }
    }

    private void evaluateRules(@NonNull MauBuilder mauBuilder, @NonNull OnResponseLauncher onResponseLauncher, @NonNull String uuid) {
        Rules rules = Rules.getRuleConfig(mauBuilder.getOrigin_application(), mauBuilder.getProcess(), mauBuilder.getSubprocess());
        switch (rules.getConfig()) {
            case TagsBuilder.RULE_AUTH:
            case TagsBuilder.RULE_FULL_CONFIG:
            case TagsBuilder.RULE_ENROLLMENT:
                onResponseLauncher.onSuccess(makeIntent(rules, mauBuilder, uuid));
                break;
            default:
                onResponseLauncher.onError(TagResponse.RULE_EXCEPTION_NOT_FOUND_CONF, TagResponse.MAU_INTERNAL_ERROR);
                break;
        }
    }
    public void checkStatusSession(@NonNull String curp, @NonNull OnResponseLauncher onResponseLauncher) {
        if (!checkProcedureConsumer.onError.hasActiveObservers()) {
            LiveDataUtil.observeOnce(checkProcedureConsumer.onError, checkProcedureConsumer.onError.getValue(), error -> {
                onResponseLauncher.onError(TagResponse.SERVICE_EXCEPTION + error, error);
            });
        }
        if (!checkProcedureConsumer.status_procedure.hasActiveObservers()) {
            LiveDataUtil.observeOnce(checkProcedureConsumer.status_procedure, checkProcedureConsumer.status_procedure.getValue(), status -> {
                boolean isSessionActive = Objects.equals(status, TAG_SESSION_ACTIVE);
                onResponseLauncher.isSessionActive(isSessionActive);
            });
        }
        checkProcedureConsumer.consumeCheckProcedure(curp);
    }
    @NonNull
    @Contract(pure = true)
    public Intent makeIntent(@NonNull Rules rule, @NonNull MauBuilder mauBuilder, @NonNull String uuid) {

        String email_client = mauBuilder.getEmail_client();
        String phone_client = mauBuilder.getPhone_client();
        String procedure_origin = mauBuilder.getProcedure_origin();

        Intent intent = new Intent();
        intent.putExtra(ARG_ID_BUC, mauBuilder.getId_buc());
        intent.setComponent(new ComponentName(TagsBuilder.TAG_PACKAGE_NAME, TAG_CLASS_MAU));

        intent.putExtra(ARG_ID_CONTRATO, mauBuilder.getId_contrato());
        intent.putExtra(ARG_NUMBER_ACCOUNT, mauBuilder.getNumber_account());
        intent.putExtra(ARG_NOMBRE, mauBuilder.getNombre());
        intent.putExtra(ARG_APELLIDO_PATERNO, mauBuilder.getApellidoPaterno());
        intent.putExtra(ARG_APELLIDO_MATERNO, mauBuilder.getApellidoMaterno());
        intent.putExtra(ARG_CURP, mauBuilder.getCURP());
        intent.putExtra(ARG_EMAIL_CLIENT, email_client == null ? " " : email_client);
        intent.putExtra(ARG_PHONE_CLIENT, phone_client == null ? " " : phone_client);
        intent.putExtra(ARG_NSS, mauBuilder.getNss_client());

        intent.putExtra(ARG_BUSINESS_LINE, mauBuilder.getBusiness_line());
        intent.putExtra(ARG_ORIGIN_BUSINESS_LINE, mauBuilder.getOrigin_business_line());
        intent.putExtra(ARG_CVE_ORIGEN, mauBuilder.getCve_origin());
        intent.putExtra(ARG_CVE_ENT_SOLICITANTE, mauBuilder.getCve_entity_applicant());
        intent.putExtra(ARG_CVE_OPERACION, mauBuilder.getCve_operation());
        intent.putExtra(ARG_TIPO_USUARIO, mauBuilder.getType_user());

        intent.putExtra(ARG_PROCESO, mauBuilder.getProcess());
        intent.putExtra(ARG_SUB_PROCESO, mauBuilder.getSubprocess());

        intent.putExtra(ARG_TRAMITE, mauBuilder.getProcedure());
        intent.putExtra(ARG_TRAMITE_ORIGEN_NOMBRE, procedure_origin == null ? "" : procedure_origin);
        intent.putExtra(ARG_APLICATIVO_ORIGEN, mauBuilder.getOrigin_application());
        intent.putExtra(ARG_ID_UNICO_PADRE, uuid);


        intent.putExtra(ARG_CORREO_ASESOR, mauBuilder.getEmail_adviser());
        intent.putExtra(ARG_ASESOR_ID, mauBuilder.getId_adviser());
        intent.putExtra(ARG_RULE_CONF, rule.getConfig());
        if (mauBuilder.getOrigin_application().equals(TagsBuilder.ORIGIN_PENSIONAT))
            intent.putExtra(TagsBuilder.ARG_SESSION_TIMEOUT, mauBuilder.getTimeOutSession());

        return intent;
    }
}
