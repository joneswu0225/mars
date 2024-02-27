package com.jones.mars.constant;

import com.jones.mars.util.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConst {

    public static String APP_MODE;
    public static List<String> APP_TABLES;
    public static Integer DEPLOY_ID;

    //    private String appMode;
    public static String[] APP_MODE_NOLOGIN_SOURCE;
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

    @Value("${app.deployId}")
    public void setDeployId(Integer deployId) {
        DEPLOY_ID = deployId;
    }
    @Value("${app.tables:app_const,block,block_attachment,block_attachment_content,block_class,block_content,block_examine,block_examine_content,block_image,block_module,block_project,block_scene_type,comment,const_city,const_province,department,department_user,enterprise,enterprise_user,file_upload,hotspot,hotspot_content,message,news,project,project_hotspot,project_scene,project_user,role,role_permission,scene,task,user,user_like,user_profile,user_role,weprogram_info}")
    public void setAppTables(String appTables) {
        APP_TABLES = Arrays.asList(appTables.split(","));
    }
    public static Integer getTableId(String tableName){
        return APP_TABLES.indexOf(tableName) + 1;
    }

    public static String generateId(String tableName){
        return new Snowflake(DEPLOY_ID, getTableId(tableName)).nextId() + "";
    }

    @Value("${app.mode.nologin.source.prefix:}")
    public void setAppModeNologinSource(String appModeNologinSource){
        APP_MODE_NOLOGIN_SOURCE = appModeNologinSource.split(",");
    }
    @Value("${app.mode:DEBUG}")
    public void setAppMode(String appMode){
        APP_MODE = appMode;
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
