package com.platform.common.config;

import com.platform.common.constant.ResponseCode;
import com.platform.common.dto.response.ResponseStatus;
import com.platform.common.dto.response.ResponseWithBody;
import com.platform.common.exception.ResourceNotFoundException;
import com.platform.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseWithBody<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        ResponseStatus status = ResponseUtil.createErrorStatusByResponseCode(ResponseCode.E404);

        return new ResponseWithBody<>(null, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseWithBody<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ResponseStatus status = ResponseUtil.createErrorStatusByResponseCode(ResponseCode.E500);

        return new ResponseWithBody<>(null, status);
    }
}
