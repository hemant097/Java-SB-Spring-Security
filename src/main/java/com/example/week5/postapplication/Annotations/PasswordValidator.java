package com.example.week5.postapplication.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if(isValidPassword(password))
            return true;

        return false;
    }

    public boolean isValidPassword(String pw){
        if(pw.length()<8) return false;

        boolean isUpperCase = false, isLowerCase = false, hasSpecialChar = false;

        String specialCharacters = "!@#$%^&*()_+-={}[]|;':<>,.?";

        for(int i=0; i<pw.length();i++){
            char c = pw.charAt(i);

            if(Character.isUpperCase(c))
                isUpperCase=true;
            if(Character.isLowerCase(c))
                isLowerCase=true;
            if(specialCharacters.indexOf(c)!=-1)
                hasSpecialChar=true;

            if( isLowerCase && isUpperCase && hasSpecialChar )
                return true;
        }

        return false;
    }

}
