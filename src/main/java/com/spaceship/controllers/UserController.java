package com.spaceship.controllers;

import com.spaceship.dtos.CreateUserBodyDTO;
import com.spaceship.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody CreateUserBodyDTO createUserBodyDTO) {
        return userService.registerUser(createUserBodyDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/role/{rolId}")
    public ResponseEntity<Map<String, Object>> getUsersByRol(@PathVariable Long rolId) {
        return userService.getUsersByRol(rolId);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody CreateUserBodyDTO createUserBodyDTO) {
        return userService.updateUser(userId, createUserBodyDTO);
    }
}
