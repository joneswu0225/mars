package com.jones.mars.model.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by jones on 18-10-4.
 */
public class CommonConstant {
    public static final int PLATEFROM = 1;
    public static final int NOPLATEFROM = 0;
    public static final String  APP_SOURCE_FIELD = "App-Source";
    public static final String APP_SOURCE_ADMIN = "ADMIN";
    public static final String APP_SOURCE_H5 = "H5";
    public static final String APP_SOURCE_PC = "PC";
    public static final String APP_SOURCE_WEIXIN = "WEIXIN";

    public static final String APP_MODE_DEBUG = "DEBUG";
    public static final String APP_MODE_NOLOGIN = "NOLOGIN";
    public static final String APP_MODE_PRODUCT = "PRODUCT";




}
