package com.chargehound.models;

import com.chargehound.models.Product;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

// A dispute. See https://www.chargehound.com/docs/api/index.html#disputes.
public class Dispute extends GenericJson {
  // A unique identifier for the dispute. This id is set by the payment processor of the dispute.
  @Key("id")
  public String id;
  // State of the dispute. One of `needs_response`,`submitted`, `under_review`, `won`, `lost`, `warning_needs_response`, `warning_under_review`, `warning_closed` , `response_disabled`, `charge_refunded`, `accepted`, `queued`.
  @Key("state")
  public String state;
  // Reason for the dispute. One of `fraudulent`, `unrecognized`, `general`, `duplicate`, `subscription_canceled`, `product_unacceptable`, `product_not_received`, `credit_not_processed`, `incorrect_account_details`, `insufficient_funds`, `bank_cannot_process`, `debit_not_authorized`, `goods_services_returned_or_refused`, `goods_services_cancelled` |
  @Key("reason")
  public String reason;
  // ISO 8601 timestamp - when the charge was made.
  @Key("charged_at")
  public String chargedAt;
  // ISO 8601 timestamp - when the charge was disputed.
  @Key("disputed_at")
  public String disputedAt;
  // ISO 8601 timestamp - when dispute evidence needs to be disputed by.
  @Key("due_by")
  public String dueBy;
  // ISO 8601 timestamp - when dispute evidence was submitted.
  @Key("submitted_at")
  public String submittedAt;
  // ISO 8601 timestamp - when the dispute was resolved.
  @Key("closed_at")
  public String closedAt;
  // Number of times the dispute evidence has been submitted.
  @Key("submitted_count")
  public int submittedCount;
  // Location of the generated evidence document.
  @Key("file_url")
  public String fileURL;
  // Id of the template attached to the dispute.
  @Key("template")
  public String template;
  // Evidence fields attached to the dispute.
  @Key("fields")
  public Map<String, String> fields;
  // Any fields required by the template that have not yet been provided.
  @Key("missing_fields")
  public Map<String, String> missingFields;
  // (Optional) A list of products in the disputed order. (See [Product data](#product-data) for details.)
  @Key("products")
  public List<Product> products;
  // Id of the disputed charge.
  @Key("charge")
  public String charge;
  // Can the charge be refunded.
  @Key("is_charge_refundable")
  public Boolean isChargeRefundable;
  // Amount of the disputed charge. Amounts are in cents (or other minor currency unit.)
  @Key("amount")
  public int amount;
  // Currency code of the disputed charge. e.g. 'USD'.
  @Key("currency")
  public String currency;
  // Dispute fee.
  @Key("fee")
  public int fee;
  // The amount deducted due to the chargeback. Amounts are in cents (or other minor currency unit.)
  @Key("reversal_amount")
  public int reversalAmount;
  // Currency code of the deduction amount. e.g. 'USD'.
  @Key("reversal_currency")
  public String reversalCurrency;
  // Id of the customer (if any). This id is set by the payment processor of the dispute.
  @Key("customer")
  public String customer;
  // Name of the customer (if any).
  @Key("customer_name")
  public String customerName;
  // Email of the customer (if any).
  @Key("customer_email")
  public String customerEmail;
  // IP of purchase (if available).
  @Key("customer_purchase_ip")
  public String customerPurchaseIP;
  // Billing address zip of the charge.
  @Key("address_zip")
  public String addressZip;
  // State of address check (if available). One of `pass`, `fail`, `unavailable`, `checked`.
  @Key("address_line1_check")
  public String addressLine1Check;
  // State of address zip check (if available). One of `pass`, `fail`, `unavailable`, `checked`.
  @Key("address_zip_check")
  public String addressZipCheck;
  // State of cvc check (if available). One of `pass`, `fail`, `unavailable`, `checked`.
  @Key("cvc_check")
  public String cvcCheck;
  // The descriptor that appears on the customer's credit card statement for this change.
  @Key("statement_descriptor")
  public String statementDescriptor;
  // The account id for Connected accounts that are charged directly through Stripe (if any)
  @Key("user_id")
  public String userID;
  // The kind for the dispute, 'chargeback', 'retrieval' or 'pre_arbitration'.
  @Key("kind")
  public String kind;
  // ISO 8601 timestamp.
  @Key("created")
  public String created;
  // ISO 8601 timestamp.
  @Key("updated")
  public String updated;
  // The source of the dispute. One of `mock`, `braintree`, `api` or `stripe`
  @Key("source")
  public String source;
  // The payment processor of the dispute. One of `braintree` or `stripe`
  @Key("processor")
  public String processor;
  // Custom URL with dispute information.
  @Key("reference_url")
  public String referenceURL;
  @Key("livemode")
  public Boolean livemode;
  // Data about the API response that created dispute.
  public HttpResponse response;

  // Params for updating or submitting a dispute. See https://www.chargehound.com/docs/api/index.html#updating-a-dispute.
  public static class UpdateParams extends GenericJson {
    // Set the account id for Connected accounts that are charged directly through Stripe. (optional)
    @Key("user_id")
    public String userId;
    // Id of the connected account for this dispute (if multiple accounts are connected) (optional)
    @Key("account")
    public String account;
    // Bypass manual review. (optional)
    @Key("force")
    public Boolean force;
    // Queue dispute for submission. (optional)
    @Key("queue")
    public Boolean queue;
    // The id of the template to use. (optional)
    @Key("template")
    public String template;
    // Evidence fields attached to the dispute.
    @Key("fields")
    public Map<String, String> fields;
    // A list of products in the disputed order. (See [Product data](#product-data) for details.) (Optional)
    @Key("products")
    public List<Product> products;
    // Custom URL with dispute information.
    @Key("reference_url")
    public String referenceUrl;
  }

  public static class CreateParams extends GenericJson {
    // The id of the dispute in your payment processor. For Stripe looks like `dp_XXX`.
    @Key("id")
    public String id;
    // The id of the disputed charge in your payment processor. For Stripe looks like `ch_XXX`.
    @Key("charge")
    public String charge;
    // The id of the charged customer in your payment processor. For Stripe looks like `cus_XXX`. (optional)
    @Key("customer")
    public String customer;
    // The bank provided reason for the dispute. One of `general`, `fraudulent`, `duplicate`, `subscription_canceled`, `product_unacceptable`, `product_not_received`, `unrecognized`, `credit_not_processed`, `incorrect_account_details`, `insufficient_funds`, `bank_cannot_process`, `debit_not_authorized`.
    @Key("reason")
    public String reason;
    // ISO 8601 timestamp - when the charge was made.
    @Key("charged_at")
    public String chargedAt;
    // ISO 8601 timestamp - when the charge was disputed.
    @Key("disputed_at")
    public String disputedAt;
    // ISO 8601 timestamp - when dispute evidence needs to be disputed by.
    @Key("due_by")
    public String dueBy;
    // The currency code of the disputed charge. e.g. 'USD'.
    @Key("currency")
    public String currency;
    // The amount of the disputed charge. Amounts are in cents (or other minor currency unit.)
    @Key("amount")
    public int amount;
    // The payment processor for the charge. One of `braintree` or `stripe`. (optional)
    @Key("processor")
    public String processor;
    // The state of the dispute. One of `needs_response`, `warning_needs_response`. (optional)
    @Key("state")
    public String state;
    // The currency code of the dispute balance withdrawal. e.g. 'USD'. (optional)
    @Key("reversal_currency")
    public String reversalCurrency;
    // The amount of the dispute fee. Amounts are in cents (or other minor currency unit.) (optional)
    @Key("fee")
    public int fee;
    // The amount of the dispute balance withdrawal (without fee). Amounts are in cents (or other minor currency unit.) (optional)
    @Key("reversal_amount")
    public int reversalAmount;
    // The total amount of the dispute balance withdrawal (with fee). Amounts are in cents (or other minor currency unit.) (optional)
    @Key("reversal_total")
    public int reversalTotal;
    // Is the disputed charge refundable. (optional)
    @Key("is_charge_refundable")
    public Boolean isChargeRefundable;
    // How many times has dispute evidence been submitted. (optional)
    @Key("submitted_count")
    public int submittedCount;
    // State of address check (if available). One of `pass`, `fail`, `unavailable`, `checked`. (optional)
    @Key("address_line1_check")
    public String addressLine1Check;
    // State of address zip check (if available). One of `pass`, `fail`, `unavailable`, `checked`. (optional)
    @Key("address_zip_check")
    public String addressZipCheck;
    // State of cvc check (if available). One of `pass`, `fail`, `unavailable`, `checked`. (optional)
    @Key("cvc_check")
    public String cvcCheck;
    // The id of the template to use. (optional)
    @Key("template")
    public String template;
    // Key value pairs to hydrate the template's evidence fields. (optional)
    @Key("fields")
    public Map<String, String> fields;
    // List of products the customer purchased. (optional)
    @Key("products")
    public List<Product> products;
    // Set the account id for Connected accounts that are charged directly through Stripe. (optional)
    @Key("user_id")
    public String userId;
    // Set the kind for the dispute, 'chargeback', 'retrieval' or 'pre_arbitration'. (optional)
    @Key("kind")
    public String kind;
    // Submit dispute evidence immediately after creation. (optional)
    @Key("submit")
    public Boolean submit;
    // Queue dispute for submission immediately after creation. (optional)
    @Key("queue")
    public Boolean queue;
    // Custom URL with dispute information.
    @Key("reference_url")
    public String referenceUrl;
  }
}
