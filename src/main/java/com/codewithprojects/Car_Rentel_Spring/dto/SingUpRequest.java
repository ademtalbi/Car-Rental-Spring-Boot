package com.codewithprojects.Car_Rentel_Spring.dto;


import lombok.Data;

@Data
public class SingUpRequest {

    private String email;
    private String name;
    private String password;
}
