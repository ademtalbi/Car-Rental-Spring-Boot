package com.codewithprojects.Car_Rentel_Spring.services.auth;

import com.codewithprojects.Car_Rentel_Spring.dto.SingUpRequest;
import com.codewithprojects.Car_Rentel_Spring.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SingUpRequest singUpRequest);

    boolean hasCustomerWithEmail(String email);
}
