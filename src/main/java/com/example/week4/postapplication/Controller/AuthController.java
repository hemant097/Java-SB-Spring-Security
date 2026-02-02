package com.example.week4.postapplication.Controller;

import com.example.week4.postapplication.DTO.SignupDto;
import com.example.week4.postapplication.DTO.UserDto;
import com.example.week4.postapplication.Service.UserService;
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

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDto> signUpMethod(@RequestBody SignupDto signupDto){

        UserDto userDto = userService.signUp(signupDto);
        return ResponseEntity.ok(userDto);
    }
}
