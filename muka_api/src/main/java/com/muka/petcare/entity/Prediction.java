package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "predictions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    Pet pet;

    @Column(name = "prediction_type", nullable = false)
    String predictionType; //sick

    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;
}
