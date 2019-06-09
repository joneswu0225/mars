package com.jones.mars.util;
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
import org.springframework.stereotype.Component;
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
    private static final String YOUDAO_URL = "https://openapi.youdao.com/iseapi";

    private static final String APP_KEY = "7a19ae0161050256";
    private static final String APP_SECRET = "Aqo72kCzADtOl6B4l87bzzyLtQXlhsJa";

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
