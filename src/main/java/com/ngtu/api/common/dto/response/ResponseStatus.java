package com.ngtu.api.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ResponseStatus {
    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private Integer code = null;
    @JsonProperty("message")
    private String message = null;
    @JsonProperty("errors")
    private List<ErrorCode> errors = null;
}
