package com.ngtu.api.common.controllers;

import com.ngtu.api.common.entities.BaseEntity;
import com.ngtu.api.common.dto.QueryBuilder;
import com.ngtu.api.common.dto.response.ResponseWithBody;
import com.ngtu.api.common.services.GenericService;
import com.ngtu.api.common.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import static com.ngtu.api.common.constant.CommonConstants.TRACE_ID;

@Slf4j
public class BaseController<T extends BaseEntity,  ID> {

    private GenericService<T, ID> genericService;

    public BaseController(GenericService<T, ID> genericService) {
        this.genericService = genericService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indicates that the create data request was executed successfully"),
            @ApiResponse(responseCode = "500", description = "Indicates that there is an error occurs when executing create data ")
    })
    @PostMapping(value = "/create", consumes = "application/json",produces = "application/json")
    public ResponseEntity<ResponseWithBody<T>> create(@RequestBody T t ){
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        // Initialize result
        ResponseWithBody<T> result = new ResponseWithBody<>();
        result.setStatus(ResponseUtil.createSuccessStatus());

        // Execute query
        T entity = genericService.create(t);
        result.setBody(entity);

        MDC.clear();
        return ResponseEntity.ok(result);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Indicates that the query request was executed successfully"),
        @ApiResponse(responseCode = "500", description = "Indicates that there is an error occurs when executing query request")
    })
    @PostMapping(value = "/query", consumes = "application/json",produces = "application/json")
    public ResponseEntity<ResponseWithBody<List<T>>> query(@RequestBody QueryBuilder queryBuilder){
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        // Initialize result
        ResponseWithBody<List<T>> result = new ResponseWithBody<>();
        result.setStatus(ResponseUtil.createSuccessStatus());

        // Execute query
        List<T> listT = genericService.query(queryBuilder);
        result.setBody(listT);

        MDC.clear();
        return ResponseEntity.ok(result);
    }
}
