package com.vhp.clockinginapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhp.clockinginapi.models.CheckPointEntity;

@Repository
public interface CheckPointRepository extends JpaRepository<CheckPointEntity, UUID> {
  
}
