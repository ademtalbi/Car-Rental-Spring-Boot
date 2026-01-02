package com.codewithprojects.Car_Rentel_Spring.dto;

import com.codewithprojects.Car_Rentel_Spring.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;
}
