package com.chargehound.errors;

import com.chargehound.errors.ChargehoundException;
import com.google.api.client.http.HttpResponseException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;

public class ChargehoundExceptionFactory {
  public ChargehoundException httpResponseException(HttpResponseException error) {
    String jsonContent = error.getContent();
    javax.json.JsonReader jsonReader = javax.json.Json.createReader(
      new StringReader(jsonContent)
    );

    javax.json.JsonObject json = jsonReader.readObject();
    jsonReader.close();

    Integer statusCode = error.getStatusCode();

    try {
      javax.json.JsonObject errorDetails = json.getJsonObject("error");
      String errorMessage = errorDetails.getString("message");
      switch (statusCode) {
        case 400:
          return new ChargehoundException.HttpException.BadRequest(error.getMessage(), error.getCause(), statusCode, errorMessage);
        case 401:
          return new ChargehoundException.HttpException.Unauthorized(error.getMessage(), error.getCause(), statusCode, errorMessage);
        case 403:
          return new ChargehoundException.HttpException.Forbidden(error.getMessage(), error.getCause(), statusCode, errorMessage);
        case 404:
          return new ChargehoundException.HttpException.NotFound(error.getMessage(), error.getCause(), statusCode, errorMessage);
        case 500:
          return new ChargehoundException.HttpException.InternalServerError(error.getMessage(), error.getCause(), statusCode, errorMessage);
        default:
          return new ChargehoundException.HttpException(error.getMessage(), error.getCause(), statusCode, errorMessage);
      }
    } catch (Exception unexpectedError) {
      return new ChargehoundException.HttpException(error.getMessage(), error.getCause(), statusCode, error.getMessage());
    }
  }

  public ChargehoundException genericChargehoundException (Exception error) {
    return new ChargehoundException(error.getMessage(), error.getCause());
  }
}
