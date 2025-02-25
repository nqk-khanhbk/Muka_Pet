package com.muka.petcare.dto.response.add_pet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePetResponse {
    @Schema(description = "pet name", example = "pet1")
    String petName;

    @Schema(description = "pet species", example = "dog")
    String species;

    @Schema(description = "pet breed", example = "bulldog")
    String breed;

    @Schema(description = "pet age", example = "12")
    Integer age;

    @Schema(description = "pet weight", example = "10.2")
    Float weight;

    @Schema(description = "pet picture", example = "/static/images/pet/image_default_pet.jpg")
    String petPicture;
}
