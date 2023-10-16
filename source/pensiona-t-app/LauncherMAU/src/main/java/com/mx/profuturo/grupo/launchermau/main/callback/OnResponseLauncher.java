package com.mx.profuturo.grupo.launchermau.main.callback;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface OnResponseLauncher {
    void onError(String log, int code);

    void onSuccess(@Nullable Intent intent);

    void isSessionActive(boolean isSessionActive);
}