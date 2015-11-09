package com.wrmoney.administrator.plusadd.tools;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
//import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Created by Administrator on 2015/9/7.
 */
public class DES3Util {
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";
    // 密钥
    private static String secretKey = "_WRCF&app@3Des$Key%[28]!";
    // 向量
    private static String iv = "20100819";

//    static {
//        try {
//            Proxperties properties = PropertiesLoaderUtils.loadAllProperties("DES3Key.properties");
//            secretKey = properties.getProperty("SECRET_KEY");
//            iv = properties.getProperty("IV");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));

        return new String(decryptData, encoding);
    }

    public static void main(String[] args) {
        try {
//    		String content = "内容：（add）加号（equal）等号（and）and（percent）百分号";
            String enStr = "{\"work\":\"1\",\"hasEstate\":\"1\",\"isMarriage\":\"1\",\"userId\":\"1\",\"hasCar\":\"1\"}";
            System.out.println(">>>>>>>>加密前：" + enStr);

            String result = encode(enStr);
            System.out.println(">>>>>>>>加密后：" + result);


            String src = result;
            System.out.println(">>>>>>>>解密前：" + src);

            System.out.println(">>>>>>>>解密后：" + java.net.URLDecoder.decode(decode(src), "UTF-8"));
            //System.out.println(">>>>>>>>解密后：" + decode("NWROvkiGvh4tHUH6XVXL5N=="));
        } catch (Exception e) {
        }
    }
}
