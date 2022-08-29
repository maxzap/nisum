package com.exercise.nisum.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contact_phone")
@Data
public class PhoneModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "number")
    @NotNull
    private String number;

    @Column(nullable = false, name = "city_code")
    @NotBlank
    private String cityCode;

    @Column(nullable = false, name = "country_code")
    @NotBlank
    private String countryCode;

    @Column(nullable = false, name = "user_id")
    @NotBlank
    private String userId;


}
