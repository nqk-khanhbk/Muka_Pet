package com.muka.petcare.dto.request.create_account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "vui lòng điền tên tài khoản")
    @Schema(description = "user name", example = "user1")
    String userName;

    @NotBlank(message = "vui lòng điền họ tên đầy đủ")
    @Schema(description = "user fullname", example = "Phùng Văn A")
    String userFullName;

    @Size(min = 6, message = "mật khẩu tối thiểu phải có 6 ký tự")
    @Schema(description = "user password", example = "123456")
    @NotBlank(message = "vui lòng điền mật khẩu")
    String userPassword;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "vui lòng điền email")
    @Schema(description = "user email", example = "user14@gmail.com")
    String userEmail;

    @NotBlank(message = "vui lòng điền số điện thoại")
    @Schema(description = "user phone", example = "0123456789")
    String userPhone;

    @NotBlank(message = "vui lòng điền vai trò của bạn")
    @Schema(description = "user role", example = "CUSTOMER")
    String role;
}
