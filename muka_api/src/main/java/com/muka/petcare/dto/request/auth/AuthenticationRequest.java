package com.muka.petcare.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "vui lòng nhập tên tài khoản hoặc email")
    String userName;
    @NotBlank(message = "vui lòng nhập mật khẩu")
    String userPassword;
}
