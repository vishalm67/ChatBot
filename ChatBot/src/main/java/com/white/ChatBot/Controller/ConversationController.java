package com.white.ChatBot.Controller;

import com.white.ChatBot.Models.Conversation;
import com.white.ChatBot.Service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("/api/conversations")
    @CrossOrigin(origins = "*")
    public class ConversationController {

        @Autowired
        private ConversationService conversationService;

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable Long userId) {
            return ResponseEntity.ok(conversationService.getUserConversations(userId));
        }

        @GetMapping("/{sessionId}")
        public ResponseEntity<?> getConversationBySessionId(@PathVariable String sessionId) {
            return conversationService.findBySessionId(sessionId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }

