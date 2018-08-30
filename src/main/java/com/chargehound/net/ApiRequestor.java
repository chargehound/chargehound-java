package com.chargehound.net;

import com.chargehound.Chargehound;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ApiRequestor {
  private Chargehound chargehound;

  // TODO: get configured version
  public static final String CHARGEHOUND_USERAGENT = "Chargehound/v1 JavaBindings/";

  static final JsonFactory JSON_FACTORY = new JacksonFactory();

  public ApiRequestor (Chargehound chargehound) {
    this.chargehound = chargehound;
  }

  public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();

    for (Map.Entry<String, String> entry : params.entrySet()) {
      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      result.append("&");
    }

    String resultString = result.toString();
    return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
  }

  private GenericUrl getUrl (String path, Map<String, String> params) {
    try {
      String query = this.getParamsString(params);
      if (query.length() > 0) {
        query = "?" + query;
      }
      return new GenericUrl(this.chargehound.getApiBase() + path + query);
    } catch (UnsupportedEncodingException e) {
      return new GenericUrl(this.chargehound.getApiBase() + path);
    }
  }

  public HttpResponse request (String method, String path, Map<String, String> params, Map<String, String> data) throws IOException {
    HttpTransport transport = this.chargehound.getHttpTransport();

    HttpRequestFactory requestFactory =
        transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
          public void initialize(HttpRequest request) {
            HttpHeaders headers = request.getHeaders();
            headers.setContentType("application/json");
            headers.setUserAgent(CHARGEHOUND_USERAGENT);
            headers.set("chargehound-version", "TODO: get the version here");

            request.setParser(new JsonObjectParser(JSON_FACTORY));
          }
        });
    GenericUrl url = this.getUrl(path, params);
    HttpRequest request = requestFactory.buildGetRequest(url);
    HttpResponse response = request.execute();

    return response;
  }
}
