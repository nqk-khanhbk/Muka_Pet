package com.muka.petcare.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {
    @NotBlank(message = "vui lòng cung cấp accessToken")
    String accessToken;
    @NotBlank(message = "vui lòng cung cấp refreshToken")
    String refreshToken;
}
