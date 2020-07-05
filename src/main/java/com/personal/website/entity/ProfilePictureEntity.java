package com.personal.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "profile_picture")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePictureEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name="profile_id", length = 11)
    private Long id;

    @Column(name = "file_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fileName;

    @Column(name="file_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fileType;

    @Lob
    @Column(name="image_data", length = 1000)
    private byte[] data;

    @JsonIgnore
    @OneToOne(mappedBy = "profilePicture")
    private UserEntity user;

}
