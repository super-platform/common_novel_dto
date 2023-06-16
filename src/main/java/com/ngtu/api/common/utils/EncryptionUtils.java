package com.ngtu.api.common.utils;

import com.google.gson.Gson;
import com.ngtu.api.common.dto.EncryptDTO;
import com.ngtu.api.common.dto.response.ResponseStatus;
import com.ngtu.api.common.dto.response.ResponseWithBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class EncryptionUtils {
    @Value("${ENCRYPTION_AES256_SECRET_KEY}")
    protected String secretKey;

    @Value("${ENCRYPTION_AES256_SALT_KEY}")
    protected String saltKey;

    protected ResponseWithBody<EncryptDTO> encryptResult(Object object, ResponseStatus status){
        // Initial response
        ResponseWithBody<EncryptDTO> response = new ResponseWithBody<>();

        // Generate ivString
        String ivString = AESUtil.generateIvString();

        if(ObjectUtils.isNotEmpty(object)){
            String encrptedString = encryptObject(object, ivString);
            response.setBody(new EncryptDTO(ivString, encrptedString));
        }
        response.setStatus(status);

        return response;
    }

    protected String encryptObject(Object object, String ivString){
        return AESUtil.encryptString(new Gson().toJson(object), secretKey, saltKey, ivString);
    }

    protected String decryptObject(String encryptString, String ivString){
        return AESUtil.decryptString(encryptString, secretKey, saltKey, ivString);
    }
}
