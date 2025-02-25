package com.muka.petcare.dto.response.add_alert;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAlertResponse {
    @Schema(description = "alert id", example = "1")
    String id;

    @Schema(description = "pet name", example = "pet1")
    String petName;

    @Schema(description = "user name", example = "user1")
    String userName;

    @Schema(description = "alert message", example = "thú cưng đang gần lửa")
    String alertMessage;

    @Schema(description = "pet age", example = "12")
    String location;
}
