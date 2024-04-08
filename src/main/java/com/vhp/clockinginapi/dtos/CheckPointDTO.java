package com.vhp.clockinginapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vhp.clockinginapi.models.UserEntity;

public record CheckPointDTO(UUID id, UserEntity user, UUID userId, LocalDateTime timeStamp, Boolean lunchBreak) {
} 
