package com.personal.website.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="chatRoom")
@Table(name="chatRoom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="chat_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String chatId;
    @Column(name="sender_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderId;
    @Column(name="recipient_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String recipientId;
}
