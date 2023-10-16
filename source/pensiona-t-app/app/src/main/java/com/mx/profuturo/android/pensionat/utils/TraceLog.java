package com.mx.profuturo.android.pensionat.utils;

import com.mx.profuturo.android.pensionat.BuildConfig;
import com.mx.profuturo.android.pensionat.data.local.db.realm.RealmConfiguracion;

import java.util.Calendar;

public class TraceLog {
    public static volatile TraceLog instance = null;
    private final String _NameApp_ = "nameapp";
    private final String _IdProcess_ = "id_folio";
    private final String _CurpProcess_ = "curp_tramite";
    private final String _CuspEmployee_ = "cusp_empleado";
    private final String _DateCreated_ = "fecha";
    private final String _TimeCreated_ = "hora_incidencia";
    private final String _ErrorDescription_ = "descripcion_error";

    private TraceLog() {
        setupDefault();
    }

    public static TraceLog getInstance() {
        if (instance == null) {
            synchronized (TraceLog.class) {
                if (instance == null) instance = new TraceLog();
            }
        }
        return instance;
    }

    private void SetNameApp() {
        RealmConfiguracion.getCrashalytics().setCustomKey(_NameApp_, BuildConfig.APP_NAME);
    }

    private void setupDefault() {
        SetNameApp();
        Calendar fecha = Calendar.getInstance();
        RealmConfiguracion.getCrashalytics().setCustomKey(_DateCreated_, Utilidades.dateFormatter(fecha.getTime()));
        RealmConfiguracion.getCrashalytics().setCustomKey(_TimeCreated_, Utilidades.timeFormatter(fecha.getTime()));
    }

    public void setIdProcess(String idProcess) {
        RealmConfiguracion.getCrashalytics().setCustomKey(_IdProcess_, idProcess);
    }

    public void setCurpProcess(String curpProcess) {
        RealmConfiguracion.getCrashalytics().setCustomKey(_CurpProcess_, curpProcess);
    }

    public void setCuspEmployee(String cuspEmployee) {
        RealmConfiguracion.getCrashalytics().setCustomKey(_CuspEmployee_, cuspEmployee);
    }

    public void setErrorDescription(String errorDescription) {
        RealmConfiguracion.getCrashalytics().setCustomKey(_ErrorDescription_, errorDescription);
    }
}
