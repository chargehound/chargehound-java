package com.chargehound.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

// Customer past payment history See https://www.chargehound.com/docs/api/index.html#past-payments.
public class PastPayment extends GenericJson {
  @Key("id")
  String id;

  @Key("amount")
  Integer amount;

  @Key("currency")
  String currency;

  @Key("charged_at")
  Object chargedAt;

  public PastPayment() {}

  private PastPayment(
      final String id,
      final Integer amount,
      final String currency,
      final Object chargedAt
  ) {
    this.id = id;
    this.amount = amount;
    this.currency = currency;
    this.chargedAt = chargedAt;
  }

  public static class Builder {
    private String id;
    private Integer amount;
    private String currency;
    private Object chargedAt;

    public Builder id(final String id) {
      this.id = id;
      return this;
    }

    public Builder amount(final Integer amount) {
      this.amount = amount;
      return this;
    }

    public Builder currency(final String currency) {
      this.currency = currency;
      return this;
    }

    public Builder chargedAt(final Object chargedAt) {
      this.chargedAt = chargedAt;
      return this;
    }

    /**
     * Finish the builder.
     * @return PastPayment
     */
    public PastPayment finish() {
      return new PastPayment(
        this.id,
        this.amount,
        this.currency,
        this.chargedAt
      );
    }
  }
}
