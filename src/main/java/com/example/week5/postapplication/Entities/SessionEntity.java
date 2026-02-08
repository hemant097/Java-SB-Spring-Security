package com.example.week5.postapplication.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table( name = "session")
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @OneToOne
    @JoinColumn(name = "u_id",unique = true)
    private User user;
    private String token;

    private LocalDateTime expiresAt;

    public void setExpiresAt(Long seconds){
        this.expiresAt=LocalDateTime.now().plusSeconds(seconds);
    }

    public Boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }


}
