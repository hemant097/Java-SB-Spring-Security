package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.Entities.Session;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepo;

    void createNewSession(User user, String refreshToken){

//        SessionEntity session = sessionRepo.findSessionByUserId(user.getId())
//                .orElse(new SessionEntity());
//
//        session.setUser(user);
//        session.setToken(token);
//        session.setExpiresAt(60L);
//        SessionEntity session1 = sessionRepo.save(session);
//
//        System.out.println(session1.getUser().getEmail());

        //either we can get the sorted list from db, or sort the list here, if size has reached max, using a comparator
        List<Session> userSessions = sessionRepo.findSortedSessionsOfAUser(user.getId());
        if(userSessions.size() == user.getPlan().getMaxSessions()){

            Session oldestSession = userSessions.get(0);

            sessionRepo.delete(oldestSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepo.save(newSession);

    }

    void validateSession(String refreshToken){
      Session session =  sessionRepo.findByRefreshToken(refreshToken)
              .orElseThrow(() -> new SessionAuthenticationException(" session not found for refresh Token : "+refreshToken));

      session.setLastUsed(LocalDateTime.now());
    }

    @Transactional
    long deleteSessions(String refreshToken){

        return sessionRepo.deleteByRefreshToken(refreshToken);
    }

    @Transactional
    long deleteAllSessions(Long userId){
        return sessionRepo.deleteByUserId(userId);
    }

}
