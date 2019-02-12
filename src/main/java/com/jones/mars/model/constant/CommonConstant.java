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
    public static String APP_COOKIE_DOMAIN;

    @Value("${app.cookie.domain:chickai-tech.cn}")
    public void setAppCookieDomain(String appCookieDomain) {
        APP_COOKIE_DOMAIN = appCookieDomain;
    }
}
