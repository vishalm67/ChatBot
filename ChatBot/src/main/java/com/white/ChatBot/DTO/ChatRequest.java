package com.white.ChatBot.DTO;
import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private String sessionId;
    private String username;

    // Constructors
    public ChatRequest() {}

    public ChatRequest(String message, String sessionId, String username) {
        this.message = message;
        this.sessionId = sessionId;
        this.username = username;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}