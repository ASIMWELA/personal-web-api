package com.personal.website.controller;


import com.personal.website.entity.MessageEntity;
import com.personal.website.payload.ChatNotification;
import com.personal.website.repository.MessageRepository;
import com.personal.website.repository.UserRepository;
import com.personal.website.service.ChatMessageService;
import com.personal.website.service.ChatRoomService;
import com.personal.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MessageController
{
    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);


    /*-------------------- Group (Public) chat--------------------*/
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public MessageEntity sendMessage(@Payload MessageEntity chatMessage)
    {

        if(!chatMessage.getType().equals("TYPING"))
        {
            messageRepository.save(chatMessage);
        }

        if(chatMessage.getType().equals("LEAVE"))
        {
            userService.toggleUserPresence(chatMessage.getSender(), false);
        }

        return chatMessage;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public MessageEntity addUser(@Payload MessageEntity chatMessage, SimpMessageHeaderAccessor headerAccessor )
    {
        // Add user in web socket session

        userService.toggleUserPresence(chatMessage.getSender(), true);

        messageRepository.save(chatMessage);

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());


       return chatMessage;
        //return chatMessage;

    }

    @MessageMapping("/toggleAdmin")
    public void toggleAdmin(@Payload MessageEntity chatMessage )
    {
        // Add user in web socket session

        if(chatMessage.getType().equals("JOIN")){
            userService.toggleUserPresence(chatMessage.getSender(), true);
        }
        if(chatMessage.getType().equals("LEAVE")){
            userService.toggleUserPresence(chatMessage.getSender(), false);
        }

    }




    /*--------------------Private chat--------------------*/

    @MessageMapping("/privateChat")
    public void processMessage(@Payload MessageEntity chatMessage) {
        String chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);

        chatMessage.setChatId(chatId);

        MessageEntity saved = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSender()));
    }

}
