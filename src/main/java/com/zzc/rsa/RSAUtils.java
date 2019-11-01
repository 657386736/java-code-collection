package com.zzc.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: [ zhongzc ]
 * @Date: 2019/10/8 13:34
 * @Description: [ ]
 * @Version: [ v1.0.0 ]
 * @Copy: 蓝鲸智数科技有限公司
 */
public class RSAUtils {


    public static void generateKey() {
        try {
            // 基于RSA算法生成对象
            KeyPairGenerator keyPairGen;
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(512);  // 最小值是512，一般设置值为1024，该值越大加密信息越安全，对计算机消耗也越大
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到私钥 RSAPrivateKey
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //得到公钥 RSAPublicKey
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            System.out.println("私钥：" + Base64.encodeBase64String(privateKey.getEncoded()));
            System.out.println("公钥：" + Base64.encodeBase64String(publicKey.getEncoded()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //简化版
    public static String encrypt(String publicKeyStr, String text) throws Exception {
        String result = "";

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr)));

        byte[] resultBytes = null;

        if (publicKey != null) {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            resultBytes = cipher.doFinal(Base64.decodeBase64(text));
        }

        if (null != resultBytes) {
            result = Base64.encodeBase64String(resultBytes);
        }

        return result;
    }

    //简化版
    public static String decrypt(String privateKeyStr, String encrytedStr) throws Exception {
        String result = null;

        byte[] keyBytes = Base64.decodeBase64(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] resultBytes = cipher.doFinal(Base64.decodeBase64((encrytedStr)));
        result = Base64.encodeBase64String(resultBytes);

        return result;
    }

    /**
     * @Description 根据字符串生产公钥
     * @return java.security.PublicKey
     * @throws @Author zhanglei
     * @Date 14:25 2019/4/29
     * @Param [key]
     **/
    public static PublicKey getPublicKey(String publicKeys) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(publicKeys);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * String转私钥PrivateKey
     * @param
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeys) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(privateKeys);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        if (publicKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }


    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey != null) {
            // Cipher负责完成加密或解密工作，基于RSA
            Cipher cipher = Cipher.getInstance("RSA");
            // 根据公钥，对Cipher对象进行初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(srcBytes);
            return resultBytes;
        }
        return null;
    }


    /**
     * Sring转byte数组
     */
    public static byte[] stringToByte(String msg) {
        byte[] msgByte = Base64.decodeBase64(msg);
        return msgByte;
    }

    /**
     * byte数组转字符串
     */
    public static String byteToString(byte[] bytes) {
        String msg = Base64.encodeBase64String(bytes);
        return msg;
    }

}
