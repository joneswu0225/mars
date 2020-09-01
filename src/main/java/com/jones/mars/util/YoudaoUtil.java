package com.jones.mars.util;
import com.jones.mars.model.param.TranslationParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
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

import java.io.*;
import java.net.URLEncoder;
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
    
    private static String BASE_URL;// = "https://openapi.youdao.com";
    private static String TMP_PATH;// = "files/tmp/audio";

    private static String APP_KEY;// = "17e32ff2275a6525";
    private static String APP_SECRET;// = "atzwO8jUsYPoX8lOgfuV8H6fm0mnN8WM";

    @Value("${youdao.tts.url}")
    public void setYoudaoUrl(String youdaoUrl) {
        YoudaoUtil.BASE_URL = youdaoUrl;
    }
    @Value("${youdao.tts.key}")
    public void setAppKey(String appKey) {
        YoudaoUtil.APP_KEY = appKey;
    }
    @Value("${youdao.tts.secret}")
    public void setAppSecret(String appSecret) {
        YoudaoUtil.APP_SECRET = appSecret;
    }
    @Value("${app.file.path.tmp.audio}")
    public void setTmpPath(String tmpPath) {
        YoudaoUtil.TMP_PATH = tmpPath;
    }


    private static class TTS {
        // https://ai.youdao.com/DOCSIRMA/html/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90TTS/API%E6%96%87%E6%A1%A3/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90%E6%9C%8D%E5%8A%A1/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
        public static final String URL = BASE_URL + "/ttsapi";
        public static final String FORMAT = "mp3";
        public static final String VOICE = "0";
        public static final String SPEED = "1";
        public static final String VOLUME = "1";
        // https://ai.youdao.com/DOCSIRMA/html/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90TTS/API%E6%96%87%E6%A1%A3/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90%E6%9C%8D%E5%8A%A1/%E8%AF%AD%E9%9F%B3%E5%90%88%E6%88%90%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html#section-9
        public static final String LANG_TYPE = "zh-CHS";

        public static Map<String, String> buildHttpParam(String q) throws UnsupportedEncodingException {
            Map<String, String> param = new HashMap<>();
            param.put("langType", LANG_TYPE);
            param.put("appKey", APP_KEY);
            param.put("voice", VOICE);
            param.put("format", FORMAT);
            param.put("speed", SPEED);
            param.put("volume", VOLUME);
            String salt = UUID.randomUUID().toString();
            String signStr = APP_KEY + q + salt + APP_SECRET;
            String sign = getDigest(signStr, "MD5");
            param.put("salt", salt);
            param.put("sign", sign);
            param.put("q", q);
            return param;
        }
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

    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            System.out.println(e.toString());
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
        try{
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            System.out.println("Content-Type:" + contentType[0].getValue());
            if("audio/mp3".equals(contentType[0].getValue())){
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] stream = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                String fileName = "youdaotts_" + System.currentTimeMillis() + ".mp3";
                if(stream != null){//合成成功
                    String file = TMP_PATH + "/" + fileName;
                    byte2File(stream,file);
                }
                result = fileName;
            }else{
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity,"UTF-8");
                EntityUtils.consume(httpEntity);
                System.out.println(json);
                result = json;
            }
        }finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                System.out.println("## release resouce error ##" + e);
            }
        }
        return result;
    }

    public static String getDigest(String string, String digestType) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes();
        try {
            MessageDigest mdInst = MessageDigest.getInstance(digestType);
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
        String sign = getDigest(signStr, "SHA-256");
        map.put("sign", sign);
        map.put("signType", "v2");
        String result = doRequest(BASE_URL + "/iseapi", map);
        return result;
    }

    /**
     * 语音合成
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getTTSInfo(String text) throws Exception {
        Map<String, String> param = TTS.buildHttpParam(text);
        String result = doRequest(TTS.URL, param);
        return result;
    }

//    public static void main(String[] args) throws Exception {
//        System.out.println(getTTSInfo("我天天去上学"));
//    }

}
