package com.white.ChatBot.Models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Column(name = "user_message", columnDefinition = "TEXT")
    private String userMessage;

    @Column(name = "bot_response", columnDefinition = "TEXT")
    private String botResponse;

    @Column(name = "intent")
    private String intent;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "response_time_ms")
    private Long responseTimeMs;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }


    public Message() {}

    public Message(Conversation conversation, String userMessage, String botResponse) {
        this.conversation = conversation;
        this.userMessage = userMessage;
        this.botResponse = botResponse;
    }

    public Long getId()
    { return id; }
    public void setId(Long id)
    { this.id = id; }

    public Conversation getConversation()
    { return conversation; }
    public void setConversation(Conversation conversation)
    { this.conversation = conversation; }

    public String getUserMessage()
    { return userMessage; }
    public void setUserMessage(String userMessage)
    { this.userMessage = userMessage; }

    public String getBotResponse()
    { return botResponse; }
    public void setBotResponse(String botResponse)
    { this.botResponse = botResponse; }

    public String getIntent()
    { return intent; }
    public void setIntent(String intent)
    { this.intent = intent; }

    public Double getConfidenceScore()
    { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore)
    { this.confidenceScore = confidenceScore; }

    public LocalDateTime getTimestamp()
    { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp)
    { this.timestamp = timestamp; }

    public Long getResponseTimeMs()
    { return responseTimeMs; }
    public void setResponseTimeMs(Long responseTimeMs)
    { this.responseTimeMs = responseTimeMs; }
}
