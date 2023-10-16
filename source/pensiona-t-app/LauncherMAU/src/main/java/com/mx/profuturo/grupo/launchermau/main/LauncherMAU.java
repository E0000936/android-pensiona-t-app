package com.mx.profuturo.grupo.launchermau.main;

import static com.mx.profuturo.grupo.launchermau.core.tags.TagResponse.MAU_INTERNAL_ERROR;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;
import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;
import com.mx.profuturo.grupo.launchermau.data.local.ConfLauncher;
import com.mx.profuturo.grupo.launchermau.main.builder.MauBuilder;
import com.mx.profuturo.grupo.launchermau.main.callback.OnResponseLauncher;
import com.mx.profuturo.grupo.launchermau.main.consumer.logic.ProcessLaunch;
import com.mx.profuturo.grupo.launchermau.main.consumer.logic.UniqueIdFatherConsumer;
import com.mx.profuturo.grupo.launchermau.utils.LiveDataUtil;
import com.mx.profuturo.grupo.launchermau.utils.ValidateField;

import org.jetbrains.annotations.Contract;

public class LauncherMAU {
    private Context context = null;
    private OnResponseLauncher onResponseLauncher = null;
    private MauBuilder mauBuilder;

    private final ProcessLaunch processLaunch = new ProcessLaunch();

    private LauncherMAU(@NonNull Context context, @NonNull OnResponseLauncher onResponseLauncher) {
        this.onResponseLauncher = onResponseLauncher;
        this.context = context;
    }


    @NonNull
    @Contract("_,_ -> new")
    public static LauncherMAU getInstance(@NonNull Context context, @NonNull OnResponseLauncher onResponseLauncher) {
        return new LauncherMAU(context, onResponseLauncher);
    }

    public void setMauBuilder(@NonNull MauBuilder mauBuilder) {
        this.mauBuilder = mauBuilder;
    }

    public static void createUuidSession(@NonNull Context context) {
        String uuid = ConfLauncher.getStringByTag(ConfLauncher.TAG_UUID_SESSION, context);
        if (uuid == null) {
            UniqueIdFatherConsumer uniqueIdFatherConsumer = new UniqueIdFatherConsumer();
            if (!uniqueIdFatherConsumer.uuid_session.hasActiveObservers()) {
                LiveDataUtil.observeOnce(uniqueIdFatherConsumer.uuid_session, chain -> {
                    ConfLauncher.setStringByTag(context, ConfLauncher.TAG_UUID_SESSION, chain);
                });
                uniqueIdFatherConsumer.consumeUniqueIdFather();
            }
        }
    }

    /**
     * This method only check status session, it's the step one for launch MAU
     */
    public void isSessionActive(@NonNull String CURP) {
        if (!ValidateField.isCURPValid(CURP)) {
            onResponseLauncher.onError(TagResponse.TAG_INVALIDATE_FIELD, -1);
            return;
        }
        processLaunch.checkStatusSession(CURP, onResponseLauncher);
    }

    /**
     * This method is called when you check uuid session and status session before to launch MAU
     */
    public void startMAU() {
        if (!ValidateField.isPackageInstalled(TagsBuilder.TAG_PACKAGE_NAME, context.getPackageManager())) {
            onResponseLauncher.onError(TagResponse.NO_MAU_INSTALLED, MAU_INTERNAL_ERROR);
            return;
        }
        if (processLaunch.validateFields(mauBuilder))
            processLaunch.buildIntentByRule(mauBuilder, onResponseLauncher, context);
        else
            onResponseLauncher.onError(processLaunch.getLog_msg(), MAU_INTERNAL_ERROR);
    }

    public static void clearProcess(Context context) {
        ConfLauncher.cleanSharedPref(context);
    }

}