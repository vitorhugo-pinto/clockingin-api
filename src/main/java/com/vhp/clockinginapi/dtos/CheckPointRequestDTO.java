package com.vhp.clockinginapi.dtos;

import java.time.LocalDateTime;

public record CheckPointRequestDTO(LocalDateTime timeStamp, Boolean lunchBreak) {
  
}
