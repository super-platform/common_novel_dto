package com.platform.common.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableRequest {
    private int page = 1;
    private int size = 20;
    private String search;
    private List<OrderBy> orderBys;
    private Filter filter;
}
