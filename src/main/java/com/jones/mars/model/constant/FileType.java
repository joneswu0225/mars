package com.jones.mars.model.constant;

import java.io.File;

public enum FileType {
    HOTSPOT_IMAGE("热点内容图片"),
    HOTSPOT_VIDEO("热点内容音频"),
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

    public String getFilePath(Integer relatedId, String fileName){
        String name = this.name() + "_" + relatedId + "_" + System.currentTimeMillis() + "_" + fileName;
        switch (this){
            case USER_NAMECARD:
                return "user" + File.separator + "namecard" + File.separator + name;
            case USER_AVATAR:
                return "user" + File.separator + "avatar" + File.separator + name;
            case BLOCK_STRUCTURE:
                return "enterprise" + File.separator + relatedId + File.separator + "block" + File.separator + name;
            case ENTERPRIISE_IMAGE:
                return "enterprise" + File.separator + relatedId + File.separator + "image" + File.separator + name;
            case ENTERPRISE_LOGO:
                return "enterprise" + File.separator + relatedId + File.separator + "image" + File.separator + name;
            case HOTSPOT_IMAGE:
                return "enterprise" + File.separator + relatedId + File.separator + "hotspot" + File.separator + name;
            case HOTSPOT_VIDEO:
                return "enterprise" + File.separator + relatedId + File.separator + "hotspot" + File.separator + name;
            default:
                return null;
        }
    }
}

