package com.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class HTTPComponent {
    public static final String HTTP_COMPONENT = "HTTP-COMPONENT";

    @Autowired
    private RestTemplate restTemplateAPI;

    public Map<String, Object> executeAPI(String url, HttpMethod method, HttpHeaders headers, Object requestObj){
        Map<String, Object> body = CommonUtils.objToMap(requestObj);

        Map<String, Object> mapResponse = null;
        try {
            HttpEntity<Map> httpEntity = new HttpEntity<Map>(body, headers);
            log.info("{} - Process ExecuteAPI - url: {} - method: {} - body: {}", HTTP_COMPONENT, url,method, body);
            ResponseEntity<Map> response = restTemplateAPI.exchange(url, method, httpEntity, Map.class, Collections.EMPTY_MAP);

            if(!ObjectUtils.isEmpty(response) && response.getStatusCodeValue() == 200 && !ObjectUtils.isEmpty(response.getBody())){
                log.info("ExecuteAPI successfully with responseEntity has body ");
                mapResponse = response.getBody();
            }
        } catch (HttpServerErrorException ex){
            log.error("{} - Error process executeAPI - responseBody: {}", HTTP_COMPONENT, ex.getMessage());
        } catch (Exception e){
            log.error("Server error: {}", e.getMessage());
        }

        return mapResponse;
    }
}
