package com.example.week5.postapplication.DTO;

import com.example.week5.postapplication.Annotations.PasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {

    @NotBlank
    @Size(min=2,max = 40, message="Name should have 2-30 characters")
    private String name;

    @NotBlank
    @Email(message = "enter a valid email")
    private String email;

    @PasswordValidation
    private String password;
}
