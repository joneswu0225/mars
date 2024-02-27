package com.jones.mars.util;

import com.jones.mars.util.agora.RtcTokenBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jones on 21-12-28.
 */
@Slf4j
@Component
@Profile({"media"})
public class AgoraUtil {
    private static String APP_ID; // = "970CA35de60c44645bbae8a215061b33";
    private static String APP_CERTIFICATE; // = "5CFd2fd1755d40ecb72977518be15d3b";

    private static final int EXPIRATION_TIME_IN_SECONDS = 3600;

    @Value("${agora.rtc.app.id:}")
    public void setAppId(String appId) {
        AgoraUtil.APP_ID = appId;
    }

    @Value("${agora.rtc.app.certificate:}")
    public void setAppCertificate(String appCertificate) {
        AgoraUtil.APP_CERTIFICATE = appCertificate;
    }

    public static Map<String, Object> getRtcToken(String userId, String channelName, RtcTokenBuilder.Role role){
        RtcTokenBuilder tokenBuilder = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + EXPIRATION_TIME_IN_SECONDS);
        int userIdInt = (int)(Integer.parseInt(userId) % Integer.MAX_VALUE);
        String token = tokenBuilder.buildTokenWithUid(APP_ID, APP_CERTIFICATE,
                channelName, userIdInt, role, timestamp);
        Map<String, Object> result = new HashMap<>();
        result.put("appId", APP_ID);
        result.put("token", token);
        result.put("channelName", channelName);
        result.put("userId", userId);
        result.put("role", role.name());
        return result;
    }

    public static void main(String[] args) throws Exception {
        String channelName = "7d72365eb983485397e3e3f9d460bdda";
        String userAccount = "2082341273";
        String uid = "2082341273";
        int uidInt = (int)(Long.parseLong(uid) % Integer.MAX_VALUE);
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + EXPIRATION_TIME_IN_SECONDS);
        String result = token.buildTokenWithUserAccount(APP_ID, APP_CERTIFICATE,
                channelName, userAccount, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        System.out.println(result);

        System.out.println(getRtcToken(uid, channelName,RtcTokenBuilder.Role.Role_Publisher));
        result = token.buildTokenWithUid(APP_ID, APP_CERTIFICATE,
                channelName, uidInt, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        System.out.println(result);
    }
}
