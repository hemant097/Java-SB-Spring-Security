package com.example.week5.postapplication.DTO;

import com.example.week5.postapplication.Entities.Enums.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationAssignmentRequest {

    private Set<Role> roles;

    private Set<Permission> permissions;
}
