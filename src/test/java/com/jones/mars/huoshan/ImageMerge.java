package com.jones.mars.huoshan;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by jones on 22-3-15.
 */
public class ImageMerge {
    public static final String BASE_URL = "https://visual.volcengineapi.com?Action=%s&Version=%s";
//    public static final String AccessKeyId = "AKLTNWIyYTZmMGFkNzg3NGYxNDg5OGM4ZTJjNzM3NDczYjc";
//    public static final String AccessKeySecret = "T0dFNU16VXhaVEkzWkdJNU5EWTVabUptT1dVek1tTXdNMk0zTURZMU0ySQ==";
    public static final String AccessKeyId = "AKLTMjI2ODVlYzI3ZGY1NGU4ZjhjYWRjMTlmNTM5OTZkYzE";
    public static final String AccessKeySecret = "TnpCak5XWXpZV1U0WkRaaE5ERmxaR0ZpTmpjeVkyUXlZek0wTWpJMU1qWQ====";

    public static final String SERVICE_IMAGE = "cv";
    public static final String REGION_CN = "cn-north-1";


    public static String URL_FACE_SWAP = String.format(BASE_URL, "FaceSwap", "2020-08-26");

    private static String getAuthorization(){
        String xdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

        String authorization = new StringBuilder("HMAC-SHA256 Credential=").append(AccessKeyId).append("/").append(xdate.substring(0,6))
                .append("/").append(REGION_CN).append("/").append(SERVICE_IMAGE).append("/request").toString();

        return null;
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String HTTPRequestMethod = "GET";
        String CanonicalURI = "/";
        String CanonicalQueryString = "Action=ListUsers&Limit=10&Offset=0&Version=2018-01-01";
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("Host", "iam.volcenggineapi.com");
        String content = "";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String xContent = Hex.encodeHexString(messageDigest.digest(content.getBytes("UTF-8")));
        header.put("x-content-sha256", xContent);
        String xdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        header.put("x-date", xdate);

        Set<String> keySet = new HashSet<>(header.keySet());
        for(String key : keySet){
            if(!key.equals(key.toLowerCase())){
                header.put(key.toLowerCase(), header.get(key));
                header.remove(key);
            }
        }
//        List<String> list = Collections.singletonList(header.keySet());
        Set sorted = (SortedSet)header.keySet();
        System.out.println("");
    }
}
