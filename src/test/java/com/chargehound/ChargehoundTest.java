package com.chargehound;

import com.chargehound.models.Dispute;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChargehoundTest {
  @Test public void testChargehoundConstructor() {
    Chargehound chargehound = new Chargehound("test_123");

    assertEquals("test_123", chargehound.getApiKey());
  }

  @Test public void testOverrideApiHost() {
    Chargehound chargehound = new Chargehound("test_123");

    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    assertEquals("http://test.test.com/v1/", chargehound.getApiBase());
  }

  @Test public void testDisputesResource() {
    Chargehound chargehound = new Chargehound("test_123");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent("123abc");
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.retrieve("dp_123");
    assertEquals(new Dispute(), result);
  }
}
