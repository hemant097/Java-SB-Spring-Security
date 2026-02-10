package com.example.week5.postapplication.Repository;

import com.example.week5.postapplication.Entities.Session;
import com.example.week5.postapplication.Entities.User;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    @Query("select s from Session s where s.user.id=:id order by s.lastUsed asc")
    List<Session> findSortedSessionsOfAUser(@Param("id") Long userId);

    @Query("select s from Session s where s.user.id=:id")
    List<Session> findSessionByUserId(@Param("id") Long userId);

    Optional<Session> findByRefreshToken(String refreshToken);

}
