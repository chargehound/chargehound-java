package com.chargehound.errors;

import java.lang.Exception;
import java.lang.Throwable;

public class ChargehoundException extends Exception {
  ChargehoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public static class HttpException extends ChargehoundException {
    private Integer status;
    private String reason;

    public Integer getStatusCode() {
      return this.status;
    }

    public String getReason() {
      return this.reason;
    }

    HttpException(String message, Throwable cause) {
      super(message, cause);
    }

    HttpException(String message, Throwable cause, Integer status, String reason) {
      super(message, cause);

      this.status = status;
      this.reason = reason;
    }

    public static class BadRequest extends HttpException {
      BadRequest(String message, Throwable cause, Integer status, String reason) {
        super(message, cause, status, reason);
      }
    }

    public static class Unauthorized extends HttpException {
      Unauthorized(String message, Throwable cause, Integer status, String reason) {
        super(message, cause, status, reason);
      }
    }

    public static class Forbidden extends HttpException {
      Forbidden(String message, Throwable cause, Integer status, String reason) {
        super(message, cause, status, reason);
      }
    }

    public static class NotFound extends HttpException {
      NotFound(String message, Throwable cause, Integer status, String reason) {
        super(message, cause, status, reason);
      }
    }

    public static class InternalServerError extends HttpException {
      InternalServerError(String message, Throwable cause, Integer status, String reason) {
        super(message, cause, status, reason);
      }
    }
  }
}
