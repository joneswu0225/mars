package com.jones.mars.util;
import com.jones.mars.model.param.TranslationParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by jones on 19-3-16.
 */
@Slf4j
@Component
public class YoudaoUtil {
    
    private static String YOUDAO_URL;

    private static String APP_KEY;
    private static String APP_SECRET;

    @Value("${youdao.tts.url}")
    public void setYoudaoUrl(String youdaoUrl) {
        YoudaoUtil.YOUDAO_URL = youdaoUrl;
    }
    @Value("${youdao.tts.key}")
    public void setAppKey(String appKey) {
        YoudaoUtil.APP_KEY = appKey;
    }
    @Value("${youdao.tts.secret}")
    public void setAppSecret(String appSecret) {
        YoudaoUtil.APP_SECRET = appSecret;
    }
    
    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    public static String loadAsBase64(String filename) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(filename);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(data);
    }

    public static String doRequest(String url, Map<String,String> requestParams) throws Exception{
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : requestParams.keySet()) {
            params.add(new BasicNameValuePair(key, requestParams.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch(IOException e) {
                System.out.println("## release resouce error ##" + e);
            }
        }
        return result;
    }

    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes();
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static String getTranslateInfo(String text, MultipartFile file) throws Exception {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        return getTranslateInfo(text, base64Encoder.encode(file.getBytes()));
    }

    public static String getTranslateInfo(TranslationParam param) throws Exception {
        String text = param.getText();
        String base64 = param.getContent();
        if(!StringUtils.isEmpty(param.getWechatMediaId())){
            base64 = WechatApiUtil.getMediaBase64(param.getWechatMediaId());
        }
        return getTranslateInfo(text, base64);
    }

    public static String getTranslateInfo(String text, String base64) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String langType = "en";
        map.put("appKey", APP_KEY);
        String q = base64;
        map.put("q", base64);
        map.put("format", "wav");
        map.put("rate", "16000");
        map.put("channel", "1");
        map.put("docType", "json");
        map.put("type", "1");
        String salt = UUID.randomUUID().toString();
        map.put("salt", salt);
        map.put("langType", langType);
        map.put("text", text);
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        map.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        map.put("sign", sign);
        map.put("signType", "v2");
        String result = doRequest(YOUDAO_URL, map);
        return result;
    }

}
