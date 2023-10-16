package com.mx.profuturo.grupo.launchermau.core.rules;

import androidx.annotation.NonNull;

import com.mx.profuturo.grupo.launchermau.core.tags.TagsBuilder;

import java.util.Objects;

public enum Rules {
    //Rules for identificaT
    FULL_CONF_MAU ("-1", "-1", "-1", TagsBuilder.RULE_FULL_CONFIG),
    MAU_IDENTIFICAT_MD_EXP_SERV ("37", "243", "478", TagsBuilder.RULE_AUTH),
    MAU_IDENTIFICAT_MD_EXP_IDENTIFICACION ("37", "243", "477", TagsBuilder.RULE_AUTH),
    MAU_PENSIONAT_MD_FORMULARIO("33", "66", "461", TagsBuilder.RULE_ENROLLMENT),
    MAU_PENSIONAT_MD_TRAMITE("33", "-1", "-1", TagsBuilder.RULE_AUTH);

    private final String origin;
    private final String process;
    private final String subprocess;
    private final String config;

    Rules( String origin, String process, String subprocess, String config ) {
        this.origin = origin;
        this.process = process;
        this.subprocess = subprocess;
        this.config = config;
    }

    public String getOrigin() {
        return origin;
    }

    public String getProcess() {
        return process;
    }

    public String getSubprocess() {
        return subprocess;
    }

    public String getConfig() {
        return config;
    }

    @NonNull
    public static Rules getRuleConfig(String origin, String process, String subprocess) {
        for (Rules rule : Rules.values()) {
            if (Objects.equals(rule.origin, origin)
            && Objects.equals(rule.process, process)
            && Objects.equals(rule.subprocess, subprocess)) {
                return rule;
            }
        }
        if (Objects.equals(origin, TagsBuilder.ORIGIN_PENSIONAT)) return Rules.MAU_PENSIONAT_MD_TRAMITE;
        return Rules.FULL_CONF_MAU;
    }

}
