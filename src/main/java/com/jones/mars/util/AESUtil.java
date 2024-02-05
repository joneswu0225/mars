package com.jones.mars.util;

import com.jones.mars.model.DeployLicense;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//引入第三方包

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.StringJoiner;

/**
 * Created by tao.
 * Date: 2021/6/15 9:37
 * 描述:
 */
public class AESUtil {

    //--------------AES---------------
    private static final String OFFSET = "abcdefgh12345678"; // 偏移量
    private static final String ENCODING = "UTF-8"; // 编码
    private static final String ALGORITHM = "AES"; //算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // 默认的加密算法，CBC模式

    public static String AESencrypt(String key, String data) throws Exception {
        //指定算法、获取Cipher对象(DES/CBC/PKCS5Padding：算法为，工作模式，填充模式)
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //根据自定义的加密密匙和算法模式初始化密钥规范
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), ALGORITHM);
        //CBC模式偏移量IV
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
        //初始化加密模式
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        //单部分加密结束，重置Cipher
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        //加密后再使用BASE64做转码
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * AES解密
     *
     * @param data
     * @return String
     * @author tao
     * @date 2021-6-15 16:46:07
     */
    public static String AESdecrypt(String key, String data) throws Exception {
        //指定算法、获取Cipher对象(DES/CBC/PKCS5Padding：算法为，工作模式，填充模式)
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //根据自定义的加密密匙和算法模式初始化密钥规范
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ASCII"), ALGORITHM);
        //CBC模式偏移量IV
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
//        iv = null;
        //初始化解密模式
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        //先用base64解码
        byte[] buffer = Base64.getDecoder().decode(data);
        //单部分加密结束，重置Cipher
        byte[] encrypted = cipher.doFinal(buffer);
        return new String(encrypted, ENCODING);
    }

    public static void main(String[] args) throws Exception {
        String originalText = "4CDA-39AE;.;2024-01-01;2025-01-01"; // 要加密的原始文本
        String diskSeries = "4CDA-39AE";
        String diskSeries2 = "4CDA-39A3";
        String diskId = "81711104";
        Integer disk = Integer.valueOf(diskId.substring(diskId.length() - 8));
        String key = String.format("%08d", disk) + diskSeries.replace("-","");
        String key2 = String.format("%08d", disk) + diskSeries2.replace("-","");

        System.out.println("加密前：" + originalText);
        String encrypted = AESUtil.AESencrypt(key, originalText);
        System.out.println("加密后：" + encrypted);
        System.out.println("解密后：" + AESUtil.AESdecrypt(key, encrypted));
//        System.out.println("解密后：" + AESUtil.AESdecrypt(key2, encrypted));
    }
}