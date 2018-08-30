package com.chargehound.models;

import com.chargehound.models.Dispute;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

// The type returned by a dispute response request.
public class Response extends GenericJson {
  @Key("livemode")
  public Boolean livemode;

  @Key("dispute_id")
  public String disputeId;

  @Key("external_charge")
  public String externalCharge;

  @Key("account_id")
  public String accountId;

  @Key("evidence")
  public Map<String, String> evidence;

  @Key("response_url")
  public String responseUrl;

  public HttpResponse response;
}
