package com.chargehound;

import com.chargehound.errors.ChargehoundException;
import com.chargehound.models.Dispute;
import com.chargehound.models.DisputesList;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpResponse;
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
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChargehoundTest {
  static final JsonFactory JSON_FACTORY = new JacksonFactory();

  @Test public void testChargehoundConstructor() {
    Chargehound chargehound = new Chargehound("test_123");

    assertEquals("test_123", chargehound.getApiKey());
  }

  @Test public void testOverrideApiHost() {
    Chargehound chargehound = new Chargehound("test_123");

    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    assertEquals("http://test.test.com/v1", chargehound.getApiBase());
  }

  @Test public void testDisputeRetrieve() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.retrieve(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeResponse() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute.Response testResponse = new Dispute.Response();
    testResponse.disputeId = "dp_123";
    testResponse.responseUrl = "http://test.test.com/the/response";

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123/response");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testResponse));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute.Response result = chargehound.Disputes.response(testResponse.disputeId);

    assertEquals(JSON_FACTORY.toString(testResponse), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeAccept() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "POST");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123/accept");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.accept(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesList() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList();
    testList.data.add(testDispute);

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testList));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    DisputesList result = chargehound.Disputes.list();

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesListWithParams() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList();
    testList.data.add(testDispute);

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes?limit=1&starting_after=dp_111");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testList));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    DisputesList.Params params = new DisputesList.Params.Builder()
      .startingAfter("dp_111")
      .limit(1)
      .finish();

    DisputesList result = chargehound.Disputes.list(params);

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdate() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap();
    disputeUpdate.fields.put("key", "value");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"fields\":{\"key\":\"value\"}}", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);


    Dispute result = chargehound.Disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeSubmit() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
      .template("template")
      .finish();

    MockHttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123/submit");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {

            assertEquals("{\"template\":\"template\"}", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    MockLowLevelHttpRequest request = transport.getLowLevelHttpRequest();

    Dispute disputeResult = chargehound.Disputes.submit(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(disputeResult));
  }

  @Test public void testDisputeSubmitNoParams() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123/submit");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {

            assertEquals("", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.submit(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeCreate() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.CreateParams disputeCreate = new Dispute.CreateParams.Builder()
      .id("dp_123")
      .kind("chargeback")
      .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "POST");
        assertEquals(url, "http://test.test.com/v1/disputes");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"id\":\"dp_123\",\"kind\":\"chargeback\"}", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.create(testDispute.id, disputeCreate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testResponseObject() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap();
    disputeUpdate.fields.put("key", "value");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"fields\":{\"key\":\"value\"}}", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.Disputes.update(testDispute.id, disputeUpdate);

    assertTrue(result.response instanceof HttpResponse);
    assertEquals((Integer) 200, (Integer) result.response.getStatusCode());
  }
}
