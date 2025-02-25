package com.muka.petcare.dto.request.add_alert;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAlertRequest {
    @NotBlank(message = "vui lòng điền tên thú cưng")
    @Schema(description = "pet name", example = "pet1")
    String petName;

    @NotBlank(message = "thiếu thông tin chủ nuôi")
    @Schema(description = "user name", example = "user1")
    String userName;

    @NotBlank(message = "vui lòng điền thông tin cảnh báo")
    @Schema(description = "alert message", example = "thú cưng đang gần lửa")
    String alertMessage;

    @Schema(description = "pet age", example = "12")
    String location;
}
