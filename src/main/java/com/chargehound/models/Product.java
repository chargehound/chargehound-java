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
}
