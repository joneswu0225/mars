package com.jones.mars.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RsaUtil {

    public static void main(String[] args) {
//        String a = "107518948843387122581837547862334956766398643634224765846833053297479518038290466911985468642264457995795178764054747478660499243415642807814561644443887560582541614836246093676657208189183638428849992961411844888496881093249797565126339593783344338858081523128943367553788193576959456818670301051015039385909";
//        byte[] d = a.getBytes();
//        System.out.println(123);
        //解密数据
        try {
            //生成公钥和私钥
            genKeyPair();
            String publicKey = keyMap.get(0);
            System.out.println("公钥:" + publicKey);
            String privateKey = keyMap.get(1);
            System.out.println("私钥:" + privateKey);

            String orgData = "fe80::c48c:3345:3fc7:33e3%14;./tutor;2024-01-01;2025-01-01";
            System.out.println("原数据：" + orgData);
            String encryptStr =encrypt(orgData,publicKey);
            System.out.println("加密结果：" + encryptStr);

            String decryptStr = decrypt(encryptStr,privateKey);
            System.out.println("解密结果：" + decryptStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str,String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = decryptBASE64(publicKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = encryptBASE64(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = decryptBASE64(str);
        //base64编码的私钥
        byte[] decoded = decryptBASE64(privateKey);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    /**
     * 用于封装随机产生的公钥与私钥
     */
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    /**
     * 随机生成密钥对
     * @throws Exception
     */
    public static Map<Integer, String> genKeyPair() throws Exception {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = encryptBASE64(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = encryptBASE64(privateKey.getEncoded());
        // 将公钥和私钥保存到Map
        //0表示公钥
        keyMap.put(0, publicKeyString);
        //1表示私钥
        keyMap.put(1, privateKeyString);
        return keyMap;
    }
}
