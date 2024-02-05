package com.jones.mars.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

//@Component
public class IdGenerator {
    private static final String CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final long CODE_LENGTH = CODE.length();
    private static final int CODE_LENGTH_INT = CODE.length();

    private static final String LOCAL_IP = getLocalIp();
    private static final String ENC_IP = getEncIp();
    private static long ENTERPRISE_ID;

    public static String Dec2Enc(Long number, int round){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<round; i++){
            sb.append(CODE.charAt((int) (number % CODE_LENGTH)));
            number /= CODE_LENGTH;
        }
        return sb.reverse().toString();
    }

    public static String Dec2Enc(int number, int round){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<round; i++){
            sb.append(CODE.charAt(number % CODE_LENGTH_INT));
            number /= CODE_LENGTH;
        }
        return sb.reverse().toString();
    }

    public static String getLocalIp() {
        String localIp = "127.0.0.1";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses;
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLinkLocalAddress() && !inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        localIp = inetAddress.getHostAddress();
                        System.out.println("本机IP地址：" + localIp);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return localIp;
    }

    private static String getEncIp(){
        StringBuilder sb = new StringBuilder();
        for (String part : LOCAL_IP.split("\\.")){
            sb.append(Dec2Enc(Integer.valueOf(part), 2));
        }
        return sb.toString();
    }

    public static String getId(Long enterpriseId, Long blockId){
        StringBuilder sb = new StringBuilder();
        sb.append(Dec2Enc(enterpriseId, 3))
                .append(Dec2Enc(blockId, 4))
                .append(ENC_IP)
                .append(Dec2Enc(System.nanoTime(), 8))
            ;
        return sb.toString();
    }

    public static String getId(Long blockId){
        return getId(ENTERPRISE_ID, blockId);
    }

    public static String getId(){
        return getId(0l);
    }

//    @Value("${app.enterprise.id:0}")
    public static void setEnterpriseId(Long enterpriseId) {
        ENTERPRISE_ID=enterpriseId;
    }
//    public static void main(String[] args){
//        Long sourceEnterprise = 11l;
//        Long sourceBlock = 1188l;
//        System.out.println(sourceEnterprise + "-" + Dec2Enc(sourceEnterprise, 3));
//        System.out.println(sourceBlock + "-" + Dec2Enc(sourceBlock, 4));
//        System.out.println(LOCAL_IP + "-" + ENC_IP);
//        long timestamp = System.currentTimeMillis();
//        System.out.println(timestamp + "-" + Dec2Enc(timestamp, 8));
//         timestamp = System.nanoTime();
//        System.out.println(timestamp + "-" + Dec2Enc(timestamp, 8));
//        System.out.println(new Random().nextInt(1));
//        System.out.println(getId(sourceEnterprise, sourceBlock));
//        System.out.println(getId(sourceBlock));
//        System.out.println(getId());
//    }
}
