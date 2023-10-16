package com.mx.profuturo.grupo.launchermau.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;
import com.mx.profuturo.grupo.launchermau.data.model.response_launcher.ResponseLauncher;

public class ConfLauncher {

    private static final String TAG_CONF_LAUNCHER = "CONF_LAUNCHER_MAU";

    public static final String TAG_STATUS_ENROLMENT = "StatusEnrolment";
    public static final String TAG_UUID_SESSION = "UUIDSession";

    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(TAG_CONF_LAUNCHER, Context.MODE_PRIVATE);
    }

    public static void cleanSharedPref(Context context) {
        getSharedPreferences(context).edit().clear().apply();
    }

    @Nullable
    public static String getStringByTag(String tag, Context context) {
        return getSharedPreferences(context).getString(tag, null);
    }

    public static void setStringByTag(Context context, String tag, String value) {
        getSharedPreferences(context).edit().putString(tag, value).apply();
    }

    public static ResponseLauncher getResponseLauncher(Context context) {
        return  new Gson().fromJson(getSharedPreferences(context).getString(TAG_STATUS_ENROLMENT, ""),ResponseLauncher.class);
    }

    public static void setResponseLauncher(Context context, ResponseLauncher responseLauncher) {
        getSharedPreferences(context).edit().putString(TAG_STATUS_ENROLMENT, new Gson().toJson(responseLauncher)).apply();
    }

}
