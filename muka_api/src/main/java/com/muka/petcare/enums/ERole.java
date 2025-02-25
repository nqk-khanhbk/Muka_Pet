package com.muka.petcare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERole {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN"),
    VETERINARIAN("VETERINARIAN");

    private final String roleName;
}
