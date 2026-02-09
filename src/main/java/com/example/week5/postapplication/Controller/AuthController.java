package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.LoginDto;
import com.example.week5.postapplication.DTO.LoginResponseDto;
import com.example.week5.postapplication.DTO.SignupDto;
import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Service.AuthService;
import com.example.week5.postapplication.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signUpMethod(@Valid @RequestBody SignupDto signupDto){

        UserDto userDto = userService.signUp(signupDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDto> loginMethod(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
                                               HttpServletResponse response){

        LoginResponseDto logined =  authService.login(loginDto);

        Cookie cookie  = new Cookie("refreshToken", logined.getRefreshToken());
        cookie.setHttpOnly(true);
//        setSecure must only be used when using https
        cookie.setSecure("production".equals(deployEnv));
//        cookie.setPath("/auth/"); this will add the cookie automatically in further requests
        response.addCookie(cookie);


        return ResponseEntity.ok(logined);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refreshMethod(HttpServletRequest request){

       String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(()-> new AuthenticationServiceException("refresh token not found inside cookies"));

        LoginResponseDto loginResponseDto = authService.refresh(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }
}
