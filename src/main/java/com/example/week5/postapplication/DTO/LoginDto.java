package com.example.week5.postapplication.DTO;

import com.example.week5.postapplication.Annotations.PasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    @Email(message = "enter a valid email")
    private String email;

    private String password;
}
