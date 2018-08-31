package com.chargehound.net;

import com.chargehound.Chargehound;
import com.chargehound.errors.ChargehoundException;
import com.chargehound.errors.ChargehoundExceptionFactory;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public class ApiRequestor {
  private Chargehound chargehound;

  // TODO: get configured version
  public static final String CHARGEHOUND_USERAGENT = "Chargehound/v1 JavaBindings/";

  static final JsonFactory JSON_FACTORY = new JacksonFactory();
  static final ChargehoundExceptionFactory ERROR_FACTORY = new ChargehoundExceptionFactory();

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

  private GenericUrl getUrl (String path, Map<String, String> params) throws ChargehoundException {
    if (params == null) {
      return this.getUrl(path);
    }
    try {
      String query = this.getParamsString(params);
      if (query.length() > 0) {
        query = "?" + query;
      }
      return new GenericUrl(this.chargehound.getApiBase() + path + query);
    } catch (UnsupportedEncodingException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  private GenericUrl getUrl (String path) throws ChargehoundException {
    this.getUrl(path, Collections.emptyMap());
  }

  private HttpContent getContent (GenericJson data) {
    if (data == null) {
      return null;
    }

    return new JsonHttpContent(JSON_FACTORY, data);
  }

  public HttpResponse request (String method, String path, Map<String, String> params, GenericJson data) throws ChargehoundException {
    HttpTransport transport = this.chargehound.getHttpTransport();
    String apiVersion = this.chargehound.getApiVersion();
    int connectTimeout = this.chargehound.getHttpConnectTimeout();
    int readTimeout = this.chargehound.getHttpReadTimeout();

    HttpRequestFactory requestFactory =
        transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
          public void initialize(HttpRequest request) {
            request.setConnectTimeout(connectTimeout * 1000);
            request.setReadTimeout(readTimeout * 1000);

            HttpHeaders headers = request.getHeaders();
            headers.setContentType("application/json");
            headers.setUserAgent(CHARGEHOUND_USERAGENT);

            if (apiVersion != null) {
              headers.set("chargehound-version", apiVersion);
            }

            request.setParser(new JsonObjectParser(JSON_FACTORY));
          }
        });

    GenericUrl url = this.getUrl(path, params);
    HttpContent content = this.getContent(data);

    try {
      HttpRequest request = requestFactory.buildRequest(method, url, content);
      HttpResponse response = request.execute();

      return response;
    } catch (HttpResponseException error) {
      throw ERROR_FACTORY.httpResponseException(error);
    } catch (IOException error) {
      throw ERROR_FACTORY.genericChargehoundException(error);
    }
  }

  public HttpResponse request (String method, String path) throws ChargehoundException {
    return this.request(method, path, Collections.emptyMap(), Collections.emptyMap());
  }

  public HttpResponse request (String method, String path, Map<String, String> params) throws ChargehoundException {
    return this.request(method, path, params, Collections.emptyMap());
  }
}
