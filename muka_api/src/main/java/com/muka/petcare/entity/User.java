package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Column(name = "user_name", nullable = false, unique = true)
    String userName;

    @Column(name = "user_fullname", nullable = false)
    String userFullName;

    @Column(name = "user_password", nullable = false)
    String userPassword;

    @Column(name = "user_email", nullable = false, unique = true)
    String userEmail;

    @Column(name = "user_phone", nullable = false, unique = true)
    String userPhone;

    @Column(name = "profile_picture", nullable = false)
    String profilePicture;

    @Column(name = "active", nullable = false)
    Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Alert> alerts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Pet> pets;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private Set<Role> roles = new HashSet<>();
}
