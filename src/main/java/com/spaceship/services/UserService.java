package com.spaceship.services;

import com.spaceship.dtos.UserDTO;
import com.spaceship.entities.User;
import com.spaceship.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<Map<String, Object>> registerUser(User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            String bcryptHasgRegex = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
            boolean isBcryptHash = user.getPassword().matches(bcryptHasgRegex);
            if (!user.getPassword().isEmpty() && !isBcryptHash) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            userRepository.save(user);
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getEmail(), user.getName());
            response.put("data", userDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
