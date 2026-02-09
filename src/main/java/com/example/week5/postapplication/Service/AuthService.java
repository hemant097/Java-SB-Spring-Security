package com.example.week5.postapplication.Service;

import com.example.week5.postapplication.DTO.LoginDto;
import com.example.week5.postapplication.DTO.LoginResponseDto;
import com.example.week5.postapplication.Entities.SessionEntity;
import com.example.week5.postapplication.Entities.User;
import com.example.week5.postapplication.Exceptions.SessionAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        User userEntity = (User)authentication.getPrincipal();

//        SessionEntity isSessionPresent = sessionService.findSession(userEntity);

//        if( isSessionPresent == null ){
            String accessToken =  jwtService.generateAccessToken(userEntity);
            String refreshToken = jwtService.generateRefreshToken(userEntity);

            return new LoginResponseDto(userEntity.getId(), accessToken, refreshToken);

//            sessionService.createSession(userEntity,token);
//            return token;
//        }
//        else{
//            Boolean existingTokenExpired = isSessionPresent.isExpired();
//
//            if(existingTokenExpired){
//                String token =  jwtService.generateToken(userEntity);
//                sessionService.createSession(userEntity,token);
//                return token;
//            }
//            else
//                throw new SessionAlreadyExistsException(userEntity.getEmail());
//
//        }

}
    public LoginResponseDto refresh(String refreshToken){
        //validates the refresh token, finds userId, checks if user is present, generates fresh access token for that
        // user, returns a new login response dto
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);

    }
}
