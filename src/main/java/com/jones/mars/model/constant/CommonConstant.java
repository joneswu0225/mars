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
@Component
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

    public static String UPLOAD_PATH;
    public static String UPLOAD_PATH_TMP_PANOIMAGE;
    public static String UPLOAD_PATH_TMP_AUDIO;
    public static String APP_DOMAIN;

    @Value("${app.domain:vr2shipping.com}")
    public void setAppDomain(String appDomain) {
        APP_DOMAIN = appDomain;
    }

    @Value("${app.file.path.upload:./pano}")
    public void setUploadPath(String uploadPath) {
        UPLOAD_PATH = uploadPath;
    }
    @Value("${app.file.path.upload.temp.panoimage:}")
    public void setUploadPathTmpPanoimage(String uploadPathTmpPanoimage){
        UPLOAD_PATH_TMP_PANOIMAGE = uploadPathTmpPanoimage;
    }
    @Value("${app.file.path.upload.temp.audio:}")
    public void setUploadPathTmpAudio(String uploadPathTmpAudio){
        UPLOAD_PATH_TMP_AUDIO = uploadPathTmpAudio;
    }

    public static String getTmpPanoimagePath(){
        return Paths.get(UPLOAD_PATH, UPLOAD_PATH_TMP_PANOIMAGE).normalize().toString();
    }
    public static String getTmpAudioPath(){
        return Paths.get(UPLOAD_PATH, UPLOAD_PATH_TMP_AUDIO).normalize().toString();
    }

    private void initDir(String path){
        File tempPath = Paths.get(path).toFile();
        if(!tempPath.exists()){
            tempPath.mkdirs();
        }
    }

    @PostConstruct
    public void init(){
        initDir(UPLOAD_PATH);
        initDir(getTmpPanoimagePath());
        initDir(getTmpAudioPath());
    }


}
