package com.vhp.clockinginapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhp.clockinginapi.models.CheckPointEntity;
import java.time.LocalDateTime;


@Repository
public interface CheckPointRepository extends JpaRepository<CheckPointEntity, UUID> {
  List<CheckPointEntity> findAllByUserIdAndTimeStampBetween(UUID userId, LocalDateTime timeStampStart, LocalDateTime timeStampEnd);
}
