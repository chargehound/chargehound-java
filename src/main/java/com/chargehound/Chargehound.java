package com.chargehound;

import com.chargehound.resources.Disputes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class Chargehound {
  private HttpTransport httpTransport = new NetHttpTransport();
  private int httpConnectTimeout = 30;
  private int httpReadTimeout = 30;
  private String apiVersion = null;
  private String basepath = "/v1";
  private String host = "api.chargehound.com";
  private String key;
  private String protocol = "https://";

  public static final String VERSION = "2.1.1";
  public Disputes disputes;

  // Creates a new chargehound client with the specified api key and the default configuration.
  public Chargehound(String key) {
    this.key = key;
    this.disputes = new Disputes(this);
  }

  // Override the host protocol, for testing
  public void setApiProtocol(String protocol) {
    this.protocol = protocol;
  }

  // Override the host, for testing
  public void setApiHost(String host) {
    this.host = host;
  }

  // Override the API base path, for testing
  public void setApiBasepath(String basepath) {
    this.basepath = basepath;
  }

  // Get the API base path
  public String getApiBase() {
    return this.protocol + this.host + this.basepath;
  }

  // Override the API version
  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  // Get the API version
  public String getApiVersion() {
    return this.apiVersion;
  }

  // Set the HTTP request connect timeout, in seconds
  public void setHttpConnectTimeout(int httpConnectTimeout) {
    this.httpConnectTimeout = httpConnectTimeout;
  }

  // Set the HTTP request read timeout, in seconds
  public void setHttpReadTimeout(int httpReadTimeout) {
    this.httpReadTimeout = httpReadTimeout;
  }

  // Get the HTTP request connect
  public int getHttpConnectTimeout() {
    return this.httpConnectTimeout;
  }

  // Get the HTTP read connect
  public int getHttpReadTimeout() {
    return this.httpReadTimeout;
  }

  // Set the HTTP transport
  public void setHttpTransport(HttpTransport transport) {
    this.httpTransport = transport;
  }

  // Get the HTTP transport
  public HttpTransport getHttpTransport() {
    return this.httpTransport;
  }

  // Get the API key
  public String getApiKey() {
    return this.key;
  }
}