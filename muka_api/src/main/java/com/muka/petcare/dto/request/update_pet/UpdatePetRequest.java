package com.muka.petcare.dto.request.update_pet;

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
public class UpdatePetRequest {
    @NotBlank(message = "vui lòng điền tên thú cưng")
    @Schema(description = "pet name", example = "pet1")
    String petName;

    @NotBlank(message = "vui lòng điền loài của thú cưng")
    @Schema(description = "pet species", example = "dog")
    String species;

    @NotBlank(message = "vui lòng điền giống của thú cưng")
    @Schema(description = "pet breed", example = "bulldog")
    String breed;

    @NotNull(message = "vui lòng điền tuổi của thú cưng (tháng)")
    @Schema(description = "pet age", example = "12")
    Integer age;

    @NotNull(message = "vui lòng điền cân nặng của thú cưng(kg)")
    @Schema(description = "pet weight", example = "10.2")
    Float weight;
}
