package com.personal.website.service;


import com.personal.website.entity.ChatRoom;
import com.personal.website.repository.ChatRoomRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public String getChatId(
            String senderId, String recipientId, boolean createIfNotExist) {

        String chartId;

        String chatId = chatRoomRepository
                .getChatId(senderId, recipientId);

        if(chatId==null)
        {
            if(createIfNotExist)
            {
                chartId =  senderId+"_"+recipientId;

                ChatRoom senderRecipient = ChatRoom
                        .builder()
                        .chatId(chartId)
                        .senderId(senderId)
                        .recipientId(recipientId)
                        .build();

                ChatRoom recipientSender = ChatRoom
                        .builder()
                        .chatId(chartId)
                        .senderId(recipientId)
                        .recipientId(senderId)
                        .build();
                chatRoomRepository.save(senderRecipient);
                chatRoomRepository.save(recipientSender);
            }
            else
            {
                chartId = null;
            }

        }
        else
        {
            chartId = chatId;


        }

    return chartId;


    }

}
