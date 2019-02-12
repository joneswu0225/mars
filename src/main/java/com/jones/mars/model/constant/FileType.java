package com.jones.mars.model.constant;

import java.io.File;

public enum FileType {
    HOTSPOT_IMAGE("热点内容图片"),
    HOTSPOT_AUDIO("热点内容音频"),
    HOTSPOT_HTML("热点内容HTML"),
    PROJECT_IMAGE("项目封面"),
    BLOCK_STRUCTURE("船体平面图"),
    USER_NAMECARD("用户名片"),
    USER_AVATAR("用户头像"),
    ENTERPRIISE_IMAGE("企业图标"),
    ENTERPRISE_LOGO("企业LOGO")
    ;

    public final String description;

    FileType(String description) {
        this.description = description;
    }

    public static final String FILE_PATH_PREFIX = "static/";
    public String getFilePath(Integer relatedId, String fileName){
        String name = this.name() + "_" + relatedId + "_" + System.currentTimeMillis() + "_" + fileName;
        String relPath = "";
        switch (this){
            case USER_NAMECARD:
                relPath = "user" + File.separator + "namecard" + File.separator + name;
                break;
            case USER_AVATAR:
                relPath = "user" + File.separator + "avatar" + File.separator + name;
                break;
            case BLOCK_STRUCTURE:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "block" + File.separator + name;
                break;
            case PROJECT_IMAGE:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "project" + File.separator + name;
                break;
            case ENTERPRIISE_IMAGE:
            case ENTERPRISE_LOGO:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "image" + File.separator + name;
                break;
            case HOTSPOT_IMAGE:
            case HOTSPOT_AUDIO:
            case HOTSPOT_HTML:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "hotspot" + File.separator + name;
                break;
            default:
                relPath = "";
        }
        return FILE_PATH_PREFIX + relPath;
    }
}

