package com.jones.mars.util;

import com.aliyuncs.utils.StringUtils;
import com.jones.mars.constant.ApplicationConst;
import com.jones.mars.model.constant.CommonConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jones on 19-5-29.
 */
@Component
@Profile({"media"})
public class XunfeiUtil {
    public static String APPID = "5ce4ebb1";
    public static String BASE_URL = "http://api.xfyun.cn/v1/service/v1";
    // 语音评测接口密钥
    public static String ISE_API_KEY = "2b731f0599fa426e3e5250a96874c9a4";
    // 语音听写接口密钥
    public static String IAT_API_KEY = "a12523df97cef9b716159e1f4da8c2e9";
    // 语音合成接口密钥
    public static String TTS_API_KEY = "a5ca35109f8bb5517ffd1d89b53f3fd0";


    /**
     * 组装http请求头
     */
    public static Map<String, String> buildHttpHeader(String param, String apiKey) throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(apiKey + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }

    public static class ISE {
        // 合成webapi接口地址
        public static final String URL = BASE_URL + "/ise";
        // 音频编码
        public static final String AUE = "raw";
        // 采样率
        public static final String AUF = "audio/L16;rate=16000";
        // 结果级别
        public static final String RESULT_LEVEL = "entirety";
        // 语种
        public static final String LANGUAGE = "en_us"; // en_us, zh_cn
        // 评测种类
        public static final String CATEGORY = "read_sentence";

        public static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
            String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"result_level\":\"" + RESULT_LEVEL + "\",\"language\":\"" + LANGUAGE + "\",\"category\":\"" + CATEGORY + "\"}";
            return XunfeiUtil.buildHttpHeader(param, ISE_API_KEY);
        }
    }

    public static class IAT {
        // 听写webapi接口地址
        public static final String URL = BASE_URL + "/iat";
        // 音频编码
        public static final String AUE = "raw";
        // 引擎类型
        //（听写服务：engine_type为识别引擎类型，开通webapi听写服务后默认识别普通话与英文：示例音频请在听写接口文档底部下载
        // sms16k（16k采样率、16bit普通话音频、单声道、pcm或者wav）
        // sms8k（8k采样率、16bit普通话音频、单声道、pcm或者wav） 
        // sms-en16k（16k采样率，16bit英语音频，单声道，pcm或者wav）
        // sms-en8k（8k采样率，16bit英语音频，单声道，pcm或者wav）
        // 请使用cool edit Pro软件查看音频格式是否满足相应的识别引擎类型，不满足则识别为空（即返回的data为空，code为0），或者识别为错误文本）
        // 音频格式转换可参考（讯飞开放平台语音识别音频文件格式说明）：https://doc.xfyun.cn/rest_api/%E9%9F%B3%E9%A2%91%E6%A0%BC%E5%BC%8F%E8%AF%B4%E6%98%8E.html
        public static final String ENGINE_TYPE = "sms16k";
        // 后端点（取值范围0-10000ms）
        public static final String VAD_EOS = "10000";

        public static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
            String param = "{\"aue\":\""+AUE+"\""+",\"engine_type\":\"" + ENGINE_TYPE + "\""+",\"vad_eos\":\"" + VAD_EOS + "\"}";
            return XunfeiUtil.buildHttpHeader(param, IAT_API_KEY);
        }
    }

    private static class TTS {
        // 合成webapi接口地址
        public static final String URL = BASE_URL + "/tts";
        // 音频编码(raw合成的音频格式pcm、wav,lame合成的音频格式MP3)
        public static final String AUE = "raw";
        // 采样率
        public static final String AUF = "audio/L16;rate=16000";
        // 语速（取值范围0-100）
        public static final String SPEED = "50";
        // 音量（取值范围0-100）
        public static final String VOLUME = "50";
        // 音调（取值范围0-100）
        public static final String PITCH = "50";
        // 发音人（登陆开放平台https://www.xfyun.cn/后--我的应用（必须为webapi类型应用）--添加在线语音合成（已添加的不用添加）--发音人管理---添加发音人--修改发音人参数）
        public static final String VOICE_NAME = "aisjiuxu";
        // 引擎类型
        public static final String ENGINE_TYPE = "intp65";
        // 文本类型（webapi是单次只支持1000个字节，具体看您的编码格式，计算一下具体支持多少文字）
        public static final String TEXT_TYPE = "text";

        public static Map<String, String> buildHttpHeader(String voiceName) throws UnsupportedEncodingException {
            String param = "{\"auf\":\"" + AUF + "\",\"aue\":\"" + AUE + "\",\"voice_name\":\"" + (StringUtils.isEmpty(voiceName) ? VOICE_NAME : voiceName) + "\",\"speed\":\"" + SPEED + "\",\"volume\":\"" + VOLUME + "\",\"pitch\":\"" + PITCH + "\",\"engine_type\":\"" + ENGINE_TYPE + "\",\"text_type\":\"" + TEXT_TYPE + "\"}";
            return XunfeiUtil.buildHttpHeader(param, TTS_API_KEY);
        }
    }

    /**
     * 讯飞语音评测
     * @param audioBase64 语音的base64
     * @param text 语音对应的实际文本
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getISEInfo(String audioBase64, String text) throws UnsupportedEncodingException {
        Map<String, String> header = ISE.buildHttpHeader();
        String result = HttpUtil.doPost1(ISE.URL, header, "audio=" + URLEncoder.encode(audioBase64, "UTF-8") + "&text=" + URLEncoder.encode(text, "UTF-8"));
        System.out.println("评测 WebAPI 接口调用结果：" + result);
        return result;
    }

    public static String getISEInfo(MultipartFile file, String text) throws Exception {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        return getISEInfo(base64Encoder.encode(file.getBytes()), text);
    }

    /**
     * 语音听写
     * @param audioBase64
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getIATInfo(String audioBase64) throws UnsupportedEncodingException {
        Map<String, String> header = IAT.buildHttpHeader();
        String result = HttpUtil.doPost1(IAT.URL, header, "audio=" + URLEncoder.encode(audioBase64, "UTF-8"));
        System.out.println("听写 WebAPI 接口调用结果：" + result);
        return result;
    }

    public static String getIATInfo(MultipartFile file) throws Exception {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        return getIATInfo(base64Encoder.encode(file.getBytes()));
    }

    /**
     * 语音合成
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getTTSInfo(String text, String voiceName) throws UnsupportedEncodingException {
        Map<String, String> header = TTS.buildHttpHeader(voiceName);
        Map<String, Object> resultMap = HttpUtil.doPost2(TTS.URL, header, "text=" + URLEncoder.encode(text, "utf-8"));
        System.out.println("占用内存大小： "+ URLEncoder.encode(text, "utf-8").getBytes().length);
        String fileName = resultMap.get("sid").toString();
        if ("audio/mpeg".equals(resultMap.get("Content-Type"))) { // 合成成功
            if ("raw".equals(TTS.AUE)) {
                fileName = fileName + ".wav";
            } else {
                fileName = fileName + ".mp3";
            }
            FileUtil.save(ApplicationConst.getTmpAudioPath(), fileName, (byte[]) resultMap.get("body"));
            System.out.println("合成 WebAPI 调用成功，音频文件：" + fileName);
        } else { // 合成失败
            System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());//返回code为错误码时，请查询https://www.xfyun.cn/document/error-code解决方案
        }
        return fileName;
    }


    @Value("${xunfei.appid:}")
    public void setAPPID(String appid) {
        APPID = appid;
    }
    @Value("${xunfei.baseurl:}")
    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
    @Value("${xunfei.ise.apikey:}")
    public void setIseApiKey(String iseApiKey) {
        ISE_API_KEY = iseApiKey;
    }
    @Value("${xunfei.iat.apikey:}")
    public void setIatApiKey(String iatApiKey) {
        IAT_API_KEY = iatApiKey;
    }
    @Value("${xunfei.tts.apikey:}")
    public void setTtsApiKey(String ttsApiKey) {
        TTS_API_KEY = ttsApiKey;
    }
}
