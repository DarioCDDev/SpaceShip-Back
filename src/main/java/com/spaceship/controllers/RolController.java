package com.spaceship.controllers;

import com.spaceship.entities.Rol;
import com.spaceship.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createRol(@RequestBody Rol rol) {
        return rolService.createRol(rol);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        return rolService.getAllRoles();
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> getRolById(@PathVariable Long rolId) {
        return rolService.getRolById(rolId);
    }

    @PutMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> updateRol(@PathVariable Long rolId, @RequestBody Rol rol) {
        return rolService.updateRol(rolId, rol);
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<Map<String, Object>> deleteRol(@PathVariable Long rolId) {
        return rolService.deleteRol(rolId);
    }
}
