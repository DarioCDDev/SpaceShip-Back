package com.spaceship.services;

import com.spaceship.dtos.CreateUserBodyDTO;
import com.spaceship.dtos.UserDTO;
import com.spaceship.entities.Rol;
import com.spaceship.entities.User;
import com.spaceship.repository.RolRepository;
import com.spaceship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;

    @Autowired
    public UserService(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    public ResponseEntity<Map<String, Object>> registerUser(CreateUserBodyDTO createUserBodyDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = createUserBodyDTO.getUser();
            String bcryptHashRegex = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
            boolean isBcryptHash = user.getPassword().matches(bcryptHashRegex);

            if (!user.getPassword().isEmpty() && !isBcryptHash) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }

            Rol rol = rolRepository.findById(createUserBodyDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("Role with ID " + createUserBodyDTO.getRolId() + " not found"));

            user.setRol(rol);
            userRepository.save(user);

            UserDTO userDTO = new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRol());
            response.put("message", "User registered successfully");
            response.put("data", userDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("error", "Error registering user: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserDTO> userDTOList = userRepository.findAll().stream()
                    .map(user -> new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRol()))
                    .collect(Collectors.toList());

            response.put("message", "Users retrieved successfully");
            response.put("data", userDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", "Error retrieving users: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getUserById(Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        UserDTO userDTO = new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRol());
                        response.put("message", "User found");
                        response.put("data", userDTO);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        response.put("error", "User with ID " + userId + " not found");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });

        } catch (Exception e) {
            response.put("error", "Error retrieving user: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getUserByEmail(String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            return userRepository.findOneByEmail(email)
                    .map(user -> {
                        UserDTO userDTO = new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRol());
                        response.put("message", "User found");
                        response.put("data", userDTO);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        response.put("error", "User with email: " + email + " not found");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });

        } catch (Exception e) {
            response.put("error", "Error retrieving user by email: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteUser(Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        userRepository.delete(user);
                        response.put("message", "User deleted successfully");
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        response.put("error", "User with ID " + userId + " not found");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });

        } catch (Exception e) {
            response.put("error", "Error deleting user: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateUser(Long userId, CreateUserBodyDTO createUserBodyDTO) {
        Map<String, Object> response = new HashMap<>();
        User newUser = createUserBodyDTO.getUser();
        try {
            return userRepository.findById(userId)
                    .map(currentUser -> {
                        if (newUser != null) {
                            if (newUser.getUsername() != null) {
                                currentUser.setUsername(newUser.getUsername());
                            }
                            if (newUser.getPassword() != null) {
                                String bcryptHashRegex = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
                                boolean isBcryptHash = newUser.getPassword().matches(bcryptHashRegex);
                                if (!newUser.getPassword().isEmpty() && !isBcryptHash) {
                                    currentUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
                                }
                            }
                        }
                        if (createUserBodyDTO.getRolId() != null){
                            Rol rol = rolRepository.findById(createUserBodyDTO.getRolId()) .orElseThrow(() -> new RuntimeException("Role with ID " + createUserBodyDTO.getRolId() + " not found"));
                            currentUser.setRol(rol);
                        }

                        User updatedUser = userRepository.save(currentUser);
                        response.put("message", "User updated successfully");
                        response.put("data", updatedUser);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        response.put("error", "User with ID " + userId + " not found");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });

        } catch (Exception e) {
            response.put("error", "Error updating user: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getUsersByRol(Long rolId) {
        Map<String, Object> response = new HashMap<>();
        try {
            return rolRepository.findById(rolId)
                    .map(rol -> {
                        List<UserDTO> userDTOList = userRepository.findAll().stream()
                                .filter(user -> user.getRol().equals(rol))
                                .map(user -> new UserDTO(user.getUserId(), user.getEmail(), user.getUsername(), user.getRol()))
                                .collect(Collectors.toList());

                        response.put("message", "Users with role retrieved successfully");
                        response.put("data", userDTOList);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        response.put("error", "Role with ID " + rolId + " not found");
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });

        } catch (Exception e) {
            response.put("error", "Error retrieving users by role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
