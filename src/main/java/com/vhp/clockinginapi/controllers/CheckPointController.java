package com.vhp.clockinginapi.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vhp.clockinginapi.dtos.ApiResponseDTO;
import com.vhp.clockinginapi.dtos.CheckPointDTO;
import com.vhp.clockinginapi.dtos.CheckPointRequestDTO;
import com.vhp.clockinginapi.dtos.SummaryDTO;
import com.vhp.clockinginapi.services.CheckPointService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/check-point")
public class CheckPointController {

  private CheckPointService checkPointService;

  CheckPointController(CheckPointService checkPointService) {
    this.checkPointService = checkPointService;
  }

  @PostMapping("/clock-in")
  @PreAuthorize("hasRole('EMPLOYEE')")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<ApiResponseDTO<CheckPointDTO>> create(@Valid @RequestBody CheckPointRequestDTO dto, HttpServletRequest request) {
    UUID userId = UUID.fromString(request.getAttribute("userId").toString());
    String jobType = request.getAttribute("jobType").toString();

    return ResponseEntity.ok(
      new ApiResponseDTO<>(
        true,
        "Success. Clock in registered",
        this.checkPointService.create(dto, userId, jobType),
        null
      )
    );
  }

  @GetMapping("/summary")
  @PreAuthorize("hasRole('EMPLOYEE')")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<ApiResponseDTO<SummaryDTO>> create(HttpServletRequest request) {
    UUID userId = UUID.fromString(request.getAttribute("userId").toString());
    String jobType = request.getAttribute("jobType").toString();
    
    return ResponseEntity.ok(
      new ApiResponseDTO<>(
        true,
        "Retrive user summary with success.",
        this.checkPointService.getSummary(userId, jobType),
        null
      )
    );
  }
}
