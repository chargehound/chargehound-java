package com.chargehound.errors;

import com.chargehound.errors.ChargehoundException;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

import java.io.IOException;
import java.io.StringReader;
import java.lang.Exception;

public class ChargehoundExceptionFactory {

  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  /**
   * Define a class to parse the error message, status, and type.
   */
  public static class ChargehoundErrorDetails extends GenericJson {
    @Key("message")
    public String message;
    @Key("status")
    public Integer status;
    @Key("type")
    public String type;
  }

  /**
   * Define a class to parse the error Json.
   */
  public static class ChargehoundErrorJson extends GenericJson {
    @Key("error")
    public ChargehoundErrorDetails error;

    public boolean livemode;

    public String url;
  }

  /**
   * Create a Chargehound exception from an Http exception.
   * @param error an HTTP exception
   * @return ChargehoundException
   */
  public ChargehoundException httpResponseException(HttpResponseException error) {
    String jsonContent = error.getContent();
    Integer statusCode = error.getStatusCode();

    StringReader reader = new StringReader(jsonContent);

    try {
      JsonParser parser = JSON_FACTORY.createJsonParser(reader);
      ChargehoundErrorJson errorDetails = parser.parseAndClose(ChargehoundErrorJson.class);
      String errorMessage = errorDetails.error.message;
      String errorType = errorDetails.error.type;
      switch (statusCode) {
        case 400:
          return new ChargehoundException.HttpException.BadRequest(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
        case 401:
          return new ChargehoundException.HttpException.Unauthorized(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
        case 403:
          return new ChargehoundException.HttpException.Forbidden(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
        case 404:
          return new ChargehoundException.HttpException.NotFound(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
        case 500:
          return new ChargehoundException.HttpException.InternalServerError(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
        default:
          return new ChargehoundException.HttpException(
            error.getMessage(), error.getCause(), statusCode, errorMessage, errorType
          );
      }
    } catch (Exception unexpectedError) {
      return new ChargehoundException.HttpException(
        error.getMessage(), error.getCause(), statusCode, error.getMessage(), null
      );
    }
  }

  /**
   * Create a Chargehound exception from a generic exception.
   * @param error any exception exception
   * @return ChargehoundException
   */
  public ChargehoundException genericChargehoundException(Exception error) {
    return new ChargehoundException(error.getMessage(), error.getCause());
  }
}
