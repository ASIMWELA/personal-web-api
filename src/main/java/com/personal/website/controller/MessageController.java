package com.personal.website.controller;


import com.personal.website.entity.MessageEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.repository.MessageRepository;
import com.personal.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
public class MessageController
{
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/send/{userName}")
    @SendTo("/queue/chat")
    public ResponseEntity<MessageEntity> sendMessage(@RequestBody MessageEntity message, @DestinationVariable String userName)
    {
        UserEntity sender = userRepository
                .findByUserName(userName)
                .orElseThrow(()->new EntityNotFoundException("No subscriber with username "+ userName));

        MessageEntity sentMessage = message.builder()
                .sender(sender.getUserName())
                .receiver(message.getReceiver())
                .content(message.getContent())
                .type(message.getType())
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(sentMessage);

        return new ResponseEntity<MessageEntity>(sentMessage, HttpStatus.OK);

    }
}
