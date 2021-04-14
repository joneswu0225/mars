package com.jones.mars.util;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.WeprogramInfo;
import com.jones.mars.model.query.WeprogramInfoQuery;
import com.jones.mars.repository.WeprogramInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jones on 19-9-2.
 */
@Component
@Slf4j
public class WechatWeProgramUtil {
    @Autowired
    private WeprogramInfoMapper weprogramInfoMapper;
    private static Map<Integer, WeprogramInfo> weprogramInfoMap;

    public static String appId;
    private static String appSecret;
    public static final String WECHAT_API_URL_BASE = "https://api.weixin.qq.com";
    public static String URL_CODE_TO_SESSION = WECHAT_API_URL_BASE + "/sns/jscode2session?grant_type=authorization_code&appid=%s&secret=%s&js_code=";

    @PostConstruct
    public void init() {
        URL_CODE_TO_SESSION = String.format(URL_CODE_TO_SESSION, appId, appSecret);
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    private void refreshWeprogramInfo(){
        Map<Integer, WeprogramInfo> weprogramInfoMap = weprogramInfoMapper.findAll(WeprogramInfoQuery.builder().build())
                .stream().collect(Collectors.toMap(WeprogramInfo::getId, p->p));
        WechatWeProgramUtil.weprogramInfoMap = weprogramInfoMap;
    }
    /**
     * 　code 转化为sessionKey
     *
     * @param code
     */
    public static JSONObject getSessionKey(Integer weprogramId, String code) {
        try {
            JSONObject resp = HttpClientUtil.getJson(weprogramInfoMap.get(weprogramId).getSessionUrl() + code);
            log.info("wechat code to session key resp: " + resp.toJSONString());
            return resp;
        } catch (Exception e) {
            log.error("fail to get wechat session key", e);
        }
        return null;
    }

    /**
     * 获取微信方的用户信息
     *
     * @param code
     * @param encrypedData
     * @param iv
     * @return
     */
    public static Map<String, String> getUserInfo(Integer weprogramId, String code, String encrypedData, String iv) {
        log.info("start to parse wx user info: \\n code:{}\\n encryptedData:{}\\n iv:{}", code, encrypedData, iv);
        Map<String, String> resultMap = null;
        JSONObject sessionInfo = getSessionKey(weprogramId, code);
        String sessionKey = sessionInfo.getString("session_key");
        if (!StringUtils.isEmpty(sessionKey)) {
            JSONObject decryptedResult = getDecryptedUserInfo(sessionKey, encrypedData, iv);
            String openid = sessionInfo.getString("openid");
//            String unionid = sessionInfo.getString("unionid");
            if (appId.equals(decryptedResult.getJSONObject("watermark").getString("appid"))) {
                resultMap = new HashMap<>();
                resultMap.put("mobile", decryptedResult.getString("purePhoneNumber"));
                resultMap.put("openid", openid);
//                resultMap.put("unionid", unionid);
            } else {
                log.error("either code({}) or encrypedData({}) or iv({}) is invalid", code, encrypedData, iv);
            }
        }
        return resultMap;
    }

    public static JSONObject getDecryptedUserInfo(String sessionKey, String encryptedData, String iv) {
        try {
            log.info("start to decrypt user info:  \n sessionKey:{}\n encryptedData:{}\n iv:{}", sessionKey, encryptedData, iv);
            String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            JSONObject decryptedResult = JSONObject.parseObject(result);
            log.info("succeed in decrypt wx user info: \\n {}", JSONObject.toJSONString(decryptedResult));
            return decryptedResult;
        } catch (Exception e) {
            log.error("fail to decrypt mobile");
        }
        return null;
    }

    public static void main(String[] args) {
        String code = "0117nWkl2CCWz541Wqnl2NnG5o37nWkK";
        String encryptedData = "jxme4Eg3tWEA0EobDQTfMvfzfhEqSK0Rqat49Vzo3W70WRemby6nK3UcDPZ5PeQgPpvtvsUoMvtJWSbH3NqLy40gXPcGBWsehPCiVu53X3wTMqi1ByyGVel8nXsMW1Il0l8afTQiyotSnbExu/9EfCevP7tUPnXfSSt/KMLFqBYQ7EvU8o/Or/4NSobIDZqoxtDJRPUp7fX6BzvB3iTzxiXcdR7YiOLPR+8fegnXngzwwVFWFpAeUrbo/C8WRgFuLGBdS4601ru04smlgXld+ekfZhO3+FXHx/41z9jjfi/WAhcSBZ9e4PHsF7THnzwqhBd/Xm7P1Ufd5ZsPJ+/UxLw1LbnvgGAkUHey7knBB/yXrrAyDhVuT7+afyYMPtjjQFhb5EEoc/RvaN0417dIXPnST/ggVaZH9X9NI2uBEvNB2r0YGVxQdB759QzAbLTCR0DEPiescIgSIezKAqmtmJM02yw8+VHiQIGl2TLJxts=";
        String iv = "4+5ZrMc/Fyd42cjMSGmN2g==";
//        System.out.println(JSONObject.toJSONString(getSessionKey("0018MKFa1OEWAz0tKRIa1jbapn48MKFc")));
        String sessionKey = "v0+5YOV/yfWP4dOlw3gkAQ==";
        encryptedData = "7Ch6Sfw7zGAedjC8s//7iY6raFnIyj81yb1iPZfkmCg1Q7Ra+va1xD+iz81+OM5LoaUb3fva105WsieYtBhx0Gi5VO1djYYWsaKdC06nJphWFUW65kiqufkg2fzkJ2mrsnv2ptmqp61Lk/SLdiVXSvCNEDYE4+TbTlXlPhQAQ73Mmjzgg/ijPc2v9NulOKm9ePrgiN0t9WkKQGl7T7mE4Q==";
        iv = "bVH5hTzu0LDX7bTfcTAU1g==";
//        System.out.println(JSONObject.toJSONString(getDecryptedUserInfo(sessionKey, encryptedData,iv)));
//        WechatApiUtil util = new WechatApiUtil();

        JSONObject decryptedResult = getDecryptedUserInfo(sessionKey, encryptedData, iv);
//            String unionid = sessionInfo.getString("unionid");
        if ("wxff2a14ab973dffe0".equals(decryptedResult.getJSONObject("watermark").getString("appid")))
            System.out.println(decryptedResult.getString("purePhoneNumber"));
//                resultMap.put("unionid", unionid);

//        System.out.println(JSONObject.toJSONString(util.getUserInfo(code,encryptedData,iv)));
    }

    @Value("${wechat.weprogram.app.id}")
    private void setAppId(String appId){
        WechatWeProgramUtil.appId = appId;
    }
    @Value("${wechat.weprogram.app.secret}")
    private void setAppSecret(String appSecret){
        WechatWeProgramUtil.appSecret = appSecret;
    }
}


