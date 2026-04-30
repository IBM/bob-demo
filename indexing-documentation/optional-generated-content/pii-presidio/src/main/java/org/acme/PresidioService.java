package org.acme;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkiverse.presidio.runtime.Analyzer;
import io.quarkiverse.presidio.runtime.Anonymizer;
import io.quarkiverse.presidio.runtime.PresidioPipeline;
import io.quarkiverse.presidio.runtime.model.AnalyzeRequest;
import io.quarkiverse.presidio.runtime.model.AnonymizeRequest;
import io.quarkiverse.presidio.runtime.model.AnonymizeResponse;
import io.quarkiverse.presidio.runtime.model.Mask;
import io.quarkiverse.presidio.runtime.model.RecognizerResultWithAnaysisExplanation;
import io.quarkiverse.presidio.runtime.model.Replace;
import io.quarkiverse.presidio.runtime.model.SupportedEntities;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service demonstrating two approaches to anonymize PII data using Quarkus Presidio:
 * 1. Manual approach - using Analyzer and Anonymizer separately
 * 2. Pipeline approach - using PresidioPipeline for simplified workflow
 */
@ApplicationScoped
public class PresidioService {

    @Inject
    @RestClient
    Analyzer analyzer;

    @Inject
    @RestClient
    Anonymizer anonymizer;

    private final PresidioPipeline presidioPipeline;

    // Define anonymization strategies
    private static final Replace REPLACE = new Replace("ANONYMIZED");
    private static final Mask PHONE_MASK = new Mask("*", 4, true);
    private static final Mask EMAIL_MASK = new Mask("*", 3, false);

    public PresidioService() {
        // Initialize the pipeline with supported entities and anonymizers
        this.presidioPipeline = PresidioPipeline.builder()
                .withSupportedEntities(
                        SupportedEntities.PERSON,
                        SupportedEntities.PHONE_NUMBER,
                        SupportedEntities.EMAIL_ADDRESS,
                        SupportedEntities.CREDIT_CARD,
                        SupportedEntities.US_SSN)
                .withSupportedAnonymizers(
                        Map.of(
                                SupportedEntities.DEFAULT, REPLACE,
                                SupportedEntities.PHONE_NUMBER, PHONE_MASK,
                                SupportedEntities.EMAIL_ADDRESS, EMAIL_MASK))
                .build();
    }

    /**
     * Manual approach: Analyze and anonymize text step by step
     * This gives you full control over the process
     */
    public AnonymizeResponse anonymizeManual(String text, String language) {
        // Step 1: Analyze the text to identify PII entities
        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        analyzeRequest.text(text);
        analyzeRequest.language(language);

        List<RecognizerResultWithAnaysisExplanation> recognizerResults = 
            analyzer.analyzePost(analyzeRequest);

        // Step 2: Configure anonymization strategies
        AnonymizeRequest anonymizeRequest = new AnonymizeRequest();
        anonymizeRequest.setText(text);

        // Define how to anonymize different entity types
        anonymizeRequest.putAnonymizersItem("DEFAULT", REPLACE);
        anonymizeRequest.putAnonymizersItem("PHONE_NUMBER", PHONE_MASK);
        anonymizeRequest.putAnonymizersItem("EMAIL_ADDRESS", EMAIL_MASK);

        // Set the analyzer results
        anonymizeRequest.analyzerResults(
            Collections.unmodifiableList(recognizerResults)
        );

        // Step 3: Anonymize the text
        return anonymizer.anonymizePost(anonymizeRequest);
    }

    /**
     * Pipeline approach: Simplified one-step anonymization
     * This is the recommended approach for most use cases
     */
    public String anonymizePipeline(String text, String language) {
        return presidioPipeline.process(text, language);
    }

    /**
     * Analyze text to identify PII entities without anonymizing
     * Useful for understanding what will be anonymized
     */
    public List<RecognizerResultWithAnaysisExplanation> analyzeOnly(String text, String language) {
        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
        analyzeRequest.text(text);
        analyzeRequest.language(language);

        return analyzer.analyzePost(analyzeRequest);
    }
}

// Made with Bob
