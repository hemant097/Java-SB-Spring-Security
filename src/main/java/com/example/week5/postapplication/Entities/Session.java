package com.example.week5.postapplication.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table( name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsed;

}
