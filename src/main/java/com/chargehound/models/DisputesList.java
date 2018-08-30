package com.chargehound.models;

import com.chargehound.models.Dispute;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

// The type returned by a list disputes request. See https://www.chargehound.com/docs/api/index.html#retrieving-a-list-of-disputes.
public class DisputesList extends GenericJson {
  @Key("data")
  public List<Dispute> data;

  @Key("has_more")
  public Boolean hasMore;

  @Key("livemode")
  public Boolean livemode;

  @Key("object")
  public String object;

  @Key("url")
  public String url;

  public HttpResponse response;
}
