package com.chargehound.resources;

import com.chargehound.Chargehound;
import com.chargehound.models.Dispute;
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

  public Dispute retrieve(String id) {
    return this.retrieve(id, Collections.emptyMap());
  }

  /**
   * Retrieve a dispute
   * This endpoint will return a single dispute.
   */
  public Dispute retrieve (String id, Map<String, String> params) {
    try {
      HttpResponse response = this.client.request(
        "GET",
        "/disputes/" + id,
        params,
        Collections.emptyMap()
      );

      Dispute dispute = response.parseAs(Dispute.class);
      return dispute;
    } catch (IOException e) {
      return null;
    }
  }
}
