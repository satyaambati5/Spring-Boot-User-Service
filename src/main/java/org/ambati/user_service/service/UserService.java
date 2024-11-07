package org.ambati.user_service.service;


import org.ambati.user_service.exceptions.UserAlreadyExistsException;
import org.ambati.user_service.model.CustomUser;


import org.ambati.user_service.dtos.MenuItemRequestDto;
import org.ambati.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class UserService {
    //Dependency injection
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private RestTemplate restTemplate;

   @Autowired
   private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public CustomUser registerNewUser(CustomUser user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public String verifyUser(CustomUser user) throws RuntimeException {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
//            String token=jwtService.generateToken(user.getUsername());
//            //Is user is Authenticated we will generate a token for user
//            logger.info("Token Data is Satya : {} ",token);

            return jwtService.generateToken(user.getUsername()) ;
        } else {
            throw  new RuntimeException("Invalid Credentials");

        }
    }


    public CustomUser getUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user =userRepository.findByUsername(username);
        if (user != null){

            return user;
        }
        else{
            throw new UsernameNotFoundException("User not found with username: ");
        }
    }


    public List<MenuItemRequestDto> getMenuItems() {
        String url = "http://localhost:8080/api/menu-items/items";
        ResponseEntity<List<MenuItemRequestDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

}
