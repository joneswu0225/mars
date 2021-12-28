package com.jones.mars.model.constant;

import java.io.File;

public enum FileType {
    HOTSPOT_IMAGE("热点内容图片"),
    HOTSPOT_AUDIO("热点内容音频"),
    HOTSPOT_HTML("热点内容HTML"),
    HOTSPOT_VIDEO("热点内容视频"),
    HOTSPOT_MEDIA("热点内容富文本"),
    HOTSPOT_ATTACH("热点附件"),
    PROJECT_IMAGE("项目封面"),
    PROJECT_VIDEO("项目视频"),
    PROJECT_MEDIA("项目富文本"),
    PROJECT_ATTACH("项目附件"),
    BLOCK_STRUCTURE("船体平面图"),
    BLOCK_ATTACH("模块附件"),
    SCENE_PANO_IMG("场景原图"),
    NEWS_MEDIA("新闻富文本"),
    NEWS_ATTACH("新闻附件"),
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
                relPath = "block" + File.separator + relatedId + File.separator + "structure_img" + File.separator + name;
                break;
            case BLOCK_ATTACH:
                relPath = "block" + File.separator + relatedId + File.separator + "block_extra" + File.separator + name;
                break;
            case SCENE_PANO_IMG:
                relPath = "block" + File.separator + relatedId + File.separator + "scene_pano_img" + File.separator + fileName;
                break;
            case PROJECT_IMAGE:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "project" + File.separator + name;
                break;
            case PROJECT_MEDIA:
            case PROJECT_ATTACH:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "project_extra" + File.separator + name;
                break;
            case ENTERPRIISE_IMAGE:
            case ENTERPRISE_LOGO:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "image" + File.separator + name;
                break;
            case HOTSPOT_IMAGE:
            case HOTSPOT_AUDIO:
            case HOTSPOT_VIDEO:
            case HOTSPOT_HTML:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "hotspot" + File.separator + name;
                break;
            case HOTSPOT_MEDIA:
            case HOTSPOT_ATTACH:
                relPath = "enterprise" + File.separator + relatedId + File.separator + "hotspot_extra" + File.separator + name;
                break;
            case NEWS_MEDIA:
                relPath = "news" + File.separator + relatedId + File.separator + "news_media" + File.separator + name;
                break;
            case NEWS_ATTACH:
                relPath = "news" + File.separator + relatedId + File.separator + "news_extra" + File.separator + name;
                break;
            default:
                relPath = "";
        }
        return FILE_PATH_PREFIX + relPath;
    }
}

