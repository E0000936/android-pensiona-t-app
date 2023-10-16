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
package com.mx.profuturo.android.pensionat.data.local.db.realm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mx.profuturo.android.pensionat.utils.Utilidades;

import io.realm.BuildConfig;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * <h1>RealmConfiguracion</h1>
 * Clase que se encarga de la inicialización de la base con Realm.
 *
 * @author Everis
 * @version 1.0
 * @since 2019-03-06
 */
public class RealmConfiguracion extends Application {

    public static Context mContext;
    public static FirebaseCrashlytics crashlytics;

    public static Context getContext() {
        return mContext;
    }

    public static FirebaseCrashlytics getCrashalytics() {
        return crashlytics;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tramitesDigitales.realm")
                .deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true)
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        /** Contexto global **/
        mContext = getApplicationContext();

        /** Crashalytics Global **/
        crashlytics = FirebaseCrashlytics.getInstance();
        Utilidades.limpiarDatosTramite(mContext);
        /** Listener para evitar captura de pantalla **/
        setupActivityListener();
    }

    /**
     * Se crea este metodo con las flags SECURE para impedir la toma de screen shots
     **/
    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                if (!BuildConfig.DEBUG){
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });
    }
}
