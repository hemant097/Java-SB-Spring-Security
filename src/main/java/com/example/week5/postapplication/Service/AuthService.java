package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.LoginDto;
import com.example.week5.postapplication.Entities.SessionEntity;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Exceptions.SessionAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        User userEntity = (User)authentication.getPrincipal();

        SessionEntity isSessionPresent = sessionService.findSession(userEntity);

        if( isSessionPresent == null ){
            String token =  jwtService.generateToken(userEntity);
            sessionService.createSession(userEntity,token);
            return token;
        }
        else{
            Boolean existingTokenExpired = isSessionPresent.isExpired();

            if(existingTokenExpired){
                String token =  jwtService.generateToken(userEntity);
                sessionService.createSession(userEntity,token);
                return token;
            }
            else
                throw new SessionAlreadyExistsException(userEntity.getEmail());

        }

}}
