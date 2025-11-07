package com.white.ChatBot.DTO;

import java.time.LocalDateTime;

public class ChatResponse {

    private String response;
    private String sessionId;
    private String intent;
    private Double confidence;
    private Long responseTimeMs;
    private LocalDateTime timestamp;

    // Constructors
    public ChatResponse() {}

    public ChatResponse(String response, String sessionId, String intent) {
        this.response = response;
        this.sessionId = sessionId;
        this.intent = intent;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }

    public Long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Long responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
