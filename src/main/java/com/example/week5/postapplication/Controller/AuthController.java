package com.example.week5.postapplication.Controller;

import com.example.week5.postapplication.DTO.LoginDto;
import com.example.week5.postapplication.DTO.SignupDto;
import com.example.week5.postapplication.DTO.UserDto;
import com.example.week5.postapplication.Service.AuthService;
import com.example.week5.postapplication.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signUpMethod(@Valid @RequestBody SignupDto signupDto){

        UserDto userDto = userService.signUp(signupDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> signUpMethod(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request,
                                               HttpServletResponse response){

        String token =  authService.login(loginDto);

        Cookie cookie  = new Cookie("hJwtToken",token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); should only be used when using https
//        cookie.setPath("/auth/"); this will add the cookie automatically in further requests
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }
}
