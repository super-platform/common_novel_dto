package com.ngtu.api.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryBuilder {
    @NotBlank(message = "operator is mandatory")
    private String operator;

    @NotBlank(message = "key is mandatory")
    private String key;

    @NotEmpty(message = "values is mandatory")
    private List<?> values;

    private List<QueryBuilder> combines;
}
