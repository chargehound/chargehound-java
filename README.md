# Chargehound Java bindings
Chargehound bindings for Java

[![Build Status](https://github.com/chargehound/chargehound-java/actions/workflows/test-java.yaml/badge.svg)](https://github.com/chargehound/chargehound-java/actions/workflows/test-java.yaml)

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.chargehound</groupId>
    <artifactId>chargehound-java</artifactId>
    <version>2.1.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.chargehound:chargehound-java:2.0.0"
```

### Others

You'll need to manually install the following JARs:

* The Chargehound JAR from https://github.com/chargehound/chargehound-java/releases/latest
* [Google HTTP client](https://developers.google.com/api-client-library/java/google-http-java-client/) from <https://developers.google.com/api-client-library/java/google-http-java-client/download>.

### Requests

Java requests use defined structs to represent parameters.

```java
import com.chargehound.Chargehound;
import com.chargehound.models.Dispute;

Chargehound chargehound = new Chargehound("test_123");

Map<String, Object> fields = new HashMap<String, Object>();
fields.put("customer_name", "Susie Chargeback");

Dispute result = chargehound.disputes.submit("dp_123",
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
import com.chargehound.Chargehound;
import com.chargehound.models.Dispute;

Dispute result = chargehound.disputes.retrieve("dp_123");

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

If you're using the library in a Google App Engine environment, you can set the Chargehound client to use the [supported HTTP transport](https://developers.google.com/api-client-library/java/google-http-java-client/app-engine#http_transport).

```java
import com.chargehound.Chargehound;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;

Chargehound chargehound = new Chargehound("test_123");

chargehound.setHttpTransport(new UrlFetchTransport());
```

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
3. Bump the version number in the `Chargehound` class in `Chargehound.java`.
4. Bump the version number in this README.
5. Deploy the Jar to the Maven central repository with `gradle clean build uploadArchives`
6. Navigate here https://oss.sonatype.org/#stagingRepositories and release the build (https://central.sonatype.org/pages/releasing-the-deployment.html)

### Deployment configuration

You will need the Sonatype login. Set the `OSSRH_USERNAME`/`OSSRH_PASSWORD` in your local `~/.gradle/gradle.properties` file.

You will also need to setup a PGP key. Follow the instructions here: https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials

Set the key info in your `~/.gradle/gradle.properties` file. Example:

 ```
signing.keyId=24875D73
signing.password=secret
signing.secretKeyRingFile=/Users/me/.gnupg/secring.gpg
```