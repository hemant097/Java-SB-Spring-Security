package com.example.week5.postapplication.DTO;

import com.example.week5.postapplication.Entities.Enums.Plan;
import lombok.Data;

@Data
public class UserDto {

    private  Long id;

    private String email;
    private String name;
    private Plan plan;

}

