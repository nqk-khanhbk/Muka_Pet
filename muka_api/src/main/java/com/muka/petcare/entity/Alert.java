package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert{
    @ManyToOne
    @JoinColumn(name = "pet_id")
    Pet pet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "alert_message", nullable = false)
    String alertMessage;

    @Column(name = "status", nullable = false)
    String status;  // unread or read

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;

    @Column(name = "location")
    String location;
}
