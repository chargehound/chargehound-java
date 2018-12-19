package com.chargehound.models;

import com.chargehound.models.Dispute;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

// The type returned by a list disputes request. See https://www.chargehound.com/docs/api/index.html#retrieving-a-list-of-disputes.
public class DisputesList extends GenericJson {
  @Key("data")
  public List<Dispute> data;

  @Key("has_more")
  public boolean hasMore;

  @Key("livemode")
  public boolean livemode;

  @Key("object")
  public String object;

  @Key("url")
  public String url;

  public HttpResponse response;

  // Params for a list disputes request. See https://www.chargehound.com/docs/api/index.html#retrieving-a-list-of-disputes.
  public static class Params extends GenericJson {
    @Key("limit")
    public Integer limit;

    @Key("starting_after")
    public String startingAfter;

    @Key("ending_before")
    public String endingBefore;

    @Key("state")
    public List<String> state;

    public Params() {}

    private Params(
        final Integer limit,
        final String startingAfter,
        final String endingBefore,
        final List<String> state
    ) {
      this.limit = limit;
      this.startingAfter = startingAfter;
      this.endingBefore = endingBefore;
      this.state = state;
    }

    public static class Builder {
      private Integer limit;
      private String startingAfter;
      private String endingBefore;
      public List<String> state;

      public Builder limit(final Integer limit) {
        this.limit = limit;
        return this;
      }

      public Builder startingAfter(final String startingAfter) {
        this.startingAfter = startingAfter;
        return this;
      }

      public Builder endingBefore(final String endingBefore) {
        this.endingBefore = endingBefore;
        return this;
      }

      public Builder state(final String... state) {
        this.state = Arrays.asList(state);
        return this;
      }

      /**
       * Finish the builder.
       * @return Params
       */
      public Params finish() {
        return new Params(
          this.limit,
          this.startingAfter,
          this.endingBefore,
          this.state
        );
      }
    }
  }
}
