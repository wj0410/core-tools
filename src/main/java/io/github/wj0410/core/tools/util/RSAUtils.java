package io.github.wj0410.core.tools.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密
 * 数字签名：私钥加密，公钥解密
 */
public class RSAUtils {
    //使用公钥进行加密
    public static String encryptByPbKey(String content, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA"); //java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        return encoder.encodeToString(cipher.doFinal(content.getBytes()));
    }
    // 使用私钥进行解密
    public static String decryptByPvKey(String ctext, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(ctext.getBytes("UTF-8"));
        //base64编码的私钥
        RSAPrivateKey priKey = getPrivateKey(privateKey);
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

    // 使用私钥进行加密
    public static String encryptByPvKey(String content, String privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA"); //java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        return encoder.encodeToString(cipher.doFinal(content.getBytes()));
    }
    // 使用公钥进行解密
    public static String decryptByPbKey(String ctext, String publicKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(ctext.getBytes("UTF-8"));
        // base64编码的私钥
        PublicKey pubKey = getPublicKey(publicKey);
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return new String(cipher.doFinal(inputByte));
    }

    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pbKey = keyFactory.generatePublic(keySpec);
        return pbKey;
    }

    private static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        RSAPrivateKey pvKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        return pvKey;
    }

    /**
     * RSA：随机生成密钥对
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static void genkeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-2048位
        keyPairGen.initialize(2048, new SecureRandom());
        //生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
        // 得到公钥字符串
        String publicKeyStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("随机生成的公钥为：" + publicKeyStr);
        System.out.println("随机生成的私钥为：" + privateKeyStr);
    }
}
