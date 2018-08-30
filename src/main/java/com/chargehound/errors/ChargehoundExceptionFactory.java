package com.chargehound.errors;

import com.chargehound.errors.ChargehoundException;
import com.google.api.client.http.HttpResponseException;
import java.io.IOException;
import java.lang.Exception;

public class ChargehoundExceptionFactory {
  public ChargehoundException httpResponseException(HttpResponseException error) {
    switch (error.getStatusCode()) {
      case 400:
        return new ChargehoundException.BadRequest(error.getMessage(), error.getCause());
      case 401:
        return new ChargehoundException.Unauthorized(error.getMessage(), error.getCause());
      case 403:
        return new ChargehoundException.Forbidden(error.getMessage(), error.getCause());
      case 404:
        return new ChargehoundException.NotFound(error.getMessage(), error.getCause());
      case 500:
        return new ChargehoundException.InternalServerError(error.getMessage(), error.getCause());
      default:
        return new ChargehoundException(error.getMessage(), error.getCause());
    }
  }

  public ChargehoundException genericChargehoundException (Exception error) {
    return new ChargehoundException(error.getMessage(), error.getCause());
  }
}
