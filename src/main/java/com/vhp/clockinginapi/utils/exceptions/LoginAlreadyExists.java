package com.vhp.clockinginapi.utils.exceptions;

public class LoginAlreadyExists extends RuntimeException {
  public LoginAlreadyExists(String message) {
      super(message);
  }
}
