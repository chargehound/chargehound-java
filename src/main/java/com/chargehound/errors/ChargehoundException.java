package com.chargehound.errors;

import java.lang.Exception;
import java.lang.Throwable;

public class ChargehoundException extends Exception {
    private Integer status;
    private String reason;

    public Integer getStatusCode () {
      return this.status;
    }

    public String getReason () {
      return this.reason;
    }

    ChargehoundException(String message, Throwable cause) {
      super(message, cause);
    }

    ChargehoundException(String message, Throwable cause, Integer status, String reason) {
      super(message, cause);

      this.status = status;
      this.reason = reason;
    }

  public static class BadRequest extends ChargehoundException {
    BadRequest(String message, Throwable cause, Integer status, String reason) {
      super(message, cause, status, reason);
    }
  }

  public static class Unauthorized extends ChargehoundException {
    Unauthorized(String message, Throwable cause, Integer status, String reason) {
      super(message, cause, status, reason);
    }
  }

  public static class Forbidden extends ChargehoundException {
    Forbidden(String message, Throwable cause, Integer status, String reason) {
      super(message, cause, status, reason);
    }
  }

  public static class NotFound extends ChargehoundException {
    NotFound(String message, Throwable cause, Integer status, String reason) {
      super(message, cause, status, reason);
    }
  }

  public static class InternalServerError extends ChargehoundException {
    InternalServerError(String message, Throwable cause, Integer status, String reason) {
      super(message, cause, status, reason);
    }
  }
}
