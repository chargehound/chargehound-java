package com.chargehound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.chargehound.Chargehound;
import com.chargehound.errors.ChargehoundException;
import com.chargehound.models.Dispute;
import com.chargehound.models.DisputesList;
import com.chargehound.models.Email;
import com.chargehound.models.Product;
import com.chargehound.models.PastPayment;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;


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

    final Dispute testDispute = new Dispute();
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

    Dispute result = chargehound.disputes.retrieve(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeRetrieveWithAccount() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testDispute.account = "payments-us";

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

    Dispute result = chargehound.disputes.retrieve(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeRetrieveWithProductsQuantity() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

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
            result.setContent("{\"id\":\"dp_123\",\"kind\":\"chargeback\",\"products\":[{\"quantity\":10}]}");
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.retrieve("dp_123");

    assertEquals("{\"amount\":0,\"fee\":0,\"id\":\"dp_123\",\"is_charge_refundable\":false,\"kind\":\"chargeback\",\"livemode\":false,\"products\":[{\"quantity\":10}],\"reversal_amount\":0,\"submitted_count\":0}", JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeRetrieveWithLongAmount() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

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
            result.setContent("{\"id\":\"dp_123\",\"kind\":\"chargeback\",\"amount\":9999900000}");
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.retrieve("dp_123");

    System.out.println(result);

    assertEquals("{\"amount\":9999900000,\"fee\":0,\"id\":\"dp_123\",\"is_charge_refundable\":false,\"kind\":\"chargeback\",\"livemode\":false,\"reversal_amount\":0,\"submitted_count\":0}", JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeRetrieveWithProductsQuantityString() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

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
            result.setContent("{\"id\":\"dp_123\",\"kind\":\"chargeback\",\"products\":[{\"quantity\":\"10\"}]}");
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.retrieve("dp_123");

    assertEquals("{\"amount\":0,\"fee\":0,\"id\":\"dp_123\",\"is_charge_refundable\":false,\"kind\":\"chargeback\",\"livemode\":false,\"products\":[{\"quantity\":\"10\"}],\"reversal_amount\":0,\"submitted_count\":0}", JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeResponse() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute.Response testResponse = new Dispute.Response();
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

    Dispute.Response result = chargehound.disputes.response(testResponse.disputeId);

    assertEquals(JSON_FACTORY.toString(testResponse), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeAccept() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
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

    Dispute result = chargehound.disputes.accept(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesList() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList<Dispute>();
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

    DisputesList result = chargehound.disputes.list();

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesListWithParams() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList<Dispute>();
    testList.data.add(testDispute);

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals("GET", method);
        assertEquals("http://test.test.com/v1/disputes?limit=1&starting_after=dp_111", url);

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

    DisputesList result = chargehound.disputes.list(params);

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesListState() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList<Dispute>();
    testList.data.add(testDispute);

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes?state=needs_response");

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
            .state("needs_response")
            .finish();

    DisputesList result = chargehound.disputes.list(params);

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputesListMultipleStates() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final DisputesList testList = new DisputesList();
    Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";
    testList.data = new ArrayList<Dispute>();
    testList.data.add(testDispute);

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "GET");
        assertEquals(url, "http://test.test.com/v1/disputes?state=needs_response&state=warning_needs_response");

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
            .state("needs_response", "warning_needs_response")
            .finish();

    DisputesList result = chargehound.disputes.list(params);

    assertEquals(JSON_FACTORY.toString(testList), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdate() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap<String, Object>();
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

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdateWithSubmit() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder().submit(true).finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"submit\":true}", this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }


  @Test public void testDisputeUpdateProduct() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Product product = new Product.Builder()
            .name("T-shirt")
            .amount(100L)
            .finish();

    ArrayList<Product> products = new ArrayList<Product>();
    products.add(product);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .products(products)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"products\":[{\"amount\":100,\"name\":\"T-shirt\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdateProductQuantity() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Product product = new Product.Builder()
            .name("T-shirt")
            .amount(100L)
            .quantity(5)
            .finish();

    ArrayList<Product> products = new ArrayList<Product>();
    products.add(product);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .products(products)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"products\":[{\"amount\":100,\"name\":\"T-shirt\",\"quantity\":5}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdateProductQuantityString() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Product product = new Product.Builder()
            .name("T-shirt")
            .amount(100L)
            .quantity("5oz")
            .finish();

    ArrayList<Product> products = new ArrayList<Product>();
    products.add(product);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .products(products)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"products\":[{\"amount\":100,\"name\":\"T-shirt\",\"quantity\":\"5oz\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdateCorrespondence() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Email email = new Email.Builder()
            .to("customer@example.com")
            .from("noreply@example.com")
            .body("Your order was received.")
            .finish();

    ArrayList<Email> emails = new ArrayList<Email>();
    emails.add(email);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .correspondence(emails)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"correspondence\":[{\"body\":\"Your order was received.\",\"from\":\"noreply@example.com\",\"to\":\"customer@example.com\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdateProductWithShipment() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Product product = new Product.Builder()
            .name("T-shirt")
            .amount(100L)
            .shippingCarrier("fedex")
            .shippingTrackingNumber("12345678")
            .finish();

    ArrayList<Product> products = new ArrayList<Product>();
    products.add(product);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .products(products)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"products\":[{\"amount\":100,\"name\":\"T-shirt\",\"shipping_carrier\":\"fedex\",\"shipping_tracking_number\":\"12345678\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdatePastPayments() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    PastPayment pastPayment = new PastPayment.Builder()
            .id("ch_1")
            .amount(100L)
            .currency("usd")
            .chargedAt("2019-09-03 11:09:41PM UTC")
            .finish();

    ArrayList<PastPayment> pastPayments = new ArrayList<PastPayment>();
    pastPayments.add(pastPayment);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .pastPayments(pastPayments)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals("{\"past_payments\":[{\"amount\":100,\"charged_at\":\"2019-09-03 11:09:41PM UTC\",\"currency\":\"usd\",\"id\":\"ch_1\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeUpdatePastPaymentsUnix() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    // Adding additional fields
    PastPayment pastPayment = new PastPayment.Builder()
            .id("ch_1")
            .amount(100L)
            .currency("usd")
            .chargedAt(1568831857)
            .userId("test123@gmail.com")
            .ipAddress("127.1.2.3")
            .shippingAddress("194 Orchad Avenue")
            .deviceId("1234-4567-2324-35-45")
            .finish();

    ArrayList<PastPayment> pastPayments = new ArrayList<PastPayment>();
    pastPayments.add(pastPayment);

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .pastPayments(pastPayments)
            .finish();

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            System.out.println("printing output: ");
            System.out.println(this.getContentAsString());
            assertEquals("{\"past_payments\":[{\"amount\":100,\"charged_at\":1568831857," +
                            "\"currency\":\"usd\",\"device_id\":\"1234-4567-2324-35-45\",\"id\":\"ch_1\"," +
                            "\"ip_address\":\"127.1.2.3\",\"shipping_address\":\"194 Orchad Avenue\"," +
                            "\"user_id\":\"test123@gmail.com\"}]}",
                    this.getContentAsString());

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testChargehoundHeaders() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");
    chargehound.setApiVersion("OverrideVersion");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap<String, Object>();
    disputeUpdate.fields.put("key", "value");

    final String chargehoundUserAgent = "Chargehound/v1 JavaBindings/" + Chargehound.VERSION;

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals(chargehoundUserAgent, this.getHeaders().get("user-agent").get(0));
            assertEquals("OverrideVersion", this.getHeaders().get("chargehound-version").get(0));

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testChargehoundHeadersNoVersion() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap<String, Object>();
    disputeUpdate.fields.put("key", "value");

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "PUT");
        assertEquals(url, "http://test.test.com/v1/disputes/dp_123");

        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            assertEquals(null, this.getHeaders().get("chargehound-version"));

            MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
            result.setContentType(Json.MEDIA_TYPE);
            result.setContent(JSON_FACTORY.toString(testDispute));
            return result;
          }
        };
      }
    };

    chargehound.setHttpTransport(transport);

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeSubmit() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams.Builder()
            .template("template")
            .finish();

    MockHttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "POST");
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

    Dispute disputeResult = chargehound.disputes.submit(testDispute.id, disputeUpdate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(disputeResult));
  }

  @Test public void testDisputeSubmitNoParams() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        assertEquals(method, "POST");
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

    Dispute result = chargehound.disputes.submit(testDispute.id);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testDisputeCreate() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
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

    Dispute result = chargehound.disputes.create(disputeCreate);

    assertEquals(JSON_FACTORY.toString(testDispute), JSON_FACTORY.toString(result));
  }

  @Test public void testResponseObject() throws IOException, ChargehoundException {
    Chargehound chargehound = new Chargehound("test_123");
    chargehound.setApiProtocol("http://");
    chargehound.setApiHost("test.test.com");

    final Dispute testDispute = new Dispute();
    testDispute.id = "dp_123";
    testDispute.kind = "chargeback";

    Dispute.UpdateParams disputeUpdate = new Dispute.UpdateParams();
    disputeUpdate.fields = new HashMap<String, Object>();
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

    Dispute result = chargehound.disputes.update(testDispute.id, disputeUpdate);

    assertTrue(result.response instanceof HttpResponse);
    assertEquals((Integer) 200, (Integer) result.response.getStatusCode());
  }
}