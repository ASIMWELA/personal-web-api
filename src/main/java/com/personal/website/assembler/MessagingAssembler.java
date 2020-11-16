package com.personal.website.assembler;

import com.personal.website.entity.MessageEntity;
import com.personal.website.entity.ResourceEntityCollection;
import com.personal.website.model.MessagingModel;
import com.personal.website.model.ResourceModelCollection;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class MessagingAssembler implements RepresentationModelAssembler<MessageEntity, MessagingModel> {
    @Override
    public MessagingModel toModel(MessageEntity entity) {
        return null;
    }

    @Override
    public CollectionModel<MessagingModel> toCollectionModel(Iterable<? extends MessageEntity> entities) {
        return null;
    }
}
