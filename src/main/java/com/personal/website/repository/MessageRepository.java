package com.personal.website.repository;


import com.personal.website.entity.MessageEntity;
import com.personal.website.payload.MessageStatus;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long>
{

  @Transactional
  @Query(value="SELECT * FROM messages WHERE messages.type IS NOT NULL ORDER BY messages.created_at DESC", nativeQuery = true)
  public List<MessageEntity> getAllMessages(Pageable page);

  List<MessageEntity> findByChatId(String chatId);

  long countBySenderIdAndRecipientIdAndStatus(
          String senderId, String recipientId, MessageStatus status);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value="UPDATE messages as s SET s.message_status=:newStatus WHERE s.recipient_id=:recId AND s.sender_id=:sdId", nativeQuery = true)
  public void updateStatus(@Param("newStatus")MessageStatus status, @Param("recId") String recId, @Param("sdId")String sdId);

}