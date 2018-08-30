package com.chargehound.resources;

import com.chargehound.Chargehound;
import com.chargehound.models.Dispute;
import com.chargehound.models.DisputesList;
import com.chargehound.models.Response;
import com.chargehound.net.ApiRequestor;
import com.google.api.client.http.HttpResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class Disputes {
  private ApiRequestor client;

  // Creates a new disputes resource
  public Disputes(Chargehound chargehound) {
    this.client = new ApiRequestor(chargehound);
  }

  /**
   * Retrieve a dispute
   * This endpoint will return a single dispute.
   */
  public Dispute retrieve(String id) {
    try {
      HttpResponse response = this.client.request(
        "GET",
        "/disputes/" + id
      );

      Dispute dispute = response.parseAs(Dispute.class);
      dispute.response = response;
      return dispute;
    } catch (IOException e) {
      // TODO: chargehound errors
      System.out.println(e);
      return null;
    }
  }

  /**
   * Retrieve a dispute response
   */
  public Response response (String id) {
    try {
      HttpResponse httpResponse = this.client.request(
        "GET",
        "/disputes/" + id + "/response"
      );

      Response disputeResponse = httpResponse.parseAs(Response.class);
      disputeResponse.response = httpResponse;
      return disputeResponse;
    } catch (IOException e) {
      // TODO: chargehound errors
      System.out.println(e);
      return null;
    }
  }

  /**
   * Retrieve a list of disputes
   */
  public DisputesList list () {
    return this.list(Collections.emptyMap());
  }

  /**
   * Retrieve a list of disputes
   */
  public DisputesList list (Map<String, String> params) {
    if (params == null) {
      return this.list();
    }

    try {
      HttpResponse httpResponse = this.client.request(
        "GET",
        "/disputes",
        params
      );

      DisputesList disputesList = httpResponse.parseAs(DisputesList.class);
      disputesList.response = httpResponse;
      return disputesList;
    } catch (IOException e) {
      // TODO: chargehound errors
      System.out.println(e);
      return null;
    }
  }
}
