package com.jones.mars.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

/**
 * Created by jones on 19-9-2.
 */
@Component
@Slf4j
public class WechatApiUtil {
//    private static String appId = "wxaa750edf0afd9bd6";
//    private static String appSecret = "44a0a56e3f611e031f85f547801dc9fd";
    public static String appId = "wxff2a14ab973dffe0";
    private static String appSecret = "22489a930bd2b48ff8865fc7c1b6312c";
    private static String accessToken;
    private String jsTicket;
    public static final String WECHAT_API_URL_BASE = "https://api.weixin.qq.com";
    public static String URL_GET_ACCESS_TOKEN = WECHAT_API_URL_BASE + "/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static String URL_CODE_TO_SESSION = WECHAT_API_URL_BASE + "/sns/jscode2session?grant_type=authorization_code&appid=%s&secret=%s&js_code=";
//    public static String URL_CODE_TO_SESSION = WECHAT_API_URL_BASE + "/sns/jscode2session?grant_type=authorization_code&appid=wxff2a14ab973dffe0&secret=22489a930bd2b48ff8865fc7c1b6312c&js_code=";
    public static String URL_GET_JS_TICKET = WECHAT_API_URL_BASE + "/cgi-bin/ticket/getticket?type=jsapi&access_token=%s";
    public static String URL_GET_MEDIA = WECHAT_API_URL_BASE + "/cgi-bin/media/get?access_token=%s&media_id=%s";
    public static String FFMPEG_PATH = "/usr/local/ffmpeg/bin/ffmpeg";
    private static String fileUploadPath = "";

    @PostConstruct
    public void init(){
        URL_GET_ACCESS_TOKEN = String.format(URL_GET_ACCESS_TOKEN, appId, appSecret);
        URL_CODE_TO_SESSION = String.format(URL_CODE_TO_SESSION, appId, appSecret);
        generateAccessToken();
    }

    public String getAccessToken(){
        return WechatApiUtil.accessToken;
    }


    public String getJsTicket(){
        if(StringUtils.isEmpty(jsTicket)){
            generateJsTicket();
        }
        return jsTicket;
    }

    public Map<String, String> getJsSdkSign(String url){
        Map<String, String> result = Sign.sign(jsTicket, url);
        result.put("appid", appId);
        result.put("access_token", getAccessToken());
        return result;
    }

    /**
     * speex 解码： https://github.com/lyflyy/wechat-speex-declib  https://blog.csdn.net/scropio0zry/article/details/84929451
     * ffmpeg
     * 安装中出现的问题： https://blog.csdn.net/baidu_38172402/article/details/80875074
     * https://blog.csdn.net/chen5287603/article/details/51549620
     * https://blog.csdn.net/luoqiang616477607/article/details/81505897
     * @Title: changeToWav http://www.sauronsoftware.it/projects/jave/index.php
     * @Description: TODO(将语音文件转为wav格式)
     * @param @param sourcePath
     * @param @param targetPath    参数
     * @return void    返回类型
     * @throws
     */
/*    @Deprecated
    public static void changeToWav_fail(String sourcePath, String targetPath,Float duration) {
        try {
            File source = new File(sourcePath);
            File target = new File(targetPath);
            AudioAttributes audio = new AudioAttributes();
            Encoder encoder = new Encoder();
            //设置解码格式，比特率，位数，声道等信息
//            audio.esetCodec("wav");
            audio.setBitRate(new Integer(16000));
            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(8000));
            audio.setVolume(new Integer(500));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("wav");
            attrs.setAudioAttributes(audio);
//            attrs.setDuration(duration);
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }*/

    public static void changeToWav(String sourcePath, String targetPath){
        File tool = new File(FFMPEG_PATH);
        if (tool.exists()) {
            String c = FFMPEG_PATH + " -y -i " + "\"" + sourcePath + "\"" + " -ar 8000 -ab 12.2k -ac 1 " + "\"" + targetPath + "\"";
            try {
                Process p = Runtime.getRuntime().exec(c);
                p.destroy();
            } catch (IOException e) {
                log.error("ffmpeg工具转换失败", e);
            }finally{
                log.info("用ffmpeg工具进行了转换！");
            }
        }else {
            log.info("ffmpeg工具不存在！");
        }
    }



    public static void main2(String[] args) {
        String toolPath = "/usr/local/ffmpeg/bin/ffmpeg";
        String sourcePath = "/home/test4.amr";
        String targetPath = "/home/test4.wav";
        changeToWav(sourcePath, targetPath);
//        changeToWav("/home/jones/test2.amr", "/home/jones/test2.wav",1000.0f);
    }

    public static String getMediaBase64(String mediaId){
        String mediaUrl = String.format(URL_GET_MEDIA, accessToken, mediaId);
        log.info("start to get voice info from [ " + mediaUrl + " ]");
        String result = null;
        try{
            String uuid = UUID.fromString(mediaId).toString();
            URL url = new URL(mediaUrl);

            InputStream inStream = url.openConnection().getInputStream();
            String sourcePath = String.format("%s/%s/%s_%s",fileUploadPath,"tmp", mediaId, System.currentTimeMillis());
            Files.copy(inStream, Paths.get(sourcePath), StandardCopyOption.REPLACE_EXISTING);
            String targetPath = sourcePath + ".wav";
            changeToWav(sourcePath, targetPath);
            result = ObjectUtil.getBase64FromInputStream(new FileInputStream(new File(targetPath)));
        }catch (Exception e){
            log.error("fail to download wechat files", e);
        }
        return result;
    }

    /**
     *　code 转化为sessionKey
     * @param code
     */
    public static JSONObject getSessionKey(String code){
        try {
            JSONObject resp = HttpClientUtil.getJson(URL_CODE_TO_SESSION + code);
            log.info("wechat code to session key resp: " + resp.toJSONString());
            return resp;
        } catch (Exception e){
            log.error("fail to get wechat session key", e);
        }
        return null;
    }

    /**
     * 获取微信方的用户信息
     * @param code
     * @param encrypedData
     * @param iv
     * @return
     */
    public static Map<String, String> getUserInfo(String code, String encrypedData, String iv) {
        log.info("start to parse wx user info: \\n code:{}\\n encryptedData:{}\\n iv:{}", code, encrypedData, iv);
        Map<String, String> resultMap = null;
        JSONObject sessionInfo = getSessionKey(code);
        String sessionKey = sessionInfo.getString("session_key");
        if(!StringUtils.isEmpty(sessionKey)) {
            JSONObject decryptedResult = getDecryptedUserInfo(sessionKey, encrypedData, iv);
            String openid = sessionInfo.getString("openid");
            String unionid = sessionInfo.getString("unionid");
            if(appId.equals(decryptedResult.getJSONObject("watermark").getString("appid"))){
                resultMap.put("mobile", decryptedResult.getString("purePhoneNumber"));
                resultMap.put("openid", openid);
                resultMap.put("unionid", unionid);
            } else {
                log.error("either code({}) or encrypedData({}) or iv({}) is invalid", code, encrypedData, iv);
            }
        }
        return resultMap;
    }

    public static JSONObject getDecryptedUserInfo(String sessionKey, String encrypedData, String iv){
        try {
            String result = AesCbcUtil.decrypt(encrypedData, sessionKey, iv, "UTF-8");
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
        String iv= "4+5ZrMc/Fyd42cjMSGmN2g==";
        System.out.println(JSONObject.toJSONString(getSessionKey("0018MKFa1OEWAz0tKRIa1jbapn48MKFc")));
        appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        iv = "r7BXXKkLb8qrSNn05n0qiA==";
        System.out.println(JSONObject.toJSONString(getDecryptedUserInfo(sessionKey, encryptedData,iv)));
//        WechatApiUtil util = new WechatApiUtil();
//        System.out.println(JSONObject.toJSONString(util.getUserInfo(code,encryptedData,iv)));
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    private void generateAccessToken() {
        try {
            JSONObject resp = HttpClientUtil.getJson(URL_GET_ACCESS_TOKEN);
            log.info("wechat access token resp: " + resp.toJSONString());
            WechatApiUtil.accessToken = resp.getString("access_token");
        } catch (Exception e){
            log.error("fail to get wechat access token", e);
        }
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    private void generateJsTicket(){
        try {
            JSONObject resp = HttpClientUtil.getJson(String.format(URL_GET_JS_TICKET, this.accessToken));
            log.info("wechat js ticker resp: " + resp.toJSONString());
            this.jsTicket = resp.getString("ticket");
            log.info("{\"msg\":\"tt='aa'\"}");
        } catch (Exception e){
            log.error("fail to get wechat js ticket", e);
        }
    }
    @Scheduled(cron = "20 * * * * ?")
    private void test(){
        log.info("{\"msg\":\"tt='aa'\"}");
    }

    @Value("${wechat.app.id}")
    private void setAppId(String appId){
        WechatApiUtil.appId = appId;
    }
    @Value("${wechat.app.secret}")
    private void setAppSecret(String appSecret){
        WechatApiUtil.appSecret = appSecret;
    }
    @Value("${app.file.path.upload}")
    private void setFileUploadPath(String fileUploadPath){
        WechatApiUtil.fileUploadPath = fileUploadPath;
    }
}


class Sign {
    public static void main(String[] args) {
//        String jsapi_ticket = "jsapi_ticket";
        String jsapi_ticket = "HoagFKDcsGMVCIY2vOjf9k-DY8Nlea81VCjEWDOuq4RZP9VbNWb3kiKZiynZo1587skOxbK0-1fs1fkIeoFWDg";

        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "https://pano.vr2shipping.com/default.html?id=246";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("noncestr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}

