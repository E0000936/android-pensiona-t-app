package com.mx.profuturo.grupo.launchermau.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.Objects;

public class LiveDataUtil {
    private static final String STEP_WAITING = "w";
    private static final String STEP_DONE = "d";
    public static <T> void observeOnce(@NonNull final LiveData<T> liveData, final Observer<T> observer) {
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                liveData.removeObserver(this);
                observer.onChanged(t);
            }
        });
    }

    public static <T> void observeOnce(@NonNull final LiveData<T> liveData, T previous ,final Observer<T> observer) {
        final String[] step = {STEP_WAITING};
        liveData.observeForever(new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (previous != t || Objects.equals(step[0], STEP_DONE)) {
                    liveData.removeObserver(this);
                    observer.onChanged(t);
                }
                if (Objects.equals(step[0], STEP_WAITING)) step[0] = STEP_DONE;
            }
        });
    }
}
