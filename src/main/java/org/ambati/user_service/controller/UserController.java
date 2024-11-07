package org.ambati.user_service.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.ambati.user_service.dtos.RegisterUserRequestDto;
import org.ambati.user_service.dtos.RegisterUserResponseDto;
import org.ambati.user_service.model.CustomUser;
import org.ambati.user_service.dtos.MenuItemRequestDto;

import org.ambati.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String Home(HttpServletRequest request){
        return "Welcome To Satya's Restaurant User portal !!!"+ request.getAuthType() +" "+ request.getPathInfo();
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDto registerUserDto){

        try {
            registerUserDto.validate();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        CustomUser newUser = userService.registerNewUser(registerUserDto.toCustomUser());
        RegisterUserResponseDto responseDto = new RegisterUserResponseDto();
        responseDto.setId(newUser.getId());
        responseDto.setUsername(newUser.getUsername());
        responseDto.setEmail(newUser.getEmail());
        responseDto.setActive(newUser.isActive());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> verifyUser(@RequestBody CustomUser user){

        try {
            return new ResponseEntity<>(userService.verifyUser(user), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage());
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        try {
            return new ResponseEntity<>(userService.getUserByUsername(username),HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }

    }

    @GetMapping("/menu-items")
    public ResponseEntity<List<MenuItemRequestDto>>  getMenu() {

        return ResponseEntity.ok(userService.getMenuItems());
    }

}
