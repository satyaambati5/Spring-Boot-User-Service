package org.ambati.user_service.dtos;


import lombok.Data;

@Data
public class RegisterUserResponseDto {
    private Long id;
    private String username;
    private String email;
    private boolean active;
}
