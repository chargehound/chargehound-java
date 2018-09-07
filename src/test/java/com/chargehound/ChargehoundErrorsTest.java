package com.chargehound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.chargehound.errors.ChargehoundException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

public class ChargehoundErrorsTest {
  @Test public void testBadRequest() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);

            String json = javax.json.Json.createObjectBuilder()
                .add("url", "/v1/disputes/puppy/submit")
                .add("livemode", false)
                .add("error", javax.json.Json.createObjectBuilder()
                    .add("status", 404)
                    .add("message", "A dispute with id 'puppy' was not found"))
                .build().toString();

            result.setContent(json);
            result.setStatusCode(400);
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    boolean exceptionThrown = false;

    try {
      chargehound.disputes.retrieve("dp_123");
    } catch (ChargehoundException.HttpException exception) {
      assertTrue(exception instanceof ChargehoundException.HttpException.BadRequest);
      assertEquals((Integer) 400, exception.getStatusCode());
      assertEquals("A dispute with id 'puppy' was not found", exception.getReason());
      exceptionThrown = true;
    }

    assertTrue(exceptionThrown);
  }

  @Test public void testUnexpectedErrorJson() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final String json = javax.json.Json.createObjectBuilder()
          .add("url", "/v1/disputes/puppy/submit")
          .add("livemode", false)
          .add("unexpectedkey", javax.json.Json.createObjectBuilder()
              .add("status", 404)
              .add("message", "A dispute with id 'puppy' was not found"))
          .build().toString();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);

            result.setContent(json);
            result.setStatusCode(400);
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    boolean exceptionThrown = false;

    try {
      chargehound.disputes.retrieve("dp_123");
    } catch (ChargehoundException.HttpException exception) {
      assertTrue(exception instanceof ChargehoundException.HttpException);
      assertEquals((Integer) 400, exception.getStatusCode());
      assertEquals("400\n" + json, exception.getReason());
      exceptionThrown = true;
    }

    assertTrue(exceptionThrown);
  }

  @Test public void testGenericError() throws IOException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            throw new IOException("IOException");
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    boolean exceptionThrown = false;

    try {
      chargehound.disputes.retrieve("dp_123");
    } catch (ChargehoundException exception) {
      assertTrue(exception instanceof ChargehoundException);
      assertEquals("IOException", exception.getMessage());
      exceptionThrown = true;
    }

    assertTrue(exceptionThrown);
  }
}
