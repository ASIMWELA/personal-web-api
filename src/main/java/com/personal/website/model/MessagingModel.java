package com.personal.website.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Data
public class MessagingModel extends RepresentationModel<MessagingModel>
{
}
