package com.spaceship.dtos;

import com.spaceship.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserBodyDTO {

    private User user;
    private Long rolId;
}
