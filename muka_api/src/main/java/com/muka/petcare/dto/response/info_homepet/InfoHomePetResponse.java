package com.muka.petcare.dto.response.info_homepet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoHomePetResponse {
    @Schema(description = "pet id", example = "1")
    String id;

    @Schema(description = "pet name", example = "pet1")
    String petName;

    @Schema(description = "pet picture", example = "/static/images/pet/image_default_pet.jpg")
    String petPicture;
}
