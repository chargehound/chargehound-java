# Chargehound Java bindings 
Chargehound bindings for Java

[![Build Status](https://travis-ci.org/chargehound/chargehound-java.svg?branch=master)](https://travis-ci.org/chargehound/chargehound-java)

## Installation

TODO: the different methods =

### Requests

Java requests use defined structs to represent parameters.

```java
import com.chargehound.models.Dispute;

HashMap<String, Object> fields = new HashMap<String, Object>() {{
  put("customer_name", "Susie Chargeback");
}};

Dispute result = chargehound.Disputes.submit("dp_123",
  new Dispute.UpdateParams.Builder()
  .template("unrecognized")
  .fields(fields)
  .finish()
);
```

### Responses

Responses from the API are automatically parsed from JSON and returned as Java objects.

Responses also include the HTTP status code on the response object.

```java
import com.chargehound.models.Dispute;

Dispute result = chargehound.Disputes.retrieve("dp_123");

System.out.println(result.state);
// "needs_response"
System.out.println(result.response.getStatusCode());
// 200
```

## Documentation

[Disputes](https://www.chargehound.com/docs/api/index.html?java#disputes)

[Errors](https://www.chargehound.com/docs/api/index.html?java#errors)

The Java library returns adapted objects rather than JSON from API calls.

## Google AppEngine

If you're using the library in a Google App Engine environment, you can pass a custom http client along with each request. `OptHTTPClient` is defined on all param structs.

TODO: example override transport

## Development

Clone the latest source and run the tests:

```bash
$ git clone git@github.com:chargehound/chargehound-java.git
$ ./gradlew build
```

## Deployment

To deploy a new version of the SDK, perform the following steps:

 1. Update the CHANGELOG to describe what features have been added.
 2. Bump the version number in `gradle.properties`.

 // TODO: the rest of the steps
