package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    @JsonIgnore
    private Long id;
    @Column(name="sender")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sender;
    @Column(name="receiver")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String receiver;
    @Column(name="type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @Column(name="content")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
    @Column(name="created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    private LocalDateTime createdAt;
}
