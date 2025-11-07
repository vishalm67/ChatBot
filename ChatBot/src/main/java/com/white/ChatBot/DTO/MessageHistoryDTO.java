package com.white.ChatBot.DTO;

import java.time.LocalDateTime;

public class MessageHistoryDTO {

    private Long id;
    private String userMessage;
    private String botResponse;
    private String intent;
    private LocalDateTime timestamp;

    // Constructors
    public MessageHistoryDTO() {}

    public MessageHistoryDTO(Long id, String userMessage, String botResponse, String intent, LocalDateTime timestamp) {
        this.id = id;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
        this.intent = intent;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public String getBotResponse() { return botResponse; }
    public void setBotResponse(String botResponse) { this.botResponse = botResponse; }

    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}