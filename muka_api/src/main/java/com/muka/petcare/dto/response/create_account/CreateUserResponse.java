package com.muka.petcare.dto.response.create_account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserResponse {
    @Schema(description = "user id", example = "1")
    String userId;

    @Schema(description = "user name", example = "user1")
    String userName;

    @Schema(description = "user fullname", example = "Phùng Văn A")
    String userFullName;

    @Schema(description = "user email", example = "user1@gmail.com")
    String userEmail;

    @Schema(description = "user phone", example = "0123456789")
    String userPhone;

    @Schema(description = "user profile picture", example = "/static/images/user/profile/profile_default.png")
    String profilePicture;

    @Schema(description = "user roles and permissions", example = "ROLE_CUSTOMER UPDATE_USER")
    Set<String> rolesAndPermissions;
}
