package com.codewithprojects.Car_Rentel_Spring.controller;

import com.codewithprojects.Car_Rentel_Spring.dto.AuthenticationRequest;
import com.codewithprojects.Car_Rentel_Spring.dto.AuthenticationResponse;
import com.codewithprojects.Car_Rentel_Spring.dto.SingUpRequest;
import com.codewithprojects.Car_Rentel_Spring.dto.UserDto;
import com.codewithprojects.Car_Rentel_Spring.entity.User;
import com.codewithprojects.Car_Rentel_Spring.repository.UserRepository;
import com.codewithprojects.Car_Rentel_Spring.services.auth.AuthService;
import com.codewithprojects.Car_Rentel_Spring.services.jwt.UserService;
import com.codewithprojects.Car_Rentel_Spring.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SingUpRequest singUpRequest) {

        if (authService.hasCustomerWithEmail(singUpRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Customer with this email already exists");
        }

        UserDto createdCustomerDto = authService.createCustomer(singUpRequest);

        if (createdCustomerDto == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Customer not created, try again");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCustomerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws UsernameNotFoundException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        UserDetails userDetails =
                userService.userDetailsService()
                        .loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser =
                userRepository.findFirstByEmail(userDetails.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return ResponseEntity.ok(authenticationResponse);
    }
}
