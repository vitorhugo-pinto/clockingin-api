package com.vhp.clockinginapi.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.vhp.clockinginapi.models.builders.CheckPointBuider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "check_point")
public class CheckPointEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "users_id", insertable = false, updatable = false)
  private UserEntity user;

  @Column(name = "users_id", nullable = false)
  private UUID userId; 

  @Temporal(TemporalType.TIMESTAMP)
  LocalDateTime timeStamp;

  private Boolean lunchBreak;

  public static CheckPointBuider builder() {
    return new CheckPointBuider();
  }

  public void setId(UUID id) {
    this.id = id;
  }
  public UUID getId() {
    return id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
  public UUID getUserId() {
    return userId;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
  }

  public Boolean getLunchBreak() {
    return lunchBreak;
  }

  public void setLunchBreak(Boolean lunchBreak) {
    this.lunchBreak = lunchBreak;
  }

  @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        CheckPointEntity checkPoint = (CheckPointEntity) o;
        return Objects.equals(timeStamp, checkPoint.timeStamp)
                && Objects.equals(userId, checkPoint.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timeStamp, userId);
    }
}
