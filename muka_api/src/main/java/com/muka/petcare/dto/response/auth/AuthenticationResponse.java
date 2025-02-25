package com.muka.petcare.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    @Schema(description = "token access", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzaG9wY2VudGVyLmNvbSIsInN1YiI6InVzZXIxIiwiZXhwIjoxNzI2NDQ3NDUyLCJpYXQiOjE3MjY0NDY3NTIsImp0aSI6IjYzMzNhM2VlLTFiMjQtNDFmYy05MDU0LTZjNjc1N2QwODNkMyIsInNjb3BlIjoiIn0.kBrDO07iNZZ-qSfzAy3iPEssscXbrA6DZqQX6z2-Fwq-PCZiavXCb7sA_571b6Us3sMN9WxVyJ97jOXSD6eHgQ")
    String accessToken;

    @Schema(description = "refresh access", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzaG9wY2VudGVyLmNvbSIsInN1YiI6InVzZXIxIiwiZXhwIjoxNzI2NDQ3NDUyLCJpYXQiOjE3MjY0NDY3NTIsImp0aSI6IjYzMzNhM2VlLTFiMjQtNDFmYy05MDU0LTZjNjc1N2QwODNkMyIsInNjb3BlIjoiIn0.kBrDO07iNZZ-qSfzAy3iPEssscXbrA6DZqQX6z2-Fwq-PCZiavXCb7sA_571b6Us3sMN9WxVyJ97jOXSD6eHgQ")
    String refreshToken;
}
