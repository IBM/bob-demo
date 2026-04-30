package org.acme;

import java.util.List;

import io.quarkiverse.presidio.runtime.model.AnonymizeResponse;
import io.quarkiverse.presidio.runtime.model.RecognizerResultWithAnaysisExplanation;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * REST endpoints demonstrating Quarkus Presidio 0.6.0 usage for data anonymization
 */
@Path("/presidio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class GreetingResource {

    @Inject
    PresidioService presidioService;

    /**
     * Anonymize text using the simplified pipeline approach (recommended)
     * 
     * Example request:
     * POST /presidio/anonymize/pipeline?language=en
     * Body: "My name is John Smith and my phone is 212-555-5555"
     * 
     * Response: "My name is ANONYMIZED and my phone is ****5555"
     */
    @POST
    @Path("/anonymize/pipeline")
    public String anonymizePipeline(
            String text,
            @QueryParam("language") String language) {
        
        String lang = language != null ? language : "en";
        return presidioService.anonymizePipeline(text, lang);
    }

    /**
     * Anonymize text using the manual approach (full control)
     * 
     * Example request:
     * POST /presidio/anonymize/manual?language=en
     * Body: "Contact me at john.doe@example.com or call 555-1234"
     * 
     * Response: Full AnonymizeResponse object with anonymized text and metadata
     */
    @POST
    @Path("/anonymize/manual")
    public AnonymizeResponse anonymizeManual(
            String text,
            @QueryParam("language") String language) {
        
        String lang = language != null ? language : "en";
        return presidioService.anonymizeManual(text, lang);
    }

    /**
     * Analyze text to identify PII entities without anonymizing
     * Useful for understanding what will be detected and anonymized
     * 
     * Example request:
     * POST /presidio/analyze?language=en
     * Body: "My SSN is 123-45-6789 and email is test@example.com"
     * 
     * Response: List of detected entities with their types, scores, and positions
     */
    @POST
    @Path("/analyze")
    public List<RecognizerResultWithAnaysisExplanation> analyze(
            String text,
            @QueryParam("language") String language) {
        
        String lang = language != null ? language : "en";
        return presidioService.analyzeOnly(text, lang);
    }
}

// Made with Bob
