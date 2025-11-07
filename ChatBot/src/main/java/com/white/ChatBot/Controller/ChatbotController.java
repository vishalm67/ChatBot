package com.white.ChatBot.Controller;

import com.white.ChatBot.DTO.ChatRequest;
import com.white.ChatBot.DTO.ChatResponse;
import com.white.ChatBot.DTO.MessageHistoryDTO;
import com.white.ChatBot.Service.ChatbotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatbotService.processMessage(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<MessageHistoryDTO>> getChatHistory(@PathVariable String sessionId) {
        List<MessageHistoryDTO> history = chatbotService.getChatHistory(sessionId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<Map<String, String>> endSession(@PathVariable String sessionId) {
        chatbotService.endSession(sessionId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Session ended successfully");
        response.put("sessionId", sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Chatbot Backend");
        health.put("timestamp", java.time.LocalDateTime.now());
        return ResponseEntity.ok(health);
    }
}