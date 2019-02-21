package com.chargehound.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

// Dispute product data See https://www.chargehound.com/docs/api/index.html#product-data.
public class Product extends GenericJson {
  @Key("name")
  String name;

  @Key("description")
  String description;

  @Key("image")
  String image;

  @Key("sku")
  String sku;

  @Key("quantity")
  Integer quantity;

  @Key("amount")
  Integer amount;

  @Key("url")
  String url;

  @Key("shipping_carrier")
  String shippingCarrier;

  @Key("shipping_tracking_number")
  String shippingTrackingNumber;

  public Product() {}

  private Product(
      final String name,
      final String description,
      final String image,
      final String sku,
      final Integer quantity,
      final Integer amount,
      final String url,
      final String shippingCarrier,
      final String shippingTrackingNumber
  ) {
    this.name = name;
    this.description = description;
    this.image = image;
    this.sku = sku;
    this.quantity = quantity;
    this.amount = amount;
    this.url = url;
    this.shippingCarrier = shippingCarrier;
    this.shippingTrackingNumber = shippingTrackingNumber;
  }

  public static class Builder {
    private String name;
    private String description;
    private String image;
    private String sku;
    private Integer quantity;
    private Integer amount;
    private String url;
    private String shippingCarrier;
    private String shippingTrackingNumber;

    public Builder name(final String name) {
      this.name = name;
      return this;
    }

    public Builder account(final String description) {
      this.description = description;
      return this;
    }

    public Builder image(final String image) {
      this.image = image;
      return this;
    }

    public Builder sku(final String sku) {
      this.sku = sku;
      return this;
    }

    public Builder quantity(final Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder amount(final Integer amount) {
      this.amount = amount;
      return this;
    }

    public Builder url(final String url) {
      this.url = url;
      return this;
    }

    public Builder shippingCarrier(final String shippingCarrier) {
      this.shippingCarrier = shippingCarrier;
      return this;
    }

    public Builder shippingTrackingNumber(final String shippingTrackingNumber) {
      this.shippingTrackingNumber = shippingTrackingNumber;
      return this;
    }

    /**
     * Finish the builder.
     * @return Product
     */
    public Product finish() {
      return new Product(
        this.name,
        this.description,
        this.image,
        this.sku,
        this.quantity,
        this.amount,
        this.url,
        this.shippingCarrier,
        this.shippingTrackingNumber
      );
    }
  }
}
