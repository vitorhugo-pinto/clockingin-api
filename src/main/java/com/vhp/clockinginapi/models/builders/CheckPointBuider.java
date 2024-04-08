package com.vhp.clockinginapi.models.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vhp.clockinginapi.models.CheckPointEntity;
import com.vhp.clockinginapi.models.UserEntity;

public class CheckPointBuider {

  private UUID id;
  private UserEntity user;
  private UUID userId;
  private LocalDateTime timeStamp;
  private Boolean lunchBreak;

  public CheckPointBuider id(UUID id) {
      this.id = id;
      return this;
  }

  public CheckPointBuider user(UserEntity user) {
      this.user = user;
      return this;
  }

  public CheckPointBuider userId(UUID userId) {
      this.userId = userId;
      return this;
  }

  public CheckPointBuider timeStamp(LocalDateTime timeStamp) {
      this.timeStamp = timeStamp;
      return this;
  }

  public CheckPointBuider lunchBreak(Boolean lunchBreak) {
    this.lunchBreak = lunchBreak;
    return this;
}

  public CheckPointEntity build() {
    CheckPointEntity checkPoint = new CheckPointEntity();
      checkPoint.setId(this.id);
      checkPoint.setUser(this.user);
      checkPoint.setUserId(this.userId);
      checkPoint.setTimeStamp(this.timeStamp);
      checkPoint.setLunchBreak(this.lunchBreak);
      return checkPoint;
  }
}
