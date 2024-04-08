package com.vhp.clockinginapi.models.enums;

public enum Role {

  ADMIN("Administrator"),
  EMPLOYEE("Employee");

  private final String description;

  Role(String description) {
      this.description = description;
  }

  public String getDescription() {
      return description;
  }
}
