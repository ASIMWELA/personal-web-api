package com.personal.website.service;


import com.personal.website.entity.MessageEntity;
import com.personal.website.exception.EntityNotFoundException;
import com.personal.website.payload.MessageStatus;
import com.personal.website.repository.MessageRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChatMessageService
{
    @Autowired private MessageRepository messageRepository;
    @Autowired private ChatRoomService chatRoomService;
    //@Autowired private MongoOperations mongoOperations;

    public MessageEntity save(MessageEntity chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        messageRepository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId)
    {
        return messageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<MessageEntity> findChatMessages(String senderId, String recipientId) {
        String chatId = chatRoomService.getChatId(senderId, recipientId, false);
        if(!(chatId==null)){
            List<MessageEntity> messages = messageRepository.findByChatId(chatId);

            if(messages.size() > 0) {
                //updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
                messages.forEach(message->{
                    if(message.getSenderId().equals(senderId) && message.getRecipientId().equals(recipientId)){
                        message.setStatus(MessageStatus.DELIVERED);
                        messageRepository.save(message);
                    }

                    // messageRepository.updateStatus(MessageStatus.DELIVERED, recipientId,recipientId);
                });
            }

            return messages;
        }
        else {
            return new ArrayList<>();
        }

    }

    public MessageEntity findById(Long id) {
        return messageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return messageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("can't find message (" + id + ")"));
    }

//    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
//        Query query = new Query(
//                Criteria
//                        .where("senderId").is(senderId)
//                        .and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessage.class);
//    }
}
