package com.jones.mars.model.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by jones on 18-10-4.
 */
@Component
public class CommonConstant {
    public static final int PLATEFROM = 1;
    public static final int NOPLATEFROM = 0;
    public static final String  APP_SOURCE_FIELD = "App-Source";
    public static final String APP_SOURCE_ADMIN = "ADMIN";
    public static final String APP_SOURCE_H5 = "H5";
    public static final String APP_SOURCE_PC = "PC";
    public static final String APP_SOURCE_WEIXIN = "WEIXIN";
    public static String APP_COOKIE_DOMAIN;

    @Value("${app.cookie.domain:chickai-tech.cn}")
    public void setAppCookieDomain(String appCookieDomain) {
        APP_COOKIE_DOMAIN = appCookieDomain;
    }
}
