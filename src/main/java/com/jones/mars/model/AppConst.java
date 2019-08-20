package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.List;

@Data
public class AppConst extends BaseObject {
    public static final String HOME_RECOMMEND_PROJECT = "HOME_RECOMMEND_PROJECT";
    public static final String HOME_ENTERPRISE_SHOWN = "HOME_ENTERPRISE_SHOWN";
    public static final String HOME_SERVICE_SUPERIORITY = "HOME_SERVICE_SUPERIORITY";
    public static final String HOME_SERVICE_PROVIDE = "HOME_SERVICE_PROVIDE";
    public static final String HOME_CHOICE_REASON = "HOME_CHOICE_REASON";
    public static final String HOME_ENTERPRISE_WECHAT = "HOME_ENTERPRISE_WECHAT";
    public static final String HOME_CONTACT_ENTERPRISE_NAME = "HOME_CONTACT_ENTERPRISE_NAME";
    public static final String HOME_CONTACT_MOBILE = "HOME_CONTACT_MOBILE";
    public static final String HOME_CONTACT_ADDRESS = "HOME_CONTACT_ADDRESS";
    public static final String HOME_CONTACT_EMAIL = "HOME_CONTACT_EMAIL";
    public static final String HOME_INTERNET_CONTENT_PROVIDER = "HOME_INTERNET_CONTENT_PROVIDER";
    public static final String PROJECT_GUIDE_HOTSPOT_IMAGE = "PROJECT_GUIDE_HOTSPOT_IMAGE";
    private String name;
    private String value;
    private String detail;
    private List<String> values;
}

