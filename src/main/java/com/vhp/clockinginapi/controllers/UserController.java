package com.vhp.clockinginapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vhp.clockinginapi.dtos.ApiResponseDTO;
import com.vhp.clockinginapi.dtos.UserDTO;
import com.vhp.clockinginapi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
  
  private UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseDTO<UserDTO>> create(@Valid @RequestBody UserDTO dto) {
    return ResponseEntity.ok(
      new ApiResponseDTO<>(
        true,
        "Success. User created",
        this.userService.create(dto),
        null
      )
    );
  }
}
