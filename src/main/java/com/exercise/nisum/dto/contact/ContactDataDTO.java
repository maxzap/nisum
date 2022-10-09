package com.exercise.nisum.dto.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDataDTO {

    @JsonProperty(value = "number")
    String number;

    @JsonProperty(value = "citycode ")
    String cityCode;

    @JsonProperty(value = "contrycode")
    String countryCode;
}
