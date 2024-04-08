package com.vhp.clockinginapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhp.clockinginapi.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  
}
