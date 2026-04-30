# Quarkus Presidio 0.6.0 - Data Anonymization Examples

This guide demonstrates how to use Quarkus Presidio 0.6.0 to anonymize sensitive data (PII) in your applications.

## Overview

Quarkus Presidio provides integration with Microsoft Presidio for detecting and anonymizing personally identifiable information (PII) in text. It offers:

- **Analyzer**: Detects PII entities (names, emails, phone numbers, SSN, credit cards, etc.)
- **Anonymizer**: Replaces, masks, or redacts detected PII
- **DevServices**: Automatically starts Presidio containers in dev/test mode
- **Health Checks**: Built-in readiness probes

## Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.quarkiverse.presidio</groupId>
    <artifactId>quarkus-presidio</artifactId>
    <version>0.6.0</version>
</dependency>
```

## Two Approaches to Anonymization

### 1. Pipeline Approach (Recommended - Simplified)

The pipeline approach combines analysis and anonymization in a single step:

```java
@ApplicationScoped
public class PresidioService {
    
    private final PresidioPipeline presidioPipeline;
    
    private static final Replace REPLACE = new Replace("ANONYMIZED");
    private static final Mask PHONE_MASK = new Mask("*", 4, true);
    
    public PresidioService() {
        this.presidioPipeline = PresidioPipeline.builder()
                .withSupportedEntities(
                        SupportedEntities.PERSON,
                        SupportedEntities.PHONE_NUMBER,
                        SupportedEntities.EMAIL_ADDRESS)
                .withSupportedAnonymizers(
                        Map.of(
                                SupportedEntities.DEFAULT, REPLACE,
                                SupportedEntities.PHONE_NUMBER, PHONE_MASK))
                .build();
    }
    
    public String anonymize(String text, String language) {
        return presidioPipeline.process(text, language);
    }
}
```

**Usage Example:**
```bash
curl -X POST http://localhost:8080/presidio/anonymize/pipeline?language=en \
  -H "Content-Type: text/plain" \
  -d "My name is John Smith and my phone is 212-555-5555"

# Response: "My name is ANONYMIZED and my phone is ****5555"
```

### 2. Manual Approach (Full Control)

The manual approach gives you complete control over each step:

```java
@ApplicationScoped
public class PresidioService {
    
    @Inject
    @RestClient
    Analyzer analyzer;
    
    @Inject
    @RestClient
    Anonymizer anonymizer;
    
    public AnonymizeResponse anonymizeManual(String text, String language) {
        // Step 1: Analyze text to identify PII
        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        analyzeRequest.text(text);
        analyzeRequest.language(language);
        
        List<RecognizerResultWithAnaysisExplanation> results = 
            analyzer.analyzePost(analyzeRequest);
        
        // Step 2: Configure anonymization strategies
        AnonymizeRequest anonymizeRequest = new AnonymizeRequest();
        anonymizeRequest.setText(text);
        anonymizeRequest.putAnonymizersItem("DEFAULT", new Replace("ANONYMIZED"));
        anonymizeRequest.putAnonymizersItem("PHONE_NUMBER", new Mask("*", 4, true));
        anonymizeRequest.analyzerResults(Collections.unmodifiableList(results));
        
        // Step 3: Anonymize
        return anonymizer.anonymizePost(anonymizeRequest);
    }
}
```

**Usage Example:**
```bash
curl -X POST http://localhost:8080/presidio/anonymize/manual?language=en \
  -H "Content-Type: text/plain" \
  -d "Contact me at john.doe@example.com or call 555-1234"

# Response: Full JSON with anonymized text and metadata
```

## Anonymization Strategies

Quarkus Presidio supports multiple anonymization strategies:

### 1. Replace
Replaces detected entities with a fixed string:
```java
Replace replace = new Replace("ANONYMIZED");
```

### 2. Mask
Masks characters with a specified character:
```java
// Mask all but last 4 characters with *
Mask mask = new Mask("*", 4, true);

// Example: "212-555-5555" → "****5555"
```

### 3. Redact
Completely removes the detected entity:
```java
Redact redact = new Redact();
```

### 4. Hash
Replaces with a hash value:
```java
Hash hash = new Hash();
```

### 5. Encrypt/Decrypt
Encrypts the value (requires encryption key):
```java
Encrypt encrypt = new Encrypt("your-encryption-key");
Decrypt decrypt = new Decrypt("your-encryption-key");
```

## Supported Entity Types

The extension can detect and anonymize:

- `PERSON` - Person names
- `EMAIL_ADDRESS` - Email addresses
- `PHONE_NUMBER` - Phone numbers
- `CREDIT_CARD` - Credit card numbers
- `US_SSN` - US Social Security Numbers
- `US_DRIVER_LICENSE` - US Driver's License numbers
- `US_PASSPORT` - US Passport numbers
- `IBAN_CODE` - International Bank Account Numbers
- `IP_ADDRESS` - IP addresses
- `LOCATION` - Geographic locations
- `DATE_TIME` - Dates and times
- `URL` - URLs
- And many more...

## Analysis Only (Without Anonymization)

You can analyze text to see what would be detected without anonymizing:

```bash
curl -X POST http://localhost:8080/presidio/analyze?language=en \
  -H "Content-Type: text/plain" \
  -d "My SSN is 123-45-6789 and email is test@example.com"
```

**Response:**
```json
[
  {
    "entity_type": "US_SSN",
    "start": 10,
    "end": 21,
    "score": 0.85,
    "analysis_explanation": {...}
  },
  {
    "entity_type": "EMAIL_ADDRESS",
    "start": 35,
    "end": 53,
    "score": 1.0,
    "analysis_explanation": {...}
  }
]
```

## Configuration

### DevServices (Development Mode)

By default, Quarkus Presidio automatically starts Presidio containers in dev/test mode. No configuration needed!

### Production Configuration

For production, configure the Presidio service URLs in `application.properties`:

```properties
# Analyzer service URL
quarkus.rest-client.presidio-analyzer.url=http://presidio-analyzer:5001

# Anonymizer service URL
quarkus.rest-client.presidio-anonymizer.url=http://presidio-anonymizer:5002

# Disable DevServices in production
%prod.quarkus.presidio.devservices.analyzer.enabled=false
%prod.quarkus.presidio.devservices.anonymizer.enabled=false
```

### Health Check Configuration

Disable health checks if needed:
```properties
quarkus.presidio.health.enabled=false
```

### Custom Container Images

Override default DevServices images:
```properties
quarkus.presidio.devservices.analyzer.image=custom/presidio-analyzer:latest
quarkus.presidio.devservices.anonymizer.image=custom/presidio-anonymizer:latest
```

## Complete Example

Here's a complete example anonymizing customer data:

```java
@Path("/customers")
public class CustomerResource {
    
    @Inject
    PresidioService presidioService;
    
    @POST
    @Path("/register")
    public Response registerCustomer(CustomerData data) {
        // Anonymize sensitive fields before storing
        String anonymizedName = presidioService.anonymizePipeline(
            data.getName(), "en");
        String anonymizedEmail = presidioService.anonymizePipeline(
            data.getEmail(), "en");
        String anonymizedPhone = presidioService.anonymizePipeline(
            data.getPhone(), "en");
        
        // Store anonymized data
        Customer customer = new Customer(
            anonymizedName,
            anonymizedEmail,
            anonymizedPhone
        );
        
        // Save to database...
        
        return Response.ok(customer).build();
    }
}
```

## Testing

Run the application in dev mode:
```bash
./mvnw quarkus:dev
```

Test the endpoints:
```bash
# Pipeline approach
curl -X POST http://localhost:8080/presidio/anonymize/pipeline?language=en \
  -H "Content-Type: text/plain" \
  -d "John Smith's email is john@example.com and phone is 555-1234"

# Manual approach
curl -X POST http://localhost:8080/presidio/anonymize/manual?language=en \
  -H "Content-Type: text/plain" \
  -d "My credit card is 4111-1111-1111-1111"

# Analysis only
curl -X POST http://localhost:8080/presidio/analyze?language=en \
  -H "Content-Type: text/plain" \
  -d "SSN: 123-45-6789, Email: test@example.com"
```

## Best Practices

1. **Use Pipeline Approach**: For most use cases, the pipeline approach is simpler and sufficient
2. **Configure Entity Types**: Only detect entity types you need to improve performance
3. **Choose Appropriate Strategies**: Use `Mask` for partial visibility, `Replace` for complete anonymization
4. **Test Thoroughly**: Test with various input formats to ensure proper detection
5. **Monitor Performance**: PII detection can be CPU-intensive for large texts
6. **Handle Errors**: Implement proper error handling for network issues with Presidio services

## Additional Resources

- [Quarkus Presidio Documentation](https://github.com/quarkiverse/quarkus-presidio)
- [Microsoft Presidio](https://microsoft.github.io/presidio/)
- [Presidio API Documentation](https://github.com/microsoft/presidio/blob/main/docs/api-docs/api-docs.yml)