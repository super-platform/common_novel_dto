package com.ngtu.api.common.constant;

import org.apache.commons.lang3.StringUtils;

public enum ResponseCode {
    SUCCESSFUL("0", "SUCCESS"),
    FAILED("1", "FAILED"),
    E998("998", "Error"),
    E999("999", "[%s] - Invalid Parameters or values");

    private final String code;
    private final String description;

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ResponseCode fromValue(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }

        for(ResponseCode e: ResponseCode.values()){
            if(code.equalsIgnoreCase(e.code)){
                return e;
            }
        }

        return null;
    }
}
