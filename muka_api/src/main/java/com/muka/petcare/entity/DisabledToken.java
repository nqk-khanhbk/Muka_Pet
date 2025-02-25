package com.muka.petcare.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "disabled_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisabledToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "token_id", unique = true)
    String tokenId;

    @Column(name = "expiration_time")
    LocalDateTime expirationTime;

    @CreationTimestamp
    @Column(name = "date_added")
    LocalDateTime dateAdded;
}
