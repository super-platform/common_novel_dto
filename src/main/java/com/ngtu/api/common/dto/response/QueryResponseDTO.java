package com.ngtu.api.common.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryResponseDTO<T> {
    private Integer start;
    private Integer size;
    private Long total;
    private List<String> sorts = new ArrayList<>();
    private String order;
    private List<T> data = new ArrayList<>();
}
