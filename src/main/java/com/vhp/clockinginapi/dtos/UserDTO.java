package com.vhp.clockinginapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vhp.clockinginapi.models.enums.Role;
import com.vhp.clockinginapi.models.enums.JobType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(
  UUID id,
  String login,
  String password,
  Role role,
  JobType jobType,
  LocalDateTime createdAt
) {
  
}
