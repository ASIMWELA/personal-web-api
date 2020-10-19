package com.personal.website.repository;


import com.personal.website.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Transactional
    @Query(value="SELECT chat_id FROM chat_room AS c WHERE c.sender_id=:senderId AND c.recipient_id=:recId", nativeQuery = true)
    String getChatId(@Param("senderId")String senderId, @Param("recId") String recipientId);

}
