package com.vhp.clockinginapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.vhp.clockinginapi.configurations.jwt.JWTService;
import com.vhp.clockinginapi.dtos.AuthenticationRequestDTO;
import com.vhp.clockinginapi.dtos.AuthenticationResponseDTO;
import com.vhp.clockinginapi.repositories.UserRepository;
import com.vhp.clockinginapi.utils.exceptions.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        }catch (BadCredentialsException exception){
            throw new BusinessException("Invalid credentials!", HttpStatus.BAD_REQUEST);
        }

        var user = userRepository.findByLogin(request.login()).orElseThrow(
            () -> new BadCredentialsException("Invalid credentials")
        );

        return jwtService.generateToken(user, user);
    }
}
