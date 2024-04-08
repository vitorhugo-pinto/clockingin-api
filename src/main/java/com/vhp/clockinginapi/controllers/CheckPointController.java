package com.vhp.clockinginapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vhp.clockinginapi.dtos.ApiResponseDTO;
import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.services.CheckPointService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/check-point")
public class CheckPointController {

  private CheckPointService checkPointService;

  CheckPointController(CheckPointService checkPointService) {
    this.checkPointService = checkPointService;
  }

  @PostMapping("/clock-in")
  @PreAuthorize("hasRole('EMPLOYEE')")
  public ResponseEntity<ApiResponseDTO<CheckPointDTO>> create(@Valid @RequestBody CheckPointDTO dto) {
    return ResponseEntity.ok(
      new ApiResponseDTO<>(
        true,
        "Success. Clock in registered",
        this.checkPointService.create(dto),
        null
      )
    );
  }
}
