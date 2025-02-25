package com.muka.petcare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusDB {
    READ("READ"),
    UNREAD("UNREAD"),

    ;

    private final String typeStatus;
}
