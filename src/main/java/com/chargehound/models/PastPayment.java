package com.chargehound.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

// Customer past payment history See https://www.chargehound.com/docs/api/index.html#past-payments.
public class PastPayment extends GenericJson {
  @Key("id")
  public String id;

  @Key("amount")
  public Long amount;

  @Key("currency")
  public String currency;

  @Key("charged_at")
  public Object chargedAt;

  @Key("user_id")
  public String userId;

  @Key("ip_address")
  public String ipAddress;

  @Key("shipping_address")
  public String shippingAddress;

  @Key("device_id")
  public String deviceId;

  public PastPayment() {}

  private PastPayment(
          final String id,
          final Long amount,
          final String currency,
          final Object chargedAt,
          final String userId,
          final String ipAddress,
          final String shippingAddress,
          final String deviceId
  ) {
    this.id = id;
    this.amount = amount;
    this.currency = currency;
    this.chargedAt = chargedAt;
    this.userId = userId;
    this.ipAddress = ipAddress;
    this.shippingAddress = shippingAddress;
    this.deviceId = deviceId;
  }

  public static class Builder {
    private String id;
    private Long amount;
    private String currency;
    private Object chargedAt;
    private String userId;
    private String ipAddress;
    private String shippingAddress;
    private String deviceId;

    public Builder id(final String id) {
      this.id = id;
      return this;
    }

    public Builder amount(final Long amount) {
      this.amount = amount;
      return this;
    }

    public Builder currency(final String currency) {
      this.currency = currency;
      return this;
    }

    public Builder chargedAt(final String chargedAt) {
      this.chargedAt = chargedAt;
      return this;
    }

    public Builder chargedAt(final Integer chargedAt) {
      this.chargedAt = chargedAt;
      return this;
    }

    public Builder userId(final String userId) {
      this.userId = userId;
      return this;
    }

    public Builder ipAddress(final String ipAddress) {
      this.ipAddress = ipAddress;
      return this;
    }

    public Builder shippingAddress(final String shippingAddress) {
      this.shippingAddress = shippingAddress;
      return this;
    }

    public Builder deviceId(final String deviceId) {
      this.deviceId = deviceId;
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
              this.chargedAt,
              this.userId,
              this.ipAddress,
              this.shippingAddress,
              this.deviceId
      );
    }
  }
}