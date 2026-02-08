package com.example.week5.postapplication.Repository;

import com.example.week5.postapplication.Entities.SessionEntity;
import com.example.week5.postapplication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

    @Query("select s from SessionEntity s where s.user.id=:id")
    Optional<SessionEntity> findSessionByUserId(@Param("id") Long userId);

    @Query("select s.expiresAt from SessionEntity s where s.user.id=:id")
    Optional<LocalDateTime> findExpirationTimeByUserId(@Param("id") Long userId);
}
