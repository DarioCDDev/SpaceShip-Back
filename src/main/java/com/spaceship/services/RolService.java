package com.spaceship.services;

import com.spaceship.entities.Rol;
import com.spaceship.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public ResponseEntity<Map<String, Object>> createRol(Rol rol) {
        Map<String, Object> response = new HashMap<>();
        try {
            Rol savedRol = rolRepository.save(rol);
            response.put("message", "Role created successfully");
            response.put("data", savedRol);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", "Error creating role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getAllRoles() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("message", "Roles retrieved successfully");
            response.put("data", rolRepository.findAll());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Error retrieving roles: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getRolById(Long rolId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Role with ID " + rolId + " not found"));
            response.put("message", "Rol retrieved successfully");
            response.put("data", rol);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Error retrieving roles: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteRol(Long rolId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new RuntimeException("Role with ID " + rolId + " not found"));

            rolRepository.delete(rol);
            response.put("message", "Role deleted successfully");
            response.put("data", rol);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Error deleting role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateRol(Long rolId, Rol newRol) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Rol> rolOptional = rolRepository.findById(rolId);
            return rolOptional.map(rol -> {
                if (newRol.getName() != null) {
                    rol.setName(newRol.getName());
                }
                Rol updatedRol = rolRepository.save(rol);
                response.put("message", "Role updated successfully");
                response.put("data", updatedRol);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }).orElseGet(() -> {
                response.put("error", "Role with ID " + rolId + " not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            });
        } catch (Exception e) {
            response.put("error", "Error updating role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
