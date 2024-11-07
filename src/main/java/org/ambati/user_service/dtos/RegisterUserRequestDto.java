package org.ambati.user_service.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.ambati.user_service.model.CustomUser;

@Data
public class RegisterUserRequestDto {

    private String username;
    private String password;
    private String email;
    private boolean active = false;

    public void validate() throws IllegalArgumentException{
        StringBuilder errorMessages = new StringBuilder();
        if (username == null || username.trim().isEmpty()) {
            errorMessages.append("Username is empty or null. ");
        }
        if (password == null || password.trim().isEmpty()) {
            errorMessages.append("Password is empty or null. ");
        }
        if (email == null || email.trim().isEmpty()) {
            errorMessages.append("Email is empty or null. ");
        }
        if (!errorMessages.isEmpty()) {
            throw new IllegalArgumentException("Validation errors: " + errorMessages.toString());
        }
    }

    public CustomUser toCustomUser() {

        CustomUser user = new CustomUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setActive(active);
        return user;
    }
}
