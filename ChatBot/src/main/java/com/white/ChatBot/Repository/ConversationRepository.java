package com.white.ChatBot.Repository;



import com.white.ChatBot.Models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findBySessionId(String sessionId);
    List<Conversation> findByUserId(Long userId);
    List<Conversation> findByUserIdAndIsActive(Long userId, Boolean isActive);
}