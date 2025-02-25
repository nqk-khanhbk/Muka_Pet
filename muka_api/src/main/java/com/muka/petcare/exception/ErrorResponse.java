package com.muka.petcare.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ErrorResponse {
    @Schema(description = "Timestamp of the error response", example = "2024-09-09T12:34:56.789")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Path where the error occurred", example = "/api/v1/users")
    private String path;

    @Schema(description = "Error code", example = "1001")
    private int errorCode;

    @Schema(description = "Error message", example = "Username existed")
    private String message;
}
