package com.white.ChatBot.Service;

import com.white.ChatBot.Models.Conversation;
import com.white.ChatBot.Models.User;
import com.white.ChatBot.Repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation createConversation(User user) {
        String sessionId = UUID.randomUUID().toString();
        Conversation conversation = new Conversation(sessionId, user);
        return conversationRepository.save(conversation);
    }

    public Optional<Conversation> findBySessionId(String sessionId) {
        return conversationRepository.findBySessionId(sessionId);
    }

    public Conversation getOrCreateConversation(String sessionId, User user) {
        return conversationRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation(sessionId, user);
                    return conversationRepository.save(newConversation);
                });
    }

    public void endConversation(String sessionId) {
        conversationRepository.findBySessionId(sessionId).ifPresent(conversation -> {
            conversation.setIsActive(false);
            conversation.setEndedAt(LocalDateTime.now());
            conversationRepository.save(conversation);
        });
    }

    public List<Conversation> getUserConversations(Long userId) {
        return conversationRepository.findByUserId(userId);
    }
}