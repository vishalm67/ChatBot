package com.white.ChatBot.Service;

import com.white.ChatBot.DTO.ChatRequest;
import com.white.ChatBot.DTO.ChatResponse;
import com.white.ChatBot.DTO.MessageHistoryDTO;
import com.white.ChatBot.Models.Conversation;
import com.white.ChatBot.Models.Message;
import com.white.ChatBot.Models.User;
import com.white.ChatBot.Repository.MessageRepository;
import com.white.ChatBot.Service.IntentRecognitionService.IntentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



    @Service
    public class ChatbotService {

        @Autowired
        private MessageRepository messageRepository;

        @Autowired
        private UserService userService;

        @Autowired
        private ConversationService conversationService;

        @Autowired
        private IntentRecognitionService intentRecognitionService;

        @Autowired
        private ResponseGeneratorService responseGeneratorService;

        public ChatResponse processMessage(ChatRequest request) {
            long startTime = System.currentTimeMillis();

            // Get or create user
            String username = request.getUsername() != null ? request.getUsername() : "anonymous";
            User user = userService.getOrCreateUser(username);
            userService.updateLastActive(user.getId());

            // Get or create conversation
            String sessionId = request.getSessionId() != null ?
                    request.getSessionId() : UUID.randomUUID().toString();
            Conversation conversation = conversationService.getOrCreateConversation(sessionId, user);

            // Recognize intent
            IntentResult intentResult = intentRecognitionService.recognizeIntent(request.getMessage());

            // Generate response
            String response = responseGeneratorService.generateResponse(
                    intentResult.getIntent(),
                    request.getMessage()
            );

            // Calculate response time
            long responseTime = System.currentTimeMillis() - startTime;

            // Save message to database
            Message message = new Message(conversation, request.getMessage(), response);
            message.setIntent(intentResult.getIntent());
            message.setConfidenceScore(intentResult.getConfidence());
            message.setResponseTimeMs(responseTime);
            messageRepository.save(message);

            // Create response
            ChatResponse chatResponse = new ChatResponse(response, sessionId, intentResult.getIntent());
            chatResponse.setConfidence(intentResult.getConfidence());
            chatResponse.setResponseTimeMs(responseTime);

            return chatResponse;
        }

        public List<MessageHistoryDTO> getChatHistory(String sessionId) {
            List<Message> messages = messageRepository.findByConversationSessionIdOrderByTimestampAsc(sessionId);
            return messages.stream()
                    .map(msg -> new MessageHistoryDTO(
                            msg.getId(),
                            msg.getUserMessage(),
                            msg.getBotResponse(),
                            msg.getIntent(),
                            msg.getTimestamp()
                    ))
                    .collect(Collectors.toList());
        }

        public void endSession(String sessionId) {
            conversationService.endConversation(sessionId);
        }
    }
