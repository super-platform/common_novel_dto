package com.ngtu.api.common.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageableRequest {
    private Integer start = 0;
    private Integer size = 20;
    private List<String> sorts = new ArrayList<>();
    private String order = "ASC";
}
