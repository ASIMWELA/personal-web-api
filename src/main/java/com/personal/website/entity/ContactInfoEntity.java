package com.personal.website.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="contact_info")
@Table(name="contact_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContactInfoEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_info_id")
    @JsonIgnore
    private Long id;

    @Column(name="phone_number",unique=true, length = 20)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;

    @Column(name="physical_address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String physicalAddress;

    @Column(name="city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @Column(name="country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    @JsonIgnore
    @OneToOne(mappedBy = "contactInfo")
    private UserEntity user;


}
