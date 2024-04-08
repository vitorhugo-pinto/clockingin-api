package com.vhp.clockinginapi.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record CheckPointResponseDTO(UUID userId, LocalDateTime timeStamp, Boolean lunchBreak) {
}