package com.example.week4.postapplication;

import com.example.week4.postapplication.Entities.User;
import com.example.week4.postapplication.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class PostApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
    }

    @Test
    void jwtTest(){
        User newUser = new User(4L,"hemant@gmail.com","Hemant Sharma","pass");

        String token = jwtService.generateToken(newUser);

        System.out.println(token);

        Long newUserId = jwtService.getUserIdFromToken("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJoZW1hbnRAZXhhbXBsZS5jb20iLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaWF0IjoxNzcwMDIyNjUxLCJleHAiOjE3NzAwMjI3MTF9.Rd2W_C7nPiCcP2gZ9SPwkXi2DvG8DMZYDgcF9rrqvxjCHa_P4zYFMQVkJf5Itrdh");

        System.out.println(newUserId);


    }

}
