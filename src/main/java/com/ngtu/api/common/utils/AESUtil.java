package com.ngtu.api.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
public class AESUtil {
     private static final String algorithm = "AES/CBC/PKCS5Padding";
     private static final String SHA256KeyAlgorithm = "PBKDF2WithHmacSHA256";
     private static final Integer ivLength = 16;

     public static String encryptString(String plainText, String secretKey, String saltKey, String ivString){
         try {
             Cipher cipher = Cipher.getInstance(algorithm);
             cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey,saltKey), generateIv(ivString));
             return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
         } catch (Exception e){
             log.error("Error encrypt: {}", e.getMessage());
             return null;
         }
     }

     public static String decryptString(String cipherText, String secretKey, String saltKey, String ivString){
         try {
             Cipher cipher = Cipher.getInstance(algorithm);
             cipher.init(Cipher.DECRYPT_MODE, getSecretKey(secretKey,saltKey), generateIv(ivString));
             return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
         } catch (Exception e){
             log.error("Error decrypt: {}", e.getMessage());
            return null;
         }
     }

     public static String generateIvString(){
         return RandomStringUtils.randomAlphabetic(ivLength);
     }

     public  static IvParameterSpec generateIv(String iv) throws UnsupportedEncodingException {
         return new IvParameterSpec(iv.getBytes("UTF-8"));
     }
     public static SecretKey getSecretKey(String secretKey, String saltKey) throws Exception {
         SecretKeyFactory factory = SecretKeyFactory.getInstance(SHA256KeyAlgorithm);
         KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), saltKey.getBytes(), 65536, 256);
         return new SecretKeySpec(factory.generateSecret(spec).getEncoded(),"AES");
     }
}
