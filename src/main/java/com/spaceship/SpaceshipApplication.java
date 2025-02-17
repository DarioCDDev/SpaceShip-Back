package com.spaceship;

import com.spaceship.entities.Rol;
import com.spaceship.repository.RolRepository;
import com.spaceship.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpaceshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceshipApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository) {
        return args -> {
            Rol userRol = new Rol();
            Rol adminRol = new Rol();
            userRol.setName("User");
            adminRol.setName("Admin");

            rolRepository.save(userRol);
            rolRepository.save(adminRol);
        };
    }

}
