package com.platform.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "responseWithBody", description = "base response dto")
@Builder
public class ResponseWithBody<T> {
    @Schema(name = "body", description = "Body payload")
    private T body;

    private ResponseStatus status;
}
