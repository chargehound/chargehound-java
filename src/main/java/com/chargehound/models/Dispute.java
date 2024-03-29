package com.chargehound.models;

import com.chargehound.models.CorrespondenceItem;
import com.chargehound.models.Product;
import com.chargehound.models.PastPayment;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

// A dispute. See https://www.chargehound.com/docs/api/2017-10-30/#disputes.
public class Dispute extends GenericJson {
  // A unique identifier for the dispute. This id is set by the payment processor of the dispute.
  @Key("id")
  public String id;
  // State of the dispute.
  @Key("state")
  public String state;
  // Reason for the dispute.
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
  // Id of the template attached to the dispute.
  @Key("template")
  public String template;
  // Evidence fields attached to the dispute.
  @Key("fields")
  public Map<String, Object> fields;
  // Any fields required by the template that have not yet been provided.
  @Key("missing_fields")
  public Map<String, Object> missingFields;
  //  A list of products in the disputed order.
  // (See [Product data](https://www.chargehound.com/docs/api/2017-10-30/#product-data) for details.) (optional)
  @Key("products")
  public List<Product> products;
  //  A list of emails with the customer.
  // (See [Customer correspondence](https://www.chargehound.com/docs/api/2017-10-30/#customer-correspondence) for details.) (optional)
  @Key("correspondence")
  public List<? extends CorrespondenceItem> correspondence;
  //  Customer's history of past payments.
  // (See [Past payments](https://www.chargehound.com/docs/api/2017-10-30/#past-payments) for details.) (optional)
  @Key("past_payments")
  public List<PastPayment> pastPayments;
  // Id of the disputed charge.
  @Key("charge")
  public String charge;
  // Can the charge be refunded.
  @Key("is_charge_refundable")
  public boolean isChargeRefundable;
  // Amount of the disputed charge. Amounts are in cents (or other minor currency unit.)
  @Key("amount")
  public long amount;
  // Currency code of the disputed charge. e.g. 'USD'.
  @Key("currency")
  public String currency;
  // Dispute fee.
  @Key("fee")
  public long fee;
  // The amount deducted due to the chargeback. Amounts are in cents (or other minor currency unit.)
  @Key("reversal_amount")
  public long reversalAmount;
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
  public String customerPurchaseIp;
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
  @Key("account_id")
  public String accountId;
  // Id of the connected account for this dispute (if multiple accounts are connected)
  @Key("account")
  public String account;
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
  public String referenceUrl;
  // Is this a livemode dispute.
  @Key("livemode")
  public boolean livemode;
  // Data about the API response that created dispute.
  public HttpResponse response;

  /**
   * The type returned by a dispute response request.
   * See https://www.chargehound.com/docs/api/2017-10-30/#retrieving-a-dispute-response
   */
  public static class Response extends GenericJson {
    @Key("livemode")
    public Boolean livemode;

    @Key("dispute_id")
    public String disputeId;

    @Key("external_charge")
    public String externalCharge;

    @Key("account_id")
    public String accountId;

    @Key("evidence")
    public Map<String, Object> evidence;

    @Key("response_url")
    public String responseUrl;

    public HttpResponse response;
  }

  // Params for updating or submitting a dispute.
  // See https://www.chargehound.com/docs/api/2017-10-30/#updating-a-dispute.
  public static class UpdateParams extends GenericJson {
    // Set the account id for Connected accounts that are charged directly through Stripe.
    // (optional)
    @Key("account_id")
    public String accountId;
    // Id of the connected account for this dispute (if multiple accounts are connected)
    // (optional)
    @Key("account")
    public String account;
    // Bypass manual review. (optional)
    @Key("force")
    public Boolean force;
    // Queue dispute for submission. (optional)
    @Key("queue")
    public Boolean queue;
    // Submit dispute evidence immediately after update. (optional)
    @Key("submit")
    public Boolean submit;
    // The id of the template to use. (optional)
    @Key("template")
    public String template;
    // Evidence fields attached to the dispute.
    @Key("fields")
    public Map<String, Object> fields;
    // A list of products in the disputed order.
    // (See [Product data](https://www.chargehound.com/docs/api/2017-10-30/#product-data) for details.) (optional)
    @Key("products")
    public List<Product> products;
    //  A list of emails with the customer.
    // (See [Customer correspondence](https://www.chargehound.com/docs/api/2017-10-30/#customer-correspondence) for details.) (optional)
    @Key("correspondence")
    public List<? extends CorrespondenceItem> correspondence;
    // Customer's history of past payments.
    // (See [Past payments](https://www.chargehound.com/docs/api/2017-10-30/#past-payments) for details.) (optional)
    @Key("past_payments")
    public List<PastPayment> pastPayments;
    // Custom URL with dispute information.
    @Key("reference_url")
    public String referenceUrl;

    public UpdateParams() {}

    private UpdateParams(
        final String accountId,
        final String account,
        final Boolean force,
        final Boolean queue,
        final Boolean submit,
        final String template,
        final Map<String, Object> fields,
        final List<Product> products,
        final List<? extends CorrespondenceItem> correspondence,
        final List<PastPayment> pastPayments,
        final String referenceUrl
    ) {
      this.accountId = accountId;
      this.account = account;
      this.force = force;
      this.queue = queue;
      this.submit = submit;
      this.template = template;
      this.fields = fields;
      this.products = products;
      this.correspondence = correspondence;
      this.pastPayments = pastPayments;
      this.referenceUrl = referenceUrl;
    }

    public static class Builder {
      private String accountId;
      private String account;
      private Boolean force;
      private Boolean queue;
      private Boolean submit;
      private String template;
      private Map<String, Object> fields;
      private List<Product> products;
      private List<? extends CorrespondenceItem> correspondence;
      private List<PastPayment> pastPayments;
      private String referenceUrl;

      public Builder accountId(final String accountId) {
        this.accountId = accountId;
        return this;
      }

      public Builder account(final String account) {
        this.account = account;
        return this;
      }

      public Builder force(final Boolean force) {
        this.force = force;
        return this;
      }

      public Builder queue(final Boolean queue) {
        this.queue = queue;
        return this;
      }

      public Builder submit(final Boolean submit) {
        this.submit = submit;
        return this;
      }

      public Builder template(final String template) {
        this.template = template;
        return this;
      }

      public Builder fields(final Map<String, Object> fields) {
        this.fields = fields;
        return this;
      }

      public Builder products(final List<Product> products) {
        this.products = products;
        return this;
      }

      public Builder correspondence(final List<? extends CorrespondenceItem> correspondence) {
        this.correspondence = correspondence;
        return this;
      }

      public Builder pastPayments(final List<PastPayment> pastPayments) {
        this.pastPayments = pastPayments;
        return this;
      }

      public Builder referenceUrl(final String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
      }

      /**
       * Finish the builder.
       * @return UpdateParams
       */
      public UpdateParams finish() {
        return new UpdateParams(
          this.accountId,
          this.account,
          this.force,
          this.queue,
          this.submit,
          this.template,
          this.fields,
          this.products,
          this.correspondence,
          this.pastPayments,
          this.referenceUrl
        );
      }
    }
  }

  // Params for creating a dispute.
  // See https://www.chargehound.com/docs/api/2017-10-30/#creating-a-dispute.
  public static class CreateParams extends GenericJson {
    // The id of the dispute in your payment processor. For Stripe looks like `dp_XXX`.
    @Key("id")
    public String id;
    // The id of the disputed charge in your payment processor.
    // For Stripe looks like `ch_XXX`.
    @Key("charge")
    public String charge;
    // The id of the charged customer in your payment processor.
    // For Stripe looks like `cus_XXX`. (optional)
    @Key("customer")
    public String customer;
    // The bank provided reason for the dispute.
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
    public Integer amount;
    // The payment processor for the charge. One of `braintree` or `stripe`. (optional)
    @Key("processor")
    public String processor;
    // The state of the dispute. One of `needs_response`, `warning_needs_response`. (optional)
    @Key("state")
    public String state;
    // The currency code of the dispute balance withdrawal. e.g. 'USD'. (optional)
    @Key("reversal_currency")
    public String reversalCurrency;
    // The amount of the dispute fee.
    // Amounts are in cents (or other minor currency unit.) (optional)
    @Key("fee")
    public Integer fee;
    // The amount of the dispute balance withdrawal (without fee).
    // Amounts are in cents (or other minor currency unit.) (optional)
    @Key("reversal_amount")
    public Integer reversalAmount;
    // The total amount of the dispute balance withdrawal (with fee).
    // Amounts are in cents (or other minor currency unit.) (optional)
    @Key("reversal_total")
    public Integer reversalTotal;
    // Is the disputed charge refundable. (optional)
    @Key("is_charge_refundable")
    public Boolean isChargeRefundable;
    // How many times has dispute evidence been submitted. (optional)
    @Key("submitted_count")
    public Integer submittedCount;
    // State of address check (if available).
    @Key("address_line1_check")
    public String addressLine1Check;
    // State of address zip check (if available).
    @Key("address_zip_check")
    public String addressZipCheck;
    // State of cvc check (if available).
    @Key("cvc_check")
    public String cvcCheck;
    // The id of the template to use. (optional)
    @Key("template")
    public String template;
    // Key value pairs to hydrate the template's evidence fields. (optional)
    @Key("fields")
    public Map<String, Object> fields;
    // List of products the customer purchased. (optional)
    @Key("products")
    public List<Product> products;
    // Correspondence with the customer. (optional)
    @Key("correspondence")
    public List<? extends CorrespondenceItem> correspondence;
    // Customer's history of past payments. (optional)
    @Key("past_payments")
    public List<PastPayment> pastPayments;
    // Set the account id for Connected accounts that are charged directly through Stripe.
    // (optional)
    @Key("account_id")
    public String accountId;
    // Set the kind for the dispute, 'chargeback', 'retrieval' or 'pre_arbitration'.
    // (optional)
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

    public CreateParams() {}

    private CreateParams(
        final String id,
        final String charge,
        final String customer,
        final String reason,
        final String chargedAt,
        final String disputedAt,
        final String dueBy,
        final String currency,
        final Integer amount,
        final String processor,
        final String state,
        final String reversalCurrency,
        final Integer fee,
        final Integer reversalAmount,
        final Integer reversalTotal,
        final Boolean isChargeRefundable,
        final Integer submittedCount,
        final String addressLine1Check,
        final String addressZipCheck,
        final String cvcCheck,
        final String template,
        final Map<String, Object> fields,
        final List<Product> products,
        final List<? extends CorrespondenceItem> correspondence,
        final List<PastPayment> pastPayments,
        final String accountId,
        final String kind,
        final Boolean submit,
        final Boolean queue,
        final String referenceUrl
    ) {
      this.id = id;
      this.charge = charge;
      this.customer = customer;
      this.reason = reason;
      this.chargedAt = chargedAt;
      this.disputedAt = disputedAt;
      this.dueBy = dueBy;
      this.currency = currency;
      this.amount = amount;
      this.processor = processor;
      this.state = state;
      this.reversalCurrency = reversalCurrency;
      this.fee = fee;
      this.reversalAmount = reversalAmount;
      this.reversalTotal = reversalTotal;
      this.isChargeRefundable = isChargeRefundable;
      this.submittedCount = submittedCount;
      this.addressLine1Check = addressLine1Check;
      this.addressZipCheck = addressZipCheck;
      this.cvcCheck = cvcCheck;
      this.template = template;
      this.fields = fields;
      this.products = products;
      this.correspondence = correspondence;
      this.pastPayments = pastPayments;
      this.accountId = accountId;
      this.kind = kind;
      this.submit = submit;
      this.queue = queue;
      this.referenceUrl = referenceUrl;
    }

    public static class Builder {
      private String id;
      private String charge;
      private String customer;
      private String reason;
      private String chargedAt;
      private String disputedAt;
      private String dueBy;
      private String currency;
      private Integer amount;
      private String processor;
      private String state;
      private String reversalCurrency;
      private Integer fee;
      private Integer reversalAmount;
      private Integer reversalTotal;
      private Boolean isChargeRefundable;
      private Integer submittedCount;
      private String addressLine1Check;
      private String addressZipCheck;
      private String cvcCheck;
      private String template;
      private Map<String, Object> fields;
      private List<Product> products;
      private List<? extends CorrespondenceItem> correspondence;
      private List<PastPayment> pastPayments;
      private String accountId;
      private String kind;
      private Boolean submit;
      private Boolean queue;
      private String referenceUrl;

      public Builder id(final String id) {
        this.id = id;
        return this;
      }

      public Builder charge(final String charge) {
        this.charge = charge;
        return this;
      }

      public Builder customer(final String customer) {
        this.customer = customer;
        return this;
      }

      public Builder reason(final String reason) {
        this.reason = reason;
        return this;
      }

      public Builder chargedAt(final String chargedAt) {
        this.chargedAt = chargedAt;
        return this;
      }

      public Builder disputedAt(final String disputedAt) {
        this.disputedAt = disputedAt;
        return this;
      }

      public Builder dueBy(final String dueBy) {
        this.dueBy = dueBy;
        return this;
      }

      public Builder currency(final String currency) {
        this.currency = currency;
        return this;
      }

      public Builder amount(final Integer amount) {
        this.amount = amount;
        return this;
      }

      public Builder processor(final String processor) {
        this.processor = processor;
        return this;
      }

      public Builder state(final String state) {
        this.state = state;
        return this;
      }

      public Builder reversalCurrency(final String reversalCurrency) {
        this.reversalCurrency = reversalCurrency;
        return this;
      }

      public Builder fee(final Integer fee) {
        this.fee = fee;
        return this;
      }

      public Builder reversalAmount(final Integer reversalAmount) {
        this.reversalAmount = reversalAmount;
        return this;
      }

      public Builder reversalTotal(final Integer reversalTotal) {
        this.reversalTotal = reversalTotal;
        return this;
      }

      public Builder isChargeRefundable(final Boolean isChargeRefundable) {
        this.isChargeRefundable = isChargeRefundable;
        return this;
      }

      public Builder submittedCount(final Integer submittedCount) {
        this.submittedCount = submittedCount;
        return this;
      }

      public Builder addressLine1Check(final String addressLine1Check) {
        this.addressLine1Check = addressLine1Check;
        return this;
      }

      public Builder addressZipCheck(final String addressZipCheck) {
        this.addressZipCheck = addressZipCheck;
        return this;
      }

      public Builder cvcCheck(final String cvcCheck) {
        this.cvcCheck = cvcCheck;
        return this;
      }

      public Builder accountId(final String accountId) {
        this.accountId = accountId;
        return this;
      }

      public Builder kind(final String kind) {
        this.kind = kind;
        return this;
      }

      public Builder submit(final Boolean submit) {
        this.submit = submit;
        return this;
      }

      public Builder queue(final Boolean queue) {
        this.queue = queue;
        return this;
      }

      public Builder template(final String template) {
        this.template = template;
        return this;
      }

      public Builder fields(final Map<String, Object> fields) {
        this.fields = fields;
        return this;
      }

      public Builder products(final List<Product> products) {
        this.products = products;
        return this;
      }

      public Builder correspondence(final List<? extends CorrespondenceItem> correspondence) {
        this.correspondence = correspondence;
        return this;
      }

      public Builder pastPayments(final List<PastPayment> pastPayments) {
        this.pastPayments = pastPayments;
        return this;
      }

      public Builder referenceUrl(final String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
      }

      /**
       * Finish the builder.
       * @return CreateParams
       */
      public CreateParams finish() {
        return new CreateParams(
          this.id,
          this.charge,
          this.customer,
          this.reason,
          this.chargedAt,
          this.disputedAt,
          this.dueBy,
          this.currency,
          this.amount,
          this.processor,
          this.state,
          this.reversalCurrency,
          this.fee,
          this.reversalAmount,
          this.reversalTotal,
          this.isChargeRefundable,
          this.submittedCount,
          this.addressLine1Check,
          this.addressZipCheck,
          this.cvcCheck,
          this.template,
          this.fields,
          this.products,
          this.correspondence,
          this.pastPayments,
          this.accountId,
          this.kind,
          this.submit,
          this.queue,
          this.referenceUrl
        );
      }
    }
  }
}
