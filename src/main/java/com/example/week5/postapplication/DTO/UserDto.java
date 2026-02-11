package com.example.week5.postapplication.DTO;

import com.example.week5.postapplication.Entities.Enums.Plan;
import com.example.week5.postapplication.Entities.Enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private  Long id;

    private String email;
    private String name;
    private Plan plan;

    private Set<Role> roles;
}

