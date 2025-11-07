package com.white.ChatBot.Repository;

import com.white.ChatBot.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationIdOrderByTimestampAsc(Long conversationId);
    List<Message> findByConversationSessionIdOrderByTimestampAsc(String sessionId);
    Long countByConversationId(Long conversationId);
}
