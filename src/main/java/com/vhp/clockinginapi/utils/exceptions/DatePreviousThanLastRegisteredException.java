package com.vhp.clockinginapi.utils.exceptions;

public class DatePreviousThanLastRegisteredException extends RuntimeException {
  public DatePreviousThanLastRegisteredException(String message) {
      super(message);
  }
}
