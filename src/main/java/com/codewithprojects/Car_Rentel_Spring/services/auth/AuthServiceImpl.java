package com.codewithprojects.Car_Rentel_Spring.services.auth;

import com.codewithprojects.Car_Rentel_Spring.dto.SingUpRequest;
import com.codewithprojects.Car_Rentel_Spring.dto.UserDto;
import com.codewithprojects.Car_Rentel_Spring.entity.User;
import com.codewithprojects.Car_Rentel_Spring.enums.UserRole;
import com.codewithprojects.Car_Rentel_Spring.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null){
            User newAdminAccount = new User();
            newAdminAccount.setName("Admin");
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newAdminAccount.setUserRole(UserRole.ADMIN);
            userRepository.save(newAdminAccount);
            System.out.println("Admin account has been created successfully");
        }
    }

    @Override
    public UserDto createCustomer(SingUpRequest singUpRequest) {
        User user = new User();
        user.setName(singUpRequest.getName());
        user.setEmail(singUpRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(singUpRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
