package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pets")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends  BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    List<Alert> alerts;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    List<Prediction> predictions;

    @Column(name = "pet_name", nullable = false)
    String petName;

    @Column(name = "pet_species", nullable = false)
    String species;

    @Column(name = "pet_breed")
    String breed;

    @Column(name = "pet_age")
    Integer age;

    @Column(name = "pet_weight")
    Float weight;

    @Column(name = "pet_picture")
    String petPicture;

    @Column(name = "active", nullable = false)
    Boolean active;
}
