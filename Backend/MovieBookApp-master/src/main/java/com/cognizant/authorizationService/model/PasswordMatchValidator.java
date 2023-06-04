package com.cognizant.authorizationService.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, User>{

    @Override
    public void initialize(PasswordMatch p) {
        
    }
    
    public boolean isValid(User user, ConstraintValidatorContext c) {
        String plainPassword = user.getUserPassword();
        String repeatPassword = user.getUserConfirmedPassword();
        
        if(plainPassword == null || !plainPassword.equals(repeatPassword)) {
            return false;
        }
        
        return true;
    }

}

