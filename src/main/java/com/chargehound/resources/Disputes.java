package com.chargehound.resources;

import com.chargehound.Chargehound;
import com.chargehound.errors.ChargehoundException;
import com.chargehound.errors.ChargehoundExceptionFactory;
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

  static final ChargehoundExceptionFactory ERROR_FACTORY = new ChargehoundExceptionFactory();

  // Creates a new disputes resource
  public Disputes(Chargehound chargehound) {
    this.client = new ApiRequestor(chargehound);
  }

  /**
   * Retrieve a dispute
   * This endpoint will return a single dispute.
   */
  public Dispute retrieve(String id) throws ChargehoundException {
    HttpResponse response = this.client.request(
      "GET",
      "/disputes/" + id
    );

    try {
      Dispute dispute = response.parseAs(Dispute.class);
      dispute.response = response;
      return dispute;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  /**
   * Retrieve a dispute response
   */
  public Response response (String id) throws ChargehoundException {
    HttpResponse httpResponse = this.client.request(
      "GET",
      "/disputes/" + id + "/response"
    );

    try {
      Response disputeResponse = httpResponse.parseAs(Response.class);
      disputeResponse.response = httpResponse;
      return disputeResponse;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  /**
   * Retrieve a list of disputes
   */
  public DisputesList list () throws ChargehoundException {
    return this.list(Collections.emptyMap());
  }

  /**
   * Retrieve a list of disputes
   */
  public DisputesList list (Map<String, String> params) throws ChargehoundException {
    if (params == null) {
      return this.list();
    }

    HttpResponse httpResponse = this.client.request(
      "GET",
      "/disputes",
      params
    );

    try {
      DisputesList disputesList = httpResponse.parseAs(DisputesList.class);
      disputesList.response = httpResponse;
      return disputesList;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  /**
   * Accept a dispute if you do not want to submit a response
   */
  public Dispute accept (String id) throws ChargehoundException {
    HttpResponse response = this.client.request(
      "POST",
      "/disputes/" + id + "/accept"
    );

    try {
      Dispute dispute = response.parseAs(Dispute.class);
      dispute.response = response;
      return dispute;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  /**
   * Update the template and the fields on a dispute.
   */
  public Dispute update (String id, Dispute.UpdateParams update) {
    HttpResponse response = this.client.request(
      "PUT",
      "/disputes/" + id,
      null,
      update
    );

    try {
      Dispute dispute = response.parseAs(Dispute.class);
      dispute.response = response;
      return dispute;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }
}
