package com.platform.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String group; // and or or
    private String field;
    private List<String> values;
    private String operator; // equa, not euqa, like, tl
    private List<Filter> rules;
}