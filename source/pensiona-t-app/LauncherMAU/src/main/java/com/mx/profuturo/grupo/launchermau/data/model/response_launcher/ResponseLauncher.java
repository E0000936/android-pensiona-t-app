package com.mx.profuturo.grupo.launchermau.data.model.response_launcher;

import com.mx.profuturo.grupo.launchermau.core.tags.TagResponse;

import java.util.Objects;

public class ResponseLauncher {

    private String curp;
    private String status_engine;
    private String statusEmail;
    private String statusPhone;
    private String statusDactilar;
    private String statusFacial;

    public ResponseLauncher(String curp, String status_engine, String statusEmail, String statusPhone, String statusDactilar, String statusFacial) {
        this.curp = curp;
        this.status_engine = status_engine;
        this.statusEmail = statusEmail;
        this.statusPhone = statusPhone;
        this.statusDactilar = statusDactilar;
        this.statusFacial = statusFacial;
    }

    public String getStatus_engine() {
        return status_engine;
    }

    public String getCurp() {
        return curp;
    }
    public boolean haveOneAuthAvailable() {
        return !(Objects.equals(statusEmail, TagResponse.CODE_STATUS_NOT_CHECK) &&
                Objects.equals(statusPhone, TagResponse.CODE_STATUS_NOT_CHECK) &&
                Objects.equals(statusDactilar, TagResponse.CODE_STATUS_NOT_CHECK) &&
                Objects.equals(statusFacial, TagResponse.CODE_STATUS_NOT_CHECK));
    }
}
