package com.vhp.clockinginapi.models.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vhp.clockinginapi.models.UserEntity;
import com.vhp.clockinginapi.models.enums.JobType;
import com.vhp.clockinginapi.models.enums.Role;

public class UserBuilder {

  private UUID id;
  private String name;
  private String login;
  private String password;
  private Role role;
  private JobType jobType;
  private LocalDateTime createdAt;

  public UserBuilder id(UUID id) {
      this.id = id;
      return this;
  }

  public UserBuilder name(String name) {
    this.name = name;
    return this;
}

  public UserBuilder login(String login) {
      this.login = login;
      return this;
  }

  public UserBuilder password(String password) {
      this.password = password;
      return this;
  }

  public UserBuilder role(Role role) {
      this.role = role;
      return this;
  }

  public UserBuilder jobType(JobType jobType) {
    this.jobType = jobType;
    return this;
}

  public UserBuilder createdAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
}

  public UserEntity build() {
      UserEntity user = new UserEntity();
      user.setId(this.id);
      user.setName(this.name);
      user.setLogin(this.login);
      user.setPassword(this.password);
      user.setRole(this.role);
      user.setJobType(this.jobType);
      user.setCreatedAt(this.createdAt);
      return user;
  }
}
