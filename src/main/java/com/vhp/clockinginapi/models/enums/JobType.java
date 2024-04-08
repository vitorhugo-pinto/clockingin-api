package com.vhp.clockinginapi.models.enums;

public enum JobType {

  FULLTIME("Full-time employee"),
  PARTTIME("Part-time employee");

  private final String description;

  JobType(String description) {
      this.description = description;
  }

  public String getDescription() {
      return description;
  }
}
