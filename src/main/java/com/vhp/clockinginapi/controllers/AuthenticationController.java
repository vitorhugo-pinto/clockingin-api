package com.vhp.clockinginapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vhp.clockinginapi.dtos.ApiResponseDTO;
import com.vhp.clockinginapi.dtos.AuthenticationRequestDTO;
import com.vhp.clockinginapi.dtos.AuthenticationResponseDTO;
import com.vhp.clockinginapi.services.AuthenticationService;

@CrossOrigin
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AuthenticationResponseDTO>> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(
                new ApiResponseDTO<>(
                        true,
                        "Authentication completed successfully",
                        authenticationService.authenticate(request),
                        null));
    }
}

