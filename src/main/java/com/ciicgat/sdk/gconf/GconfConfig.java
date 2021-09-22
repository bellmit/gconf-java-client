/*
 * Copyright 2007-2021, CIIC Guanaitong, Co., Ltd.
 * All rights reserved.
 */

package com.ciicgat.sdk.gconf;


import java.util.Objects;

/**
 * config of gconf
 * Created by August.Zhou on 2018/8/23 10:21.
 */
public class GconfConfig {
    private final String domain;
    private String appName;

    private GconfConfig(String domain, String appName) {
        this.domain = domain;
        this.appName = appName;
    }

    public String getDomain() {
        return domain;
    }

    public String getAppName() {
        return appName;
    }

    public GconfConfig setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public static final GconfConfig INSTANCE = autoDetectGconfConfig();


    private static GconfConfig autoDetectGconfConfig() {
        String domain = null;
        if (Objects.nonNull(System.getenv("KUBERNETES_SERVICE_HOST"))) {
            domain = "gconf.kube-system";
        } else {
            String workEnv = System.getenv("WORK_ENV");
            if (workEnv == null) {
                workEnv = "dev";
            }
            String workIdc = System.getenv("WORK_IDC");
            if (workIdc == null) {
                workIdc = "ofc";
            }
            domain = "gconf.services." + workEnv + "." + workIdc;
        }
        return new GconfConfig(domain, System.getenv("APP_NAME"));
    }
}
