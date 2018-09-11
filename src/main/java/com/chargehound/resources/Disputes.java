package com.chargehound.resources;

import com.chargehound.Chargehound;
import com.chargehound.errors.ChargehoundException;
import com.chargehound.errors.ChargehoundExceptionFactory;
import com.chargehound.models.Dispute;
import com.chargehound.models.DisputesList;
import com.chargehound.net.ApiRequestor;
import com.google.api.client.http.HttpResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class Disputes {
  private ApiRequestor client;

  private static final ChargehoundExceptionFactory
      ERROR_FACTORY = new ChargehoundExceptionFactory();

  // Creates a new disputes resource
  public Disputes(Chargehound chargehound) {
    this.client = new ApiRequestor(chargehound);
  }

  /**
   * Retrieve a dispute.
   * This endpoint will return a single dispute.
   * @param id A dispute id
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
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
   * Retrieve the response for a dispute once it is available
   * @param id A dispute id
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */
  public Dispute.Response response(String id) throws ChargehoundException {
    HttpResponse httpResponse = this.client.request(
        "GET",
        "/disputes/" + id + "/response"
    );

    try {
      Dispute.Response disputeResponse = httpResponse.parseAs(Dispute.Response.class);
      disputeResponse.response = httpResponse;
      return disputeResponse;
    } catch (IOException e) {
      throw ERROR_FACTORY.genericChargehoundException(e);
    }
  }

  /**
   * Retrieve a list of disputes.
   * @return DisputesList
   * @throws ChargehoundException Exception on failed API request
   */
  public DisputesList list() throws ChargehoundException {
    return this.list(new DisputesList.Params());
  }

  /**
   * Retrieve a list of disputes.
   * This endpoint will list all the disputes that we have synced.
   * By default the disputes will be ordered by `created` with the most recent dispute first.
   * `has_more` will be `true` if more results are available.
   * @param params Query parameters for the list of disputes.
   * @return DisputesList
   * @throws ChargehoundException Exception on failed API request
   */
  public DisputesList list(DisputesList.Params params) throws ChargehoundException {
    if (params == null) {
      return this.list();
    }

    HttpResponse httpResponse = this.client.request(
        "GET",
        "/disputes",
        params.asStringMap()
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
   * Accepting a dispute
   * Accept a dispute if you do not want to submit a response
   * @param id A dispute id
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */
  public Dispute accept(String id) throws ChargehoundException {
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
   * Updating a dispute
   * You can update the template and the fields on a dispute.
   * @param id A dispute id
   * @param update  dispute update object
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */
  public Dispute update(String id, Dispute.UpdateParams update) throws ChargehoundException {
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

  /**
   * Submitting a dispute
   * You will want to submit the dispute through Chargehound after you recieve a webhook notification of a new dispute.
   * With one `POST` request you can update a dispute with the evidence fields and submit the response.
   * The response will have a `201` status if the submit was successful. The dispute will also be in the submitted state.
   * @param id A dispute id
   * @param update A dispute update object
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */

  public Dispute submit(String id, Dispute.UpdateParams update) throws ChargehoundException {
    HttpResponse response = this.client.request(
        "POST",
        "/disputes/" + id + "/submit",
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

  /**
   * Submitting a dispute
   * You will want to submit the dispute through Chargehound after you recieve a webhook notification of a new dispute.
   * With one `POST` request you can update a dispute with the evidence fields and submit the response.
   * The response will have a `201` status if the submit was successful. The dispute will also be in the submitted state.
   * @param id A dispute id
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */
  public Dispute submit(String id) throws ChargehoundException {
    return this.submit(id, null);
  }

  /**
   * Create a dispute
   * This endpoint will return a single dispute.
   * @param create A dispute create object
   * @return Dispute
   * @throws ChargehoundException Exception on failed API request
   */
  public Dispute create(Dispute.CreateParams create) throws ChargehoundException {
    HttpResponse response = this.client.request(
        "POST",
        "/disputes",
        null,
        create
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
