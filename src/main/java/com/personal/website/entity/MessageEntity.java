package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.website.payload.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="messages")
@Table(name="messages")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private Long id;
    @Column(name="sender")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sender;
    @Column(name="receiver")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String receiver;
    @Column(name="sender_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderId;
    @Column(name="chatId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chatId;
    @Column(name="recipient_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recipientId;
    @Column(name="type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @Column(name="content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
    @Column(name="message_status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MessageStatus status;
    @Column(name="created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt = LocalDateTime.now();
}
