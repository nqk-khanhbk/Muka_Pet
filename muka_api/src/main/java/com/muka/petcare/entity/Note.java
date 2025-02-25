package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(name = "notes")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Note extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name= "status", nullable = false)
    String status; // pending done

    @Column(name = "content", nullable = false)
    String content;
}
