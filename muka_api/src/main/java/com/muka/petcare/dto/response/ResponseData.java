package com.muka.petcare.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Setter
@Getter
public class ResponseData<T> {
    @Schema(description = "Timestamp of the response", example = "2024-09-09T12:34:56.789")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "202")
    private int status;

    @Schema(description = "Response message", example = "Request successful")
    private String message;

    @Schema(description = "Data response")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
