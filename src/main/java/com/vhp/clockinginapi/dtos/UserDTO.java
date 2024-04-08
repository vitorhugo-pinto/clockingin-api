package com.vhp.clockinginapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vhp.clockinginapi.models.enums.Role;
import com.vhp.clockinginapi.models.enums.JobType;

public class UserDTO {
  private UUID id;
  private String name;
  private String login;
  private String password;
  private Role role;
  private JobType jobType;
  private LocalDateTime createdAt;

  public UserDTO() {

  }

  public UserDTO(UUID id, String name, String login, String password, Role role, JobType jobType, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.login = login;
    this.password = password;
    this.role = role;
    this.jobType = jobType;
    this.createdAt = createdAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogin() {
    return login;
  }
  
  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public JobType getJobType() {
    return jobType;
  }

  public void setJobType(JobType jobType) {
    this.jobType = jobType;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
}
