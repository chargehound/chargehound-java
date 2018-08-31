package com.chargehound;

import com.chargehound.errors.ChargehoundException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChargehoundErrorsTest {
  @Test public void testBadRequest() throws IOException {
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
            // TODO: error content
            result.setStatusCode(400);
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    try {
      chargehound.Disputes.retrieve("dp_123");
    } catch (ChargehoundException exception) {
      // TODO: error description
      assertTrue(exception instanceof ChargehoundException.BadRequest);
      assertEquals("400", exception.getMessage());
    }
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

    try {
      chargehound.Disputes.retrieve("dp_123");
    } catch (ChargehoundException exception) {
      // TODO: error description
      assertTrue(exception instanceof ChargehoundException);
      assertEquals("IOException", exception.getMessage());
    }
  }
}
