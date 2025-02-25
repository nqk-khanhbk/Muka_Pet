package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @Column(name = "permission_name")
    String permissionName;

    @Column(name = "permission_desc")
    String permissionDesc;

    @ManyToMany(mappedBy = "permissions")
    Set<Role> roles = new HashSet<>();
}
