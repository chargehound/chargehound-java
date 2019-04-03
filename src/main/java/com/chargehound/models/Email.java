package com.chargehound.models;

import com.chargehound.models.CorrespondenceItem;
import com.google.api.client.util.Key;

// Dispute correspondence data See https://www.chargehound.com/docs/api/index.html#correspondence-data.
public class Email extends CorrespondenceItem {
  @Key("to")
  String to;

  @Key("from")
  String from;

  @Key("sent")
  String sent;

  @Key("subject")
  String subject;

  @Key("body")
  String body;

  @Key("caption")
  String caption;

  public Email() {}

  private Email(
      final String to,
      final String from,
      final String sent,
      final String subject,
      final String body,
      final String caption
  ) {
    this.to = to;
    this.from = from;
    this.sent = sent;
    this.subject = subject;
    this.body = body;
    this.caption = caption;
  }

  public static class Builder {
    private String to;
    private String from;
    private String sent;
    private String subject;
    private String body;
    private String caption;

    public Builder to(final String to) {
      this.to = to;
      return this;
    }

    public Builder from(final String from) {
      this.from = from;
      return this;
    }

    public Builder sent(final String sent) {
      this.sent = sent;
      return this;
    }

    public Builder subject(final String subject) {
      this.subject = subject;
      return this;
    }

    public Builder body(final String body) {
      this.body = body;
      return this;
    }

    public Builder caption(final String caption) {
      this.caption = caption;
      return this;
    }
    /**
     * Finish the builder.
     * @return Product
     */
    public Email finish() {
      return new Email(
        this.to,
        this.from,
        this.sent,
        this.subject,
        this.body,
        this.caption
      );
    }
  }
}
