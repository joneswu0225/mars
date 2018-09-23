package com.jones.mars.model.constant;

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
}

