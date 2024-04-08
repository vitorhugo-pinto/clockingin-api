package com.vhp.clockinginapi.utils.exceptions;

import java.time.LocalDateTime;

public record ErrorDTO(LocalDateTime timestamp, Integer status, String error, String message, String path) {
}

