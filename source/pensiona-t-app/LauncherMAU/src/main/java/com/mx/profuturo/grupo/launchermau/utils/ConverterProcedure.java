package com.mx.profuturo.grupo.launchermau.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

public enum ConverterProcedure {
    CASE_80_396("80", "396", "80", "425"),
    CASE_80_388("80","388","80","426"),
    CASE_80_("80"," ","80","427"),
    CASE_79_462("79","462","79","428"),
    CASE_79_("79","","79","429"),
    CASE_79_1("79","","79","430"),
    CASE_79_2("67","","67","431"),
    CASE_79_418("67","418","67","432"),
    CASE_71_353("71","353","71","433"),
    CASE_71_354("71","354","71","434"),
    CASE_74_340("74","340","74","435"),
    CASE_74_313("74","313","74","436"),
    CASE_88_359("80","359","80","437"),
    CASE_80_395("80","395","80","438"),
    CASE_80_379("80","379","80","439"),
    CASE_80_382("80","382","80","440"),
    CASE_80_383("80","383","80","441"),
    CASE_80_394("80","394","80","442"),
    CASE_80_461("80","461","80","443"),
    CASE_80_459("80","459","80","444"),
    CASE_80_1("80","","80","445"),
    CASE_67_("67","","67","446"),
    CASE_68_425("68","425","68","447"),
    CASE_68_427("68","427","68","448"),
    CASE_68_446("68","446","68","449"),
    CASE_68_446_("68","473","68","450"),
    CASE_68_451("68","476","68","451"),
    CASE_68_452("68","477","68","452"),
    CASE_68_478("68","478","68","453"),
    CASE_85_453("69","437","85","454"),
    CASE_70_449("85","457","86","455"),
    CASE_72_460("70","303","70","459"),
    CASE_66_461("72","439","72","460");
    private String originalProcess;
    private String originalSubprocess;
    private String process;
    private String subprocess;

    ConverterProcedure(String originalProcess, String originalSubprocess, String process, String subprocess) {
        this.originalProcess = originalProcess;
        this.originalSubprocess = originalSubprocess;
        this.process = process;
        this.subprocess = subprocess;
    }

    public String getProcess() {
        return process;
    }

    public String getSubprocess() {
        return subprocess;
    }

    @Nullable
    public static ConverterProcedure getRuleConfig(String originalProcess, String originalSubprocess) {
        for (ConverterProcedure rule : ConverterProcedure.values()) {
            if ((Objects.equals(rule.originalProcess, originalProcess) && Objects.equals(rule.originalSubprocess, originalSubprocess)) ||
                    (Objects.equals(rule.process, originalProcess) && Objects.equals(rule.subprocess, originalSubprocess))) {
                return rule;
            }
        }
        return null;
    }
}
