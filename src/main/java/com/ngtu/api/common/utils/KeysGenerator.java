package com.ngtu.api.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Slf4j
public class KeysGenerator {
    private static final String algorithm = "HmacSHA256";

    public static String generateKey(String path) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(4096);

        SecretKey secretKey = keyGenerator.generateKey();
        String publicKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        if (StringUtils.isNotBlank(path)){
            writeToFile(publicKey,path);
        }
        return publicKey;
    }

    public static List<String> generatePairKey(String path) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(4096);

        KeyPair pair = keyGen.generateKeyPair();
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

        if(StringUtils.isNotBlank(path)){
            writeToFile(privateKey,   path + "_privateKey.txt");
            writeToFile(publicKey, path + "_publicKey.txt");
        }
        return Arrays.asList(privateKey, publicKey);
    }

    public static void writeToFile(String key, String path){
        try {
            String absolutePath = System.getProperty("user.dir") + "/" + path;
            File file = new File(absolutePath);
            if(file != null && file.getParentFile() != null){
                file.getParentFile().mkdirs();

                FileWriter myWriter = new FileWriter(absolutePath);
                myWriter.write(key);
                myWriter.close();
            }
        } catch (IOException e){
            log.error("IO exception: {}", e.getMessage());
        }
    }
}
