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
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;

import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.List;

public class ApiRequestor {
  private Chargehound chargehound;

  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final ChargehoundExceptionFactory
      ERROR_FACTORY = new ChargehoundExceptionFactory();
  private static final String
      CHARGEHOUND_USER_AGENT = "Chargehound/v1 JavaBindings/" + Chargehound.VERSION;

  public ApiRequestor(Chargehound chargehound) {
    this.chargehound = chargehound;
  }

  private static String getParamsString(
      Map<String, Object> params
  ) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();

    for (Map.Entry<String, Object> entry : params.entrySet()) {
      if (entry.getValue() instanceof List) {
        for (Object value: (List) entry.getValue()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(value.toString(), "UTF-8"));
          result.append("&");
        }
      } else {
        result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        result.append("&");
      }
    }

    String resultString = result.toString();
    if (resultString.length() > 0) {
      return resultString.substring(0, resultString.length() - 1);
    } else {
      return resultString;
    }
  }

  private GenericUrl getUrl(String path, Map<String, Object> params) throws ChargehoundException {
    if (params == null) {
      return this.getUrl(path);
    }
    try {
      String query = ApiRequestor.getParamsString(params);
      if (query.length() > 0) {
        query = "?" + query;
      }
      return new GenericUrl(this.chargehound.getApiBase() + path + query);
    } catch (UnsupportedEncodingException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  private GenericUrl getUrl(String path) throws ChargehoundException {
    Map<String, Object> emptyMap = Collections.emptyMap();
    return this.getUrl(path, emptyMap);
  }

  private HttpContent getContent(GenericJson data) {
    if (data == null) {
      return null;
    }

    return new JsonHttpContent(JSON_FACTORY, data);
  }

  /**
   * Make an http request to the Chargehound API.
   * @param method the http method
   * @param path the URL path
   * @param params the query parameters
   * @param data the request body data
   * @return HttpResponse
   * @throws ChargehoundException Exception on failed API request
   */
  public HttpResponse request(
      String method,
      String path,
      Map<String, Object> params,
      GenericJson data
  ) throws ChargehoundException {
    HttpTransport transport = this.chargehound.getHttpTransport();
    final String apiVersion = this.chargehound.getApiVersion();
    final String apiKey = this.chargehound.getApiKey();
    final int connectTimeout = this.chargehound.getHttpConnectTimeout();
    final int readTimeout = this.chargehound.getHttpReadTimeout();

    HttpRequestFactory requestFactory =
        transport.createRequestFactory(new HttpRequestInitializer() {
            @Override
          public void initialize(HttpRequest request) {
            request.setConnectTimeout(connectTimeout * 1000);
            request.setReadTimeout(readTimeout * 1000);
            request.setSuppressUserAgentSuffix(true);

            HttpHeaders headers = request.getHeaders();
            headers.setContentType("application/json");
            headers.setUserAgent(CHARGEHOUND_USER_AGENT);
            headers.setBasicAuthentication(apiKey, "");

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

  /**
   * Make an http request to the Chargehound API.
   * @param method the http method
   * @param path the URL path
   * @return HttpResponse
   * @throws ChargehoundException Exception on failed API request
   */
  public HttpResponse request(String method, String path) throws ChargehoundException {
    Map<String, Object> emptyMap = Collections.emptyMap();
    return this.request(method, path, emptyMap, null);
  }

  /**
   * Make an http request to the Chargehound API.
   * @param method the http method
   * @param path the URL path
   * @param params the query parameters
   * @return HttpResponse
   * @throws ChargehoundException Exception on failed API request
   */
  public HttpResponse request(
      String method,
      String path,
      Map<String, Object> params
  ) throws ChargehoundException {
    return this.request(method, path, params, null);
  }
}
