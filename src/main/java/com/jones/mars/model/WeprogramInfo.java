package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeprogramInfo extends BaseObject {
    public static final String WECHAT_API_URL_BASE = "https://api.weixin.qq.com";
    public static String URL_CODE_TO_SESSION = WECHAT_API_URL_BASE + "/sns/jscode2session?grant_type=authorization_code&appid=%s&secret=%s&js_code=";

    private String name;
    private String detail;
    private String appId;
    private String appSecret;
    private String info;

    public String getSessionUrl(){
        return String.format(URL_CODE_TO_SESSION, this.appId, this.appSecret);
    }
}

