package com.exercise.nisum.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContactDataDTO {

    @JsonProperty(value = "number")
    String number;

    @JsonProperty(value = "citycode ")
    String cityCode;

    @JsonProperty(value = "contrycode")
    String countryCode;
}
