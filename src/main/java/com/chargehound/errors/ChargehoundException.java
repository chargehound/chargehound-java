package com.chargehound.errors;

import java.lang.Exception;
import java.lang.Throwable;

public class ChargehoundException extends Exception {
    ChargehoundException(String message, Throwable cause) {
      super(message, cause);
    }

  static class BadRequest extends ChargehoundException {
    BadRequest(String message, Throwable cause) {
      super(message, cause);
    }
  }

  static class Unauthorized extends ChargehoundException {
    Unauthorized(String message, Throwable cause) {
      super(message, cause);
    }
  }

  static class Forbidden extends ChargehoundException {
    Forbidden(String message, Throwable cause) {
      super(message, cause);
    }
  }

  static class NotFound extends ChargehoundException {
    NotFound(String message, Throwable cause) {
      super(message, cause);
    }
  }

  static class InternalServerError extends ChargehoundException {
    InternalServerError(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
