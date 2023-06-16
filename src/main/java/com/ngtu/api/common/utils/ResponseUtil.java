package com.ngtu.api.common.utils;

import com.ngtu.api.common.constant.ResponseCode;
import com.ngtu.api.common.dto.response.ErrorCode;
import com.ngtu.api.common.dto.response.ResponseStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ResponseUtil {
    public static ResponseStatus createSuccessStatus(){
        ResponseStatus status = new ResponseStatus();
        status.setCode(Integer.valueOf(ResponseCode.SUCCESSFUL.getCode()));
        status.setMessage(ResponseCode.SUCCESSFUL.getDescription());
        return status;
    }
    public static ResponseStatus createFailedStatus(){
        ResponseStatus status = new ResponseStatus();
        status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
        status.setMessage(ResponseCode.FAILED.getDescription());
        return status;
    }

    public static ResponseStatus createResponseStatusFromErrorList(List<ErrorCode> errors){
        ResponseStatus status = null;
        if(!CollectionUtils.isEmpty(errors)){
            status = createFailedStatus();
            status.setErrors(errors);
        }

        return status;
    }

    public static ResponseStatus createErrorStatusByResponseCode(ResponseCode responseCode){
        ResponseStatus status = createFailedStatus();
        status.setErrors(Arrays.asList(createErrorCode(responseCode)));

        return status;
    }

    public static ErrorCode createErrorCode(ResponseCode responseCode ){
        ErrorCode errorCode = new ErrorCode();
        errorCode.setCode(responseCode.getCode());
        errorCode.setMessage(responseCode.getDescription());

        return errorCode;
    }

    public static ResponseStatus createFailedInvalidInputStatus(ResponseCode responseCode, Set<String> errorFields){
        ResponseStatus status = createFailedStatus();

        List<ErrorCode> errorCodes = new ArrayList<ErrorCode>();

        for(String errorField: errorFields){
            ErrorCode errorCode = new ErrorCode();
            errorCode.setCode(responseCode.getCode());
            errorCode.setMessage(String.format(responseCode.getDescription(), errorField));
            errorCodes.add(errorCode);
        }
        status.setErrors(errorCodes);

        return status;
    }

    public static ResponseStatus createFailedInvalidInputStatusWithSet(ResponseCode responseCode, Set<String> fields){
        ResponseStatus status = new ResponseStatus();
        status.setCode(Integer.valueOf(ResponseCode.FAILED.getCode()));
        status.setMessage(ResponseCode.FAILED.getDescription());

        // Set errorCode
        List<ErrorCode> errorCodes = new ArrayList<>();
        for (String field: fields){
            ErrorCode errorCode = new ErrorCode();
            errorCode.setCode(responseCode.getCode());
            errorCode.setMessage(String.format(responseCode.getDescription(),field));
            errorCodes.add(errorCode);
        }

        status.setErrors(errorCodes);
        return status;
    }

}

