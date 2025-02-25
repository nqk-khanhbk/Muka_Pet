package com.muka.petcare.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPermission {
    READ_USER("READ_USER"),
    CREATE_USER("CREATE_USER"),
    UPDATE_USER("UPDATE_USER"),
    DELETE_USER("DELETE_USER"),
    VIEW_PET("VIEW_PET"),
    MANAGE_PET("MANAGE_PET");

    private final String permissionName;
}
