package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.Entities.SessionEntity;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepo;

    void createSession(User user, String token){

        SessionEntity session = sessionRepo.findSessionByUserId(user.getId())
                .orElse(new SessionEntity());

        session.setUser(user);
        session.setToken(token);
        session.setExpiresAt(60L);
        SessionEntity session1 = sessionRepo.save(session);

        System.out.println(session1.getUser().getEmail());

    }

    SessionEntity findSession(User user){
      return sessionRepo.findSessionByUserId(user.getId()).orElse(null);

    }

}
